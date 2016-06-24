/**
 * Copyright (C) 2015 Smithsonian Astrophysical Observatory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cfa.vo.iris.visualizer.plotter;

import javax.swing.JPanel;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.quantities.SPVYQuantity;
import cfa.vo.iris.visualizer.preferences.FunctionModel;
import cfa.vo.iris.visualizer.preferences.LayerModel;
import cfa.vo.iris.sed.stil.SegmentStarTable;
import cfa.vo.iris.visualizer.preferences.FunctionModel;
import cfa.vo.iris.visualizer.preferences.SedModel;
import cfa.vo.iris.visualizer.preferences.VisualizerComponentPreferences;
import cfa.vo.iris.visualizer.preferences.VisualizerDataModel;
import java.awt.Dimension;
import uk.ac.starlink.ttools.plot2.geom.PlaneAspect;
import uk.ac.starlink.ttools.plot2.geom.PlaneSurfaceFactory;
import uk.ac.starlink.ttools.plot2.task.PlanePlot2Task;
import uk.ac.starlink.ttools.plot2.task.PlotDisplay;
import uk.ac.starlink.ttools.task.MapEnvironment;

import java.awt.GridBagConstraints;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.starlink.task.Parameter;
import uk.ac.starlink.ttools.plot2.Axis;

public class StilPlotter extends JPanel {

    private static final Logger logger = Logger
            .getLogger(StilPlotter.class.getName());

    private static final long serialVersionUID = 1L;
    
    // Primary plot display
    private PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> display;
    
    // Residuals
    private PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> residuals;
    private boolean showResiduals = false;
    private String residualsOrRatios = FunctionModel.RESIDUAL;

    // List of SEDs plotted in Plotter
    private List<ExtSed> seds = new ArrayList<>();
    
    // For data model preferences
    private VisualizerComponentPreferences preferences;
    private VisualizerDataModel dataModel;
    
    // Plot preferences for the currently plotted selection of SEDs
    private PlotPreferences plotPreferences;
    
    // MapEnvironment for the primary plotter
    private MapEnvironment env;
    
    // MapEnvironment for the residuals plotter
    private MapEnvironment resEnv;
    
    // Needs a default constructor for Netbeans
    public StilPlotter() {
    }
    
    public StilPlotter(VisualizerComponentPreferences preferences) {
        this.setPreferences(preferences);
    }

    /*
     * 
     * Getters and Setters
     * 
     */
    
    public List<ExtSed> getSeds() {
        return seds;
    }

    /**
     * For binding to the datamodel. This function SHOULD NOT be called otherwise.
     */
    public void setSeds(List<ExtSed> newSeds) {
        this.seds = newSeds;
        
        // Update plot preferences for new seds.
        setPlotPreferences(preferences.getPlotPreferences(newSeds));
        
        resetPlot(false, true);
    }

    public VisualizerComponentPreferences getPreferences() {
        return preferences;
    }

    void setPreferences(VisualizerComponentPreferences prefs) {
        this.preferences = prefs;
        setDataModel(prefs.getDataModel());
    }
    
    public VisualizerDataModel getDataModel() {
        return dataModel;
    }
    
    void setDataModel(VisualizerDataModel dataModel) {
        this.dataModel = dataModel;
        this.setSeds(dataModel.getSelectedSeds());
    }
    
    public List<LayerModel> getLayerModels() {
        return dataModel.getLayerModels();
    }
    
    /**
     * For binding to the dataModel, this SHOULD NOT be called otherwise.
     */
    public void setLayerModels(List<LayerModel> models) {
        this.resetPlot(false, false);
    }

    /**
     * @return The stil plot display.
     */
    public PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> getPlotDisplay() {
        return display;
    }

    /**
     * @return The residuals plot display.
     */
    public PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> getResidualsPlotDisplay() {
        return residuals;
    }

    /**
     * @return MapEnvironment configuration for the Stil plot task.
     */
    protected MapEnvironment getEnv() {
        return env;
    }
    
    /**
     * @return MapEnvironment configuration for the residuals.
     */
    protected MapEnvironment getResEnv() {
        return resEnv;
    }
   
    /**
     * @return Current set of PlotPreferences used by the plotter.
     */
    public PlotPreferences getPlotPreferences() {
        return plotPreferences;
    }
    
    /**
     * Used internally for updating the plot preferences on SED changes.
     */
    public static final String PROP_PLOT_PREFERENCES = "plotPreferences";
    public void setPlotPreferences(PlotPreferences pp) {
        // If the preferences are changing we want to save the last known display's
        // aspect in the old preferences.
        if (display != null) {
            getPlotPreferences().setAspect(display.getAspect());
        }
        
        PlotPreferences old = this.plotPreferences;
        this.plotPreferences = pp;
        this.firePropertyChange(PROP_PLOT_PREFERENCES, old, pp);
    }
    
    /**
     * Change the plotting space between logarithmic and linear. One of the axes
     * can be logarithmic, while the other is linear.
     * 
     * @param plotType the plot type to use.
     */
    public void setPlotType(PlotPreferences.PlotType plotType) {
        getPlotPreferences().setPlotType(plotType);
        resetPlot(false, false);
    }
    
    public boolean getGridOn() {
        return getPlotPreferences().getShowGrid();
    }
    
    public void setGridOn(boolean on) {
        getPlotPreferences().setShowGrid(on);
        resetPlot(false, false);
    }
    
    public boolean isShowResiduals() {
        return showResiduals;
    }
    
    public void setShowResiduals(boolean on) {
        this.showResiduals = on;
        resetPlot(false, false);
    }
    
    public String getResidualsOrRatios() {
        return residualsOrRatios;
    }
    
    public void setResidualsOrRatios(String residualsOrRatios) {
        this.residualsOrRatios = residualsOrRatios;
        resetPlot(false, false);
    }
    
    /**
     * Resets the plot.
     * @param forceReset - forces the plot to reset its bounds
     * @param newPlot - If we are plotting a new plot or re-plotting an existing plot.
     */
    void resetPlot(boolean forceReset, boolean newPlot)
    {
        // forceReset can override this class's internal usage of preferences
        boolean fixed = getPlotPreferences().getFixed();
        
        // Clear the display and save all necessary information before we
        // throw it away on a model change.
        setupForPlotDisplayChange(newPlot);
        
        // Setup new stil plot components
        setupPlotComponents();
        
        // Set the bounds using the current SED's aspect if the plot is fixed and if we're not
        // forcing a redraw
        if (fixed && !forceReset) {
            PlaneAspect existingAspect = getPlotPreferences().getAspect();
            display.setAspect(existingAspect);
        }
        
        // Add the display to the plot view
        addPlotToDisplay();
    }
    
    /**
     * Resets boundaries on the zoom to their original settings.
     */
    public void resetZoom() {
        resetPlot(true, false);
    }
    
    /**
     * Zoom in or out of plot by a given scale factor.
     * @param zoomFactor 
     */
    public void zoom(double zoomFactor) {
        
        double xmax = this.getPlotDisplay().getAspect().getXMax();
        double xmin = this.getPlotDisplay().getAspect().getXMin();
        double ymax = this.getPlotDisplay().getAspect().getYMax();
        double ymin = this.getPlotDisplay().getAspect().getYMin();
        
        double [] xlimits = zoomAxis(zoomFactor, xmin, xmax, 
                getPlotPreferences().getXlog());
        double [] ylimits = zoomAxis(zoomFactor, ymin, ymax, 
                getPlotPreferences().getYlog());
                
        // create new aspect for zoomed view
        PlaneAspect zoomedAspect = new PlaneAspect(xlimits, ylimits);
        this.getPlotDisplay().setAspect(zoomedAspect);
    }
    
    /**
     * Calculate new zoomed axis range.
     * @param zoomFactor - scale factor to zoom in/out by
     * @param min - min axis value
     * @param max - max axis value
     * @param isLog - flag if the axis is in log-space (true) or not (false)
     * @return a double array of the zoomed min and max range: [min, max]
     */
    private double[] zoomAxis(double zoomFactor, double min, double max, boolean isLog) {
        
        if (isLog) {
            
            // calculate central axis value
            double centerFactor = (Math.log10(max) - Math.log10(min))/2;
            double center = centerFactor + Math.log10(min);
            center = Math.pow(10, center);
            
            // calculate zoomed min and max values
            return Axis.zoom(min, max, center, zoomFactor, isLog);
            
        } else {
            
            // calculate the central axis value
            double center = (Math.abs(max) - Math.abs(min))/2;
            if (min < 0 && max > 0) {
                // pass. leave xcenter as it is
            } else {
                center = min + Math.abs(center);
            }
            
            // calculate zoomed min and max values
            return Axis.zoom(min, max, center, zoomFactor, isLog);
        }
    }

    /**
     * Create the stil plot component
     * @return
     */
    private void setupPlotComponents()
    {
        logger.info("Creating new plot from selected SED(s)");

        try {
            // Setup each MapEnvironment
            setupMapEnvironment();
            setupResidualMapEnvironment();
            
            // Create the plot display
            display = createPlotComponent(env, true);
            
            // Create the residuals if specified
            residuals = showResiduals ? createPlotComponent(resEnv, false) : null;
            
            // TODO: Handle mouse listeners and zooming for the residuals!
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param env Plot display environment to use
     * @return PlotDisplay
     * @throws Exception
     */
    protected PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> createPlotComponent(
            MapEnvironment env, boolean enableMouseListeners) throws Exception {

        logger.log(Level.FINE, "plot environment:");
        logger.log(Level.FINE, ReflectionToStringBuilder.toString(env));
        
        // Don't use STIL caching http://tinyurl.com/h7dml6d, our data is already 
        // cached so it doesn't necessarily buy us any performance gains. Moreover,
        // it can cause problems in testing with repeating segments, and potentially
        // production with many changing segments with the same data.
        @SuppressWarnings("unchecked")
        PlotDisplay<PlaneSurfaceFactory.Profile,PlaneAspect> display =
                new PlanePlot2Task().createPlotComponent(env, false);
        
        if (enableMouseListeners) {
            // Always update mouse listeners with the new display
            preferences.getMouseListenerManager().activateListeners(display);
        }
        
        return display;
    }

    protected void setupMapEnvironment() throws IOException {

        env = new MapEnvironment(new LinkedHashMap<String, Object>());
        env.setValue("type", "plot2plane");
        env.setValue("insets", new Insets(30, 80, 40, 50));
        
        // TODO: force numbers on Y axis to only be 3-5 digits long. Keeps
        // Y-label from falling off the jpanel. Conversely, don't set "insets"
        // and let the plotter dynamically change size to keep axes labels
        // on the plot.
        
        PlotPreferences pp = getPlotPreferences();
        
        // Add high level plot preferences
        for (String key : pp.getPreferences().keySet()) {
            
            // if in magnitudes, flip the direction of the Y-axis
            // (lower magnitude = brighter source -> higher on Y-axis)
            if (SPVYQuantity.MAGNITUDE.getPossibleUnits().contains(pp.getPreferences().get(key))) {
                env.setValue("yflip", true);
            }
            
            env.setValue(key, pp.getPreferences().get(key));
        }
        
        // set title of plot
        env.setValue("title", dataModel.getDataModelTitle());

        // Add segments and segment preferences
        for (LayerModel layer : dataModel.getLayerModels()) {
            Map<String, Object> prefs = layer.getPreferences();
            for (String key : prefs.keySet()) {
                env.setValue(key, prefs.get(key));
            }
        }
        
        // add model functions
        for (SedModel sedModel : dataModel.getSedModels()) {
            // If no model available (e.g. no fit) skip it
            FunctionModel mod = sedModel.getFunctionModel();
            if (mod == null) {
                continue;
            }
            LayerModel layer = mod.getFunctionLayerModel();
            Map<String, Object> prefs = layer.getPreferences();
            for (String key : prefs.keySet()) {
                env.setValue(key, prefs.get(key));
            }
            
        }
        
    }
    
    private void setupResidualMapEnvironment() throws Exception {
        if (!showResiduals) {
            resEnv = null;
            return;
        }
        
        PlotPreferences pp = getPlotPreferences();
        
        resEnv = new MapEnvironment(new LinkedHashMap<String, Object>());
        resEnv.setValue("type", "plot2plane");
        resEnv.setValue("insets", new Insets(20, 80, 20, 50));
        
        resEnv.setValue(PlotPreferences.SIZE, pp.getSize());
        resEnv.setValue(PlotPreferences.X_LOG, pp.getPlotType().xlog);
        resEnv.setValue(PlotPreferences.GRID, pp.getShowGrid());
        
        resEnv.setValue("ylabel", residualsOrRatios);
        resEnv.setValue("xlabel", null);
        resEnv.setValue("legend", false);
        
        // Add the function layer model for each FunctionModel available in the 
        // dataModel
        for (SedModel sedModel : dataModel.getSedModels()) {
            // If no model available (e.g. no fit) skip it
            FunctionModel mod = sedModel.getFunctionModel();
            if (mod == null) {
                continue;
            }
            
            // If no residuals available Skip
            LayerModel layer = mod.getResidualsLayerModel(residualsOrRatios);
            if (layer == null) {
                continue;
            }
            
            Map<String, Object> prefs = layer.getPreferences();
            for (String key : prefs.keySet()) {
                resEnv.setValue(key, prefs.get(key));
            }
        }
    }
    
    private void setupForPlotDisplayChange(boolean newPlot) {
        if (display == null) {
            return;
        }
        
        if (residuals != null) {
            remove(residuals);
        }
        
        remove(display);
        
        if (!newPlot) {
            getPlotPreferences().setAspect(display.getAspect());
        }
    }
    
    /**
     * Add and update the PlotDisplay.
     */
    private void addPlotToDisplay() {
        
        GridBagConstraints displayGBC = new GridBagConstraints();
        displayGBC.anchor = GridBagConstraints.NORTHWEST;
        displayGBC.fill = GridBagConstraints.BOTH;
        displayGBC.weightx = 1;
        displayGBC.weighty = .75;
        displayGBC.gridx = 0;
        displayGBC.gridy = 0;
        displayGBC.gridheight = 1;
        displayGBC.gridwidth = 1;

        display.setPreferredSize(new Dimension(600, 500));
        add(display, displayGBC);
        
        // Ad the residuals to the jpanel if specified
        if (showResiduals) {
            // Ensure it fills the entire display
            GridBagConstraints residualsGBC = new GridBagConstraints();
            residualsGBC.anchor = GridBagConstraints.NORTHWEST;
            residualsGBC.fill = GridBagConstraints.BOTH;
            residualsGBC.weightx = 1;
            residualsGBC.weighty = .25;
            residualsGBC.gridx = 0;
            residualsGBC.gridy = 1;
            residualsGBC.gridheight = 1;
            residualsGBC.gridwidth = 1;
            
            residuals.setPreferredSize(new Dimension(600,100));
            residuals.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            residuals.setMinimumSize(new Dimension(0, 50));
            add(residuals, residualsGBC);
            
            residuals.revalidate();
            residuals.repaint();
        }
        
        display.revalidate();
        display.repaint();
    }
}