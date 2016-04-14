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

/*
 * TestBuilderFrame.java
 *
 * Created on Dec 16, 2011, 8:02:07 AM
 */
package cfa.vo.iris.test;

import cfa.vo.interop.SAMPFactory;
import cfa.vo.interop.SimpleSAMPMessage;
import cfa.vo.iris.fitting.FitConfiguration;
import cfa.vo.iris.gui.widgets.SedList;
import cfa.vo.iris.logging.LogEntry;
import cfa.vo.iris.logging.LogEvent;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.sedlib.DoubleParam;
import cfa.vo.sedlib.Sed;
import cfa.vo.sedlib.Segment;
import cfa.vo.sedlib.io.SedFormat;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cfa.vo.sherpa.models.CompositeModel;
import cfa.vo.sherpa.models.ModelFactory;
import cfa.vo.sherpa.models.ModelImpl;
import cfa.vo.sherpa.models.Parameter;
import cfa.vo.sherpa.optimization.OptimizationMethod;
import cfa.vo.sherpa.stats.Stats;
import org.astrogrid.samp.Message;
import org.astrogrid.samp.client.SampException;

/**
 *
 * @author olaurino
 */
public final class TestBuilderFrame extends JInternalFrame {

    private SedlibSedManager manager;
    private SedList sedList;

