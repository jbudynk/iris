/**
 * Copyright (C) 2016 Smithsonian Astrophysical Observatory
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

import java.util.HashMap;
import java.util.Map;

public class PlotPreferences {
    
    // This is only a small sample of the preferences available in STILTS, for
    // the full list, see
    // http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#TypedPlot2Task
    // Global Settings
    public static final String GRID = "grid";
    public static final String X_LABEL = "xlabel";
    public static final String Y_LABEL = "ylabel";
    public static final String X_LOG = "xlog";
    public static final String Y_LOG = "ylog";
    public static final String AUTO_FIX = "auto_fix"; // not STILTS
    public static final String X_MAX = "xmax";
    public static final String X_MIN = "xmin";
    public static final String Y_MAX = "ymax";
    public static final String Y_MIN = "ymin";
    public static final String PLOT_TYPE = "plot_type"; // not STILTS
    //public static final String SHOW_ERRORS = "show_errors"; // not STILTS
    
    // Plot Types - Iris-specific, not STILTS.
    public enum PlotType {
        LOG("log"),
        LINEAR("linear"),
        X_LOG("xlog"),
        Y_LOG("ylog");
        
        public String name;
    
        private PlotType(String arg) {
            this.name = arg;
        }
    }
    
    /**
     * 
     * @return default plot preferences
     */
    public static PlotPreferences getDefaultPlotPreferences() {
        return new PlotPreferences()
                .setXlog(true)
                .setYlog(true)
                .setShowGrid(true)
                .setFixed(false)
//                .setShowErrors(true)
                .setPlotType(PlotType.LOG);
    }
    
    private Map<String, Object> preferences;

    public PlotPreferences() {
        this.preferences = new HashMap<String, Object>();
    }
    
    public Map<String, Object> getPreferences() {
        return preferences;
    }
    
    public PlotPreferences setShowGrid(boolean arg1) {
        this.preferences.put(GRID, arg1);
        return this;
    }
    
    public boolean getShowGrid() {
        return (boolean) this.preferences.get(GRID);
    }
    
    public PlotPreferences setYlabel(String arg1) {
        this.preferences.put(Y_LABEL, arg1);
        return this;
    }
    
    public String getYlabel() {
        return (String) this.preferences.get(Y_LABEL);
    }
    
    public PlotPreferences setXlabel(String arg1) {
        this.preferences.put(X_LABEL, arg1);
        return this;
    }
    
    public String getXlabel() {
        return (String) this.preferences.get(X_LABEL);
    }
    
    public PlotPreferences setXlog(boolean arg1) {
        this.preferences.put(X_LOG, arg1);
        return this;
    }
    
    public boolean getXlog() {
        return (boolean) this.preferences.get(X_LOG);
    }
    
    public PlotPreferences setYlog(boolean arg1) {
        this.preferences.put(Y_LOG, arg1);
        return this;
    }
    
    public boolean getYlog() {
        return (boolean) this.preferences.get(Y_LOG);
    }
    
    public PlotPreferences setFixed(boolean arg1) {
        this.preferences.put(AUTO_FIX, arg1);
        return this;
    }
    
    public boolean getFixed() {
        return (boolean) this.preferences.get(AUTO_FIX);
    }
    
    public PlotPreferences setXmax(double arg1) {
        this.preferences.put(X_MAX, arg1);
        return this;
    }
    
    public double getXmax() {
        return (double) this.preferences.get(X_MAX);
    }
    
    public PlotPreferences setXmin(double arg1) {
        this.preferences.put(X_MIN, arg1);
        return this;
    }
    
    public double getXmin() {
        return (double) this.preferences.get(X_MIN);
    }
    
    public PlotPreferences setYmax(double arg1) {
        this.preferences.put(Y_MAX, arg1);
        return this;
    }
    
    public double getYmax() {
        return (double) this.preferences.get(Y_MAX);
    }
    
    public PlotPreferences setYmin(double arg1) {
        this.preferences.put(Y_MIN, arg1);
        return this;
    }
    
    public double getYmin() {
        return (double) this.preferences.get(Y_MIN);
    }
    
    public PlotPreferences setPlotType(PlotType arg1) {
        this.preferences.put(PLOT_TYPE, arg1);
        switch (arg1) {
            
            case LOG:
                setXlog(true);
                setYlog(true);
                break;
            case LINEAR:
                setXlog(false);
                setYlog(false);
                break;
            case X_LOG:
                setXlog(true);
                setYlog(false);
                break;
            case Y_LOG:
                setXlog(false);
                setYlog(true);
                break;
            default:
                throw new EnumConstantNotPresentException(arg1.getClass(),
                        "Invalid plot type specified.");

        }
        return this;
    }
    
    public PlotType getPlotType() {
        return (PlotType) preferences.get(PLOT_TYPE);
    }
    
//    public PlotPreferences setShowErrors(boolean arg1) {
//        this.preferences.put(SHOW_ERRORS, arg1);
//        return this;
//    }
}
