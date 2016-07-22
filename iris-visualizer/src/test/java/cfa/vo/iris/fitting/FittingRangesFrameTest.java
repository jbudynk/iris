/*
 * Copyright 2016 Chandra X-Ray Observatory.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cfa.vo.iris.fitting;

import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.iris.sed.quantities.XUnit;
import cfa.vo.iris.test.unit.AbstractComponentGUITest;
import cfa.vo.iris.visualizer.FittingToolComponent;
import cfa.vo.iris.visualizer.preferences.VisualizerComponentPreferences;
import cfa.vo.iris.visualizer.preferences.VisualizerDataModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uispec4j.Window;
import org.uispec4j.interception.BasicHandler;
import org.uispec4j.interception.WindowInterceptor;

/**
 *
 * @author jbudynk
 */
public class FittingRangesFrameTest extends AbstractComponentGUITest {
    
    private FittingToolComponent comp = new FittingToolComponent();
    private Window fittingTool;
    private SedlibSedManager sedManager;
    
    @Before
    public void setUp() {
        
        // add a SED
        sedManager = (SedlibSedManager) app.getWorkspace().getSedManager();
        sedManager.newSed("sed");
        
        String windowName = "Fitting Tool";
        
        // open fitting tool
        window.getMenuBar()
            .getMenu("Tools")
            .getSubMenu(windowName)
            .getSubMenu(windowName)
            .click();
        
        assertTrue(desktop.containsWindow(windowName).isTrue());
        
        // get the fitting tool window
        fittingTool = desktop.getWindow(windowName);
        
    }

    @Test
    public void testFittingRangesManager() throws Exception {
        
        // add a range to the Fit Config
        FittingRange range1 = new FittingRange(1.0, 3.0, XUnit.ANGSTROM);
        FittingRange range2 = new FittingRange(4.0, 5.0, XUnit.ANGSTROM);
        FittingRange range3 = new FittingRange(.5, .6, XUnit.NM);
        FitConfiguration fitConfig = sedManager.getSelected().getFit();
        fitConfig.addFittingRange(range1);
        fitConfig.addFittingRange(range2);
        fitConfig.addFittingRange(range3);
        
        // open FittingRangesManager
        fittingTool.getMenuBar().getMenu("Edit").getSubMenu("Add/Edit Fitting Ranges...").click();
        
        // assert fitting ranges manager appears
        desktop.containsWindow("Fitting Ranges Manager").check();
        
        Window rangesWindow = desktop.getWindow("Fitting Ranges Manager");
        
        // assert the table already has a range in it
        assertEquals(3, rangesWindow.getTable().getRowCount());
        
        // check values
        assertEquals("1.0", rangesWindow.getTable().getContentAt(0, 0));
        assertEquals("3.0", rangesWindow.getTable().getContentAt(0, 1));
        assertEquals("Angstrom", rangesWindow.getTable().getContentAt(0, 2));
        assertEquals("5.0", rangesWindow.getTable().getContentAt(2, 0));
        assertEquals("6.0", rangesWindow.getTable().getContentAt(2, 1));
        assertEquals("Angstrom", rangesWindow.getTable().getContentAt(0, 2));
        
        // add a specific range
        rangesWindow.getTextBox("x1TextBox").setText("10.0");
        rangesWindow.getTextBox("x2TextBox").setText("1.0");
        rangesWindow.getComboBox("xUnitComboBox").select(XUnit.NM.getString());
        rangesWindow.getButton("Add");
        
        // assert it's added to the table AND fit config
        assertEquals(4, rangesWindow.getTable().getRowCount());
        assertEquals(4, fitConfig.getFittingRanges().size());
        
//        WindowInterceptor
//                .init(rangesWindow.getButton("Add Range").triggerClick())
//                .process(BasicHandler.init().select(outputFile.getAbsolutePath()))
//                .run());
//        rangesWindow.getButton("Add Range").click();
//        Window setRange = desktop.getWindow("Set Fitting Range");
//        setRange.getTextBox("startPoint").setText("10.0");
//        setRange.getTextBox("endPoint").setText("1.0");
//        setRange.getComboBox("units").select(XUnit.NM.getString());
        
        
    }
    
    @Test
    public void testUpdateTable() {
    }

    @Override
    protected IrisComponent getComponent() {
        return this.comp;
    }
    
}