    /** Creates new form TestBuilderFrame */
    public TestBuilderFrame(final SedlibSedManager manager) {
        super("Test Builder");
        this.manager = manager;
        initComponents();

        sedList = new SedList(manager);

        listPanel.setViewportView(sedList);

        sedList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    JList list = (JList) lse.getSource();
                    setSedSelected(!list.isSelectionEmpty());
                }
            }
        });

    }
    private boolean sedSelected;
    public static final String PROP_SEDSELECTED = "sedSelected";

    /**
     * Get the value of sedSelected
     *
     * @return the value of sedSelected
     */
    public boolean isSedSelected() {
        return sedSelected;
    }

    /**
     * Set the value of sedSelected
     *
     * @param sedSelected new value of sedSelected
     */
    public void setSedSelected(boolean sedSelected) {
        boolean oldSedSelected = this.sedSelected;
        this.sedSelected = sedSelected;
        firePropertyChange(PROP_SEDSELECTED, oldSedSelected, sedSelected);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        newSedButton = new javax.swing.JButton();
        addNedButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        removeSegmentButton = new javax.swing.JButton();
        segNumber = new javax.swing.JTextField();
        removeButton = new javax.swing.JButton();
        listPanel = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        newSedButton.setText("New SED");
        newSedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSed(evt);
            }
        });

        addNedButton.setText("Add NED Segment to selected SED");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sedSelected}"), addNedButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        addNedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNedSegment(evt);
            }
        });

        addUserButton.setText("Add User Segment to selected SED");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sedSelected}"), addUserButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserSegment(evt);
            }
        });

        removeSegmentButton.setText("Remove Segment: ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sedSelected}"), removeSegmentButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        removeSegmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semoveSegment(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sedSelected}"), segNumber, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        removeButton.setText("Remove");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sedSelected}"), removeButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSed(evt);
            }
        });

        jButton1.setText("Send VOTable through SAMP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendVOTable(evt);
            }
        });

        jButton2.setText("Send FITS through SAMP");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFITS(evt);
            }
        });

        jButton3.setText("Send NON Compliant FITS through SAMP-SSA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3sendFITS(evt);
            }
        });

        jButton4.setText("Send Meaningless Message through SAMP");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4sendFITS(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(newSedButton)
                                    .add(removeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(listPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                            .add(addUserButton)
                            .add(addNedButton)
                            .add(layout.createSequentialGroup()
                                .add(removeSegmentButton)
                                .add(9, 9, 9)
                                .add(segNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jButton1))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jButton2))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jButton3))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(newSedButton)
                        .add(4, 4, 4)
                        .add(removeButton))
                    .add(listPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(addUserButton)
                .add(4, 4, 4)
                .add(addNedButton)
                .add(9, 9, 9)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(removeSegmentButton))
                    .add(segNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton4)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newSed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSed
        int c = 0;
        while (manager.existsSed("Sed" + c)) {
            c++;
        }

        ExtSed newSed = manager.newSed("Sed" + c);

    }//GEN-LAST:event_newSed

    private void addNedSegment(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNedSegment
        try {
            Segment seg = Sed.read(getClass().getResource("/3c066aNED.vot").openStream(), SedFormat.VOT).getSegment(0);
            if (seg.createTarget().getPos() == null) {

                if (seg.createChar().createSpatialAxis().createCoverage().getLocation() != null) {
                    seg.createTarget().createPos().setValue(seg.getChar().getSpatialAxis().getCoverage().getLocation().getValue());
                } else {
                    seg.createTarget().createPos().setValue(new DoubleParam[]{new DoubleParam(Double.NaN), new DoubleParam(Double.NaN)});
                }
            }
            manager.getSelected().addSegment(seg);
            FitConfiguration fit = new FitConfiguration();

            ModelFactory factory = new ModelFactory();
            ModelImpl m = factory.getModel("polynomial", "m");
            Parameter c0 = m.getParameter("m.c0");
            c0.setFrozen(0);

            Parameter c1 = m.getParameter("m.c1");
            c1.setFrozen(0);

            CompositeModel cm = SAMPFactory.get(CompositeModel.class);
            cm.setName("m");
            cm.addPart(m);
            fit.setModel(cm);

            fit.setMethod(OptimizationMethod.LevenbergMarquardt);
            fit.setStat(Stats.LeastSquares);

            manager.getSelected().setFit(fit);
        } catch (Exception ex) {
            Logger.getLogger(TestBuilderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addNedSegment

    private void addUserSegment(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserSegment
        try {
            Segment segment = Sed.read(getClass().getResource("/mine.vot").openStream(), SedFormat.VOT).getSegment(0);
            sedList.getSelectedSed().addSegment(segment);
        } catch (Exception ex) {
            Logger.getLogger(TestBuilderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addUserSegment

    private void semoveSegment(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semoveSegment
        try {
            Integer i = Integer.parseInt(segNumber.getText());
            sedList.getSelectedSed().removeSegment(i);
        } catch (Exception ex) {
            LogEvent.getInstance().fire(this, new LogEntry("Error parsing segment number:" + segNumber.getText(), this));
        }
    }//GEN-LAST:event_semoveSegment

    private void removeSed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSed
        manager.remove(sedList.getSelectedSed().getId());
    }//GEN-LAST:event_removeSed

    private void sendVOTable(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendVOTable
        URL url = getClass().getResource("/3c066aNED.vot");
        String format = "application/x-votable+xml";
        Message msg = new Message("spectrum.load.ssa-generic");
        Map map = new HashMap();
        map.put("Access.Format", format);
        msg.addParam("meta", map);
        msg.addParam("url", url.toString());
        msg.addParam("name", "3c066a");
        try {
            TestSSAServer.getController().sendMessage(new SimpleSAMPMessage(msg));
        } catch (SampException ex) {
            Logger.getLogger(TestBuilderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendVOTable

    private void sendFITS(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFITS
        URL url = getClass().getResource("/3c066aNED.fits");
        String format = "application/fits";
        Message msg = new Message("spectrum.load.ssa-generic");
        Map map = new HashMap();
        map.put("Access.Format", format);
        msg.addParam("meta", map);
        msg.addParam("url", url.toString());
        msg.addParam("name", "3c066a");
        try {
            TestSSAServer.getController().sendMessage(new SimpleSAMPMessage(msg));
        } catch (SampException ex) {
            Logger.getLogger(TestBuilderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendFITS

    private void jButton3sendFITS(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3sendFITS
        URL url = getClass().getResource("/lambrate.fits");
        String format = "application/fits";
        Message msg = new Message("spectrum.load.ssa-generic");
        Map map = new HashMap();
        map.put("Access.Format", format);
        msg.addParam("meta", map);
        msg.addParam("url", url.toString());
        msg.addParam("name", "3c066a");
        try {
            TestSSAServer.getController().sendMessage(new SimpleSAMPMessage(msg));
        } catch (SampException ex) {
            Logger.getLogger(TestBuilderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3sendFITS

    private void jButton4sendFITS(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4sendFITS
        URL url = getClass().getResource("/gear_small.png");
        String format = "application/nothing";
        Message msg = new Message("spectrum.load.ssa-generic");
        Map map = new HashMap();
        map.put("Access.Format", format);
        msg.addParam("meta", map);
        msg.addParam("url", url.toString());
//        msg.addParam("name", "3c066a");
        try {
            TestSSAServer.getController().sendMessage(new SimpleSAMPMessage(msg));
        } catch (SampException ex) {
            Logger.getLogger(TestBuilderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4sendFITS

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNedButton;
    private javax.swing.JButton addUserButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane listPanel;
    private javax.swing.JButton newSedButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton removeSegmentButton;
    private javax.swing.JTextField segNumber;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
