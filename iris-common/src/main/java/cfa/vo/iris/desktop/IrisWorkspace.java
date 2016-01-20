/**
 * Copyright (C) 2012 Smithsonian Astrophysical Observatory
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cfa.vo.iris.desktop;

import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.sed.ISedManager;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.iris.units.UnitsManager;
import cfa.vo.utils.Default;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author olaurino
 */
public class IrisWorkspace implements IWorkspace {

    protected IrisDesktop mainview;

    private ISedManager sedManager;

    private static final JFileChooser fileChooser = new JFileChooser();

    public IrisWorkspace() {
        sedManager = new SedlibSedManager();
    }

    public void setDesktop(IrisDesktop mainView) {
        this.mainview = mainView;
        mainView.setWorkspace(this);
    }

    @Override
    public ISedManager getSedManager() {
        return sedManager;
    }

    @Override
    public JFrame getRootFrame() {
        return mainview;
    }

    @Override
    public JDesktopPane getDesktop() {
        return mainview.getDesktopPane();
    }

    @Override
    public UnitsManager getUnitsManager() {
        return Default.getInstance().getUnitsManager();
    }

    /**
     * Get the value of fileChooser
     *
     * @return the value of fileChooser
     */
    @Override
    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    @Override
    public void addFrame(JInternalFrame frame) {
        this.getDesktop().add(frame);
        this.getDesktop().setLayer(frame, 1);
        JDesktopPane d = getDesktop();
        frame.setLocation((d.getWidth()-frame.getWidth())/2, (d.getHeight()-frame.getHeight())/2);
    }

    @Override
    public void removeFrame(JInternalFrame frame) {
        this.getDesktop().remove(frame);
    }
}
