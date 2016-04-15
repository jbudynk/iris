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
package cfa.vo.iris.visualizer;

import cfa.vo.interop.SAMPFactory;
import cfa.vo.iris.test.unit.AbstractComponentGUITest;
import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.fitting.FitConfiguration;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.iris.test.unit.TestUtils;
import cfa.vo.sherpa.models.*;

import java.util.ArrayList;
import cfa.vo.sherpa.optimization.OptimizationMethod;
import cfa.vo.sherpa.stats.Stats;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.uispec4j.ComboBox;
import org.uispec4j.TextBox;
import org.uispec4j.Tree;
import org.uispec4j.Window;
import org.uispec4j.interception.BasicHandler;
import org.uispec4j.interception.WindowInterceptor;

public class FittingToolComponentTest extends AbstractComponentGUITest {

    private FittingToolComponent comp = new FittingToolComponent();
    private String windowName;
    private SedlibSedManager sedManager;

    @Before
    public void setUp() throws Exception {
        windowName = "Fitting Tool";
        sedManager = (SedlibSedManager) app.getWorkspace().getSedManager();
    }
    
    @After
    public void tearDown() throws Exception {
        if (sedManager.getSeds() != null) {
            for (ExtSed sed : new ArrayList<>(sedManager.getSeds())) {
                sedManager.remove(sed.getId());
            }
        }
    }

    @Override
    protected IrisComponent getComponent() {
        return comp;
    }

    @Test
    public void testFittingNoSed() throws Exception {
        WindowInterceptor wi = WindowInterceptor.init(
            window.getMenuBar()
                .getMenu("Tools")
                .getSubMenu(comp.getName())
                .getSubMenu(comp.getName())
                .triggerClick()
        );
        
        wi.process(BasicHandler.init().triggerButtonClick("OK")).run();
    }
    
    @Test
    public void testFittingEmptySed() throws Exception {
        sedManager.newSed("TestSed");
        
        final Window mainFit = openWindow();

        TextBox parName = mainFit.getTextBox("Par Name");
        parName.textEquals("No Parameter Selected").check();
        assertEquals("No Model", mainFit.getTextBox("modelExpressionField").getText());
    }

    @Test
    public void testSedNameChange() throws Exception {
        sedManager.newSed("TestSed");
        final Window mainFit = openWindow();
        assertEquals("TestSed", mainFit.getTextBox("currentSedField").getText());
        sedManager.newSed("TestSed2");
        TestUtils.invokeWithRetry(50, 100, new Runnable(){
            @Override
            public void run() {
                assertEquals("TestSed2", mainFit.getTextBox("currentSedField").getText());
            }
        });
    }

    @Test
    public void testViewNonEmptySed() throws Exception {
        ExtSed sed = sedManager.newSed("TestSed");

        final Window mainFit = openWindow();

        addFit(sed);

        TestUtils.invokeWithRetry(50, 100, new Runnable(){
            @Override
            public void run() {
                assertEquals("m", mainFit.getTextBox("modelExpressionField").getText());
            }
        });

        Tree modelsTree = mainFit.getTree("modelsTree");
        modelsTree.click("polynomial.m/m.c0");

        TestUtils.invokeWithRetry(50, 100, new Runnable(){
            @Override
            public void run() {
                assertEquals("1.0", mainFit.getInputTextBox("Par Val").getText());
            }
        });

        final ComboBox statisticCombo = mainFit.getComboBox("statisticCombo");
        final ComboBox optimizationCombo = mainFit.getComboBox("optimizationCombo");
        TestUtils.invokeWithRetry(50, 100, new Runnable(){
            @Override
            public void run() {
                assertTrue(statisticCombo.selectionEquals("LeastSquares").isTrue());
                assertTrue(optimizationCombo.selectionEquals("LevenbergMarquardt").isTrue());
            }
        });

    }

    @Test
    public void testModelSelection() throws Exception {
        sedManager.newSed("TestSed");
        Window mainFit = openWindow();

        Tree availableTree = mainFit.getTree("availableTree");
        final TextBox descriptionArea = mainFit.getTextBox("descriptionArea");
        availableTree.click("Preset Model Components/absorptionedge");

        TestUtils.invokeWithRetry(50, 100, new Runnable(){
            @Override
            public void run() {
                assertEquals("Optical model of interstellar absorption", descriptionArea.getText());
            }
        });
    }

    @Test
    public void testModelSearch() throws Exception {
        sedManager.newSed("TestSed");
        Window mainFit = openWindow();

        TextBox searchField = mainFit.getInputTextBox("searchField");
        searchField.setText("power");
        mainFit.getButton("searchButton").click();

        Tree availableTree = mainFit.getTree("availableTree");
        assertEquals(4, availableTree.getChildCount("Preset Model Components"));
        assertTrue(availableTree.contains("Preset Model Components/beta1d").isTrue());
        assertTrue(availableTree.contains("Preset Model Components/powerlaw").isTrue());
        assertTrue(availableTree.contains("Preset Model Components/brokenpowerlaw").isTrue());
    }

    private Window openWindow() {
        window.getMenuBar()
                .getMenu("Tools")
                .getSubMenu(comp.getName())
                .getSubMenu(comp.getName())
                .click();
        return desktop.getWindow(windowName);
    }

    private FitConfiguration addFit(ExtSed sed) {
        FitConfiguration fit = new FitConfiguration();

        ModelFactory factory = new ModelFactory();
        Model m = factory.getModel("polynomial", "m");
        Parameter c0 = m.findParameter("c0");
        c0.setFrozen(0);

        Parameter c1 = m.findParameter("c1");
        c1.setFrozen(0);

        CompositeModel cm = SAMPFactory.get(CompositeModel.class);
        cm.setName("m");
        cm.addPart(m);
        fit.setModel(cm);

        fit.setMethod(OptimizationMethod.LevenbergMarquardt);
        fit.setStat(Stats.LeastSquares);

        sed.setFit(fit);
        return fit;
    }
}
