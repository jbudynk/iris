/*
 * This software is distributed under a BSD license,
 * as described in the LICENSE file at the top
 * source directory in the Specview source code base.
 */

package spv.components;

/**
 * Created by IntelliJ IDEA.
 * User: busko
 * Date: Nov 18, 2011
 * Time: 9:25:07 AM
 */

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import org.astrogrid.samp.gui.GuiHubConnector;

import cfa.vo.iris.events.*;
import cfa.vo.iris.logging.LogEntry;
import cfa.vo.iris.logging.LogEvent;
import cfa.vo.iris.sed.SedlibSedManager;

import spv.controller.ManagedSpectrum2;
import spv.controller.SecondaryController2;
import spv.controller.display.SecondaryDisplayManager;
import spv.glue.PlottableSEDFittedSpectrum;
import spv.glue.PlottableSEDSegmentedSpectrum;
import spv.glue.SEDFittedSpectrumVisualEditor;
import spv.glue.SEDSegmentedSpectrumVisualEditor;
import spv.util.Callback;
import spv.util.Command;
import spv.util.Include;
import spv.util.properties.SpvProperties;
import spv.view.PlotWidget;

/*
 *  Revision history:
 *  ----------------
 *
 *  18 Nov 11  -  Implemented (IB)
 */

/**
 * This class provides support for Iris-specific display requirements.
 *
 *
 * @author Ivo Busko (Space Telescope Science Institute)
 * @version 1.0 - 18Nov11
 */


public class IrisDisplayManager extends SecondaryDisplayManager implements SedListener {

    public static final String FIT_MODEL = "fit.model";

    private SecondaryController2 secondaryController;
    private GuiHubConnector connection;

    public IrisDisplayManager() {
    }

    void setConnection(GuiHubConnector connection) {
        this.connection = connection;
    }

    /**
     *  This is the main method used to display Sed instances.
     *
     * @param sed    the Sed instance. If <code>null</code>, an empty
     *               frame is loaded on the display.
     * @param name   the string to be used as frame title.
     */
    void display(SedlibSedManager.ExtSed sed, String name) {
        if (sed != null) {

            ManagedSpectrum2 msp1 = (ManagedSpectrum2) sed.getAttachment(FIT_MODEL);

            msp1.getSpectrum().setName(name);
            display(msp1);

            LogEvent.getInstance().fire(this, new LogEntry("SED displayed:  " + name, this));
        }
    }

    public void display(ManagedSpectrum2 msp) {
        PlotWidget pw = buildPlotWidget(msp, false, null);

        MetadataDisplay metadataDisplay = new MetadataDisplay();
        pw.setCommand(Callback.META_DATA, metadataDisplay);

        if (secondaryController == null) {
            secondaryController = new SecondaryController2(pw, this);
        } else {
            PlotWidget widget = secondaryController.getPlotWidget();
            widget.removeListeners();

            secondaryController.loadWidget(pw);
        }
    }

    public void remove(String name) {
        secondaryController.remove(name);
    }

    public PlotWidget getPlotWidget() {
        return secondaryController.getPlotWidget();
    }

    void setDesktopMode(boolean desktopMode) {
        SpvProperties.SetSessionProperty(Include.DESKTOP_MODE, desktopMode ? "true" : "false");
    }

    protected void displayInSecondaryWindow(ManagedSpectrum2 msp) {

        PlotWidget pw = buildPlotWidget(msp, false, null);

        MetadataDisplay metadataDisplay = new MetadataDisplay();
        pw.setCommand(Callback.META_DATA, metadataDisplay);

        if (secondaryController == null ) {
            secondaryController = new SecondaryController2(pw, this);
        } else {
            secondaryController.unregister();
            secondaryController.loadWidget(pw);
        }
    }

    // GUI stuff.

    JInternalFrame getInternalFrame() {
        if (secondaryController != null) {
            return secondaryController.getInternalFrame();
        } else {
            // Returns a fake frame since no data is being displayed.
            JInternalFrame fakeFrame = new JInternalFrame(Include.IRIS_APP_NAME, true, true, true, true);
            JPanel contentPane = (JPanel) fakeFrame.getRootPane().getContentPane();
            makeSpecviewIDPanel(contentPane);
            fakeFrame.setSize(Include.DEFAULT_EMPTY_WINDOW_SIZE);
            return fakeFrame;
        }
    }

    private void makeSpecviewIDPanel(JPanel panel) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new java.awt.Color(255, 255, 255));

        writeIrisIDPanel(titlePanel);

        panel.removeAll();
        panel.setLayout (new BorderLayout());
        panel.setPreferredSize(Include.DEFAULT_EMPTY_WINDOW_SIZE);
        panel.setBackground( new java.awt.Color(0,0,0) );
        {
            String strConstraint;
            strConstraint = "Center";
            panel.add(titlePanel, strConstraint, -1);
        }
    }

    private void writeIrisIDPanel(JPanel titlePanel) {
        titlePanel.setLayout(new BorderLayout());

        URL resource = getClass().getResource(Include.IRIS_ID_ICON);
        ImageIcon icon = new ImageIcon(resource);
        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setBackground(Color.white);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.white);
        labelPanel.setBorder(BorderFactory.createEmptyBorder());
        labelPanel.add(label, BorderLayout.CENTER);
        titlePanel.add(labelPanel, BorderLayout.CENTER);

        JLabel versionLabel = new JLabel(Include.IRIS_SUBTITLE);
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        versionLabel.setForeground(Color.black);
        versionLabel.setFont(new Font("dialog", 1, 16));
    }

    GuiHubConnector getSAMPConnector() {
        return connection;
    }

    public void process(SedlibSedManager.ExtSed extSed, SedCommand sedCommand) {
    }

    // Metadata button. This button is not present in Iris 1.0. Its
    // purpose is to give access to the table with metadata and data
    // for the entire SED. This function was available in Iris 1.0
    // from the Coplot window instead.

    private class MetadataDisplay implements Command {
        public void execute(Object arg) {
            if (arg instanceof PlottableSEDSegmentedSpectrum) {

                // Use this metadata/data browser when not fitting.

                new SEDSegmentedSpectrumVisualEditor((PlottableSEDSegmentedSpectrum) arg,
                                                       null, true, Color.red, null);

            } else if (arg instanceof PlottableSEDFittedSpectrum) {

                // Use this metadata/data browser when fitting a model.

                new SEDFittedSpectrumVisualEditor((PlottableSEDFittedSpectrum)arg,
                                                  null, Color.red, null);
            }
        }
    }
}