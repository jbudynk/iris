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

import cfa.vo.iris.IrisApplication;
import cfa.vo.iris.events.SedCommand;
import cfa.vo.iris.events.SedEvent;
import cfa.vo.iris.events.SedListener;
import cfa.vo.iris.fitting.custom.DefaultCustomModel;
import cfa.vo.iris.fitting.custom.ModelsListener;
import cfa.vo.iris.gui.NarrowOptionPane;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.visualizer.FittingToolComponent;
import cfa.vo.sherpa.models.Model;
import cfa.vo.sherpa.optimization.OptimizationMethod;
import cfa.vo.sherpa.stats.Statistic;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FittingMainView extends JInternalFrame implements SedListener {
    private ExtSed sed;
    private FitController controller;
    public final String DEFAULT_DESCRIPTION = "Double click on a Component to add it to the list of selected Components.";
    public final String CUSTOM_DESCRIPTION = "User Model";
    public static final String PROP_SED = "sed";

    public FittingMainView() {
        initComponents();
        SedEvent.getInstance().add(this);
    }

    public FittingMainView(FitController controller) {
        this();
        this.controller = controller;
        setSed(controller.getSed());
        initController();
        setUpAvailableModelsTree();
        setUpModelViewerPanel();
        setUpMenuBar();
        confidencePanel.setController(controller);
        revalidate();
        pack();
    }

    public ExtSed getSed() {
        return sed;
    }

    public void setSed(ExtSed sed) {
        this.sed = sed;
        firePropertyChange(PROP_SED, null, sed);
        controller.setSed(sed);
    }

    @Override
    public void process(ExtSed source, SedCommand payload) {
        if (SedCommand.SELECTED.equals(payload) || SedCommand.CHANGED.equals(payload) && source.equals(sed)) {
            setSed(source);
        }
    }

    private void initController() {
        controller.addListener((ModelsListener) availableTree);
    }

    private void setUpMenuBar() {
        saveTextMenuItem.setAction(new SaveTextAction());
    }

    private void setUpModelViewerPanel() {
        modelViewerPanel.setSed(sed);
        modelViewerPanel.setEditable(true);
    }

    private void setUpAvailableModelsTree() {
        ((CustomJTree) availableTree).register();
        availableTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        availableTree.addMouseListener(new AvailableModelsMouseAdapter());
        availableTree.setModel(controller.getModelsTreeModel());
        rootPane.setDefaultButton(searchButton);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        modelPanel = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        optimizationCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        statisticCombo = new javax.swing.JComboBox();
        fitButton = new javax.swing.JButton();
        modelViewerPanel = new cfa.vo.iris.gui.widgets.ModelViewerPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        resultsContainer = new javax.swing.JPanel();
        resultsPanel = new cfa.vo.iris.fitting.FitResultsPanel();
        confidenceContainer = new javax.swing.JPanel();
        confidencePanel = new cfa.vo.iris.fitting.ConfidencePanel();
        jPanel4 = new javax.swing.JPanel();
        availableComponents = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        searchButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        availableTree = new CustomJTree();
        currentSedLabel = new javax.swing.JLabel();
        currentSedField = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveTextMenuItem = new javax.swing.JMenuItem();
        loadMenuItem = new javax.swing.JMenuItem();
        saveXmlMenuItem = new javax.swing.JMenuItem();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Fitting Tool");
        setAutoscrolls(true);
        setMinimumSize(new java.awt.Dimension(700, 500));
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jSplitPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Fit Configuration"));
        jSplitPane1.setDividerLocation(250);

        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        modelPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSplitPane3.setDividerLocation(450);

        jLabel1.setText("Optimization Method:");

        optimizationCombo.setModel(new DefaultComboBoxModel<>(OptimizationMethod.values()));
        optimizationCombo.setName("optimizationCombo"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sed.fit.method}"), optimizationCombo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel2.setText("Statistic:");

        statisticCombo.setModel(new DefaultComboBoxModel<>(Statistic.values()));
        statisticCombo.setName("statisticCombo"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sed.fit.stat}"), statisticCombo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        fitButton.setText("Fit");
        fitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doFit(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optimizationCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(fitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(statisticCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optimizationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statisticCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fitButton)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel5);
        jSplitPane3.setLeftComponent(modelViewerPanel);

        javax.swing.GroupLayout modelPanelLayout = new javax.swing.GroupLayout(modelPanel);
        modelPanel.setLayout(modelPanelLayout);
        modelPanelLayout.setHorizontalGroup(
            modelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3)
        );
        modelPanelLayout.setVerticalGroup(
            modelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
        );

        jSplitPane4.setLeftComponent(modelPanel);

        jSplitPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane2.setDividerLocation(325);

        resultsContainer.setBorder(null);

        javax.swing.GroupLayout resultsContainerLayout = new javax.swing.GroupLayout(resultsContainer);
        resultsContainer.setLayout(resultsContainerLayout);
        resultsContainerLayout.setHorizontalGroup(
            resultsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
        );
        resultsContainerLayout.setVerticalGroup(
            resultsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(resultsContainer);

        javax.swing.GroupLayout confidenceContainerLayout = new javax.swing.GroupLayout(confidenceContainer);
        confidenceContainer.setLayout(confidenceContainerLayout);
        confidenceContainerLayout.setHorizontalGroup(
            confidenceContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(confidencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );
        confidenceContainerLayout.setVerticalGroup(
            confidenceContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(confidencePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(confidenceContainer);

        jSplitPane4.setRightComponent(jSplitPane2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4)
        );

        jSplitPane1.setRightComponent(jPanel2);

        availableComponents.setBorder(javax.swing.BorderFactory.createTitledBorder("Available Components"));

        descriptionArea.setEditable(false);
        descriptionArea.setColumns(20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setRows(3);
        descriptionArea.setText(DEFAULT_DESCRIPTION);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEnabled(false);
        descriptionArea.setName("descriptionArea"); // NOI18N
        jScrollPane2.setViewportView(descriptionArea);

        searchButton.setText("Search");
        searchButton.setName("searchButton"); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchField.setName("searchField"); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Model Components");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Preset Model Components");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("model");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("User Model");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("model");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        availableTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        availableTree.setName("availableTree"); // NOI18N
        jScrollPane3.setViewportView(availableTree);

        javax.swing.GroupLayout availableComponentsLayout = new javax.swing.GroupLayout(availableComponents);
        availableComponents.setLayout(availableComponentsLayout);
        availableComponentsLayout.setHorizontalGroup(
            availableComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(availableComponentsLayout.createSequentialGroup()
                .addComponent(searchField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        availableComponentsLayout.setVerticalGroup(
            availableComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(availableComponentsLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(availableComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(availableComponents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(availableComponents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel4);

        currentSedLabel.setText("Current Sed: ");

        currentSedField.setEditable(false);
        currentSedField.setEnabled(false);
        currentSedField.setName("currentSedField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sed.id}"), currentSedField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentSedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentSedField, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSplitPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentSedLabel)
                    .addComponent(currentSedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        getContentPane().add(jPanel1);

        jMenu1.setText("File");

        saveTextMenuItem.setText("load");
        jMenu1.add(saveTextMenuItem);

        loadMenuItem.setText("saveXML");
        jMenu1.add(loadMenuItem);

        saveXmlMenuItem.setText("saveText");
        jMenu1.add(saveXmlMenuItem);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        controller.filterModels(searchField.getText());
    }//GEN-LAST:event_searchButtonActionPerformed

    private void doFit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doFit
        try {
            controller.fit();
        } catch (Exception e) {
            NarrowOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    e.getClass().getSimpleName(),
                    NarrowOptionPane.ERROR_MESSAGE);
            modelViewerPanel.fitResult(false);
            return; // TODO maybe should do something more/different.
        }
        resultsPanel.setFit(sed.getFit());
        modelViewerPanel.fitResult(true);
        modelViewerPanel.updateUI();
    }//GEN-LAST:event_doFit

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel availableComponents;
    private javax.swing.JTree availableTree;
    private javax.swing.JPanel confidenceContainer;
    private cfa.vo.iris.fitting.ConfidencePanel confidencePanel;
    private javax.swing.JTextField currentSedField;
    private javax.swing.JLabel currentSedLabel;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JButton fitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel modelPanel;
    private cfa.vo.iris.gui.widgets.ModelViewerPanel modelViewerPanel;
    private javax.swing.JComboBox optimizationCombo;
    private javax.swing.JPanel resultsContainer;
    private cfa.vo.iris.fitting.FitResultsPanel resultsPanel;
    private javax.swing.JMenuItem saveTextMenuItem;
    private javax.swing.JMenuItem saveXmlMenuItem;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JComboBox statisticCombo;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private class CustomJTree extends JTree implements ModelsListener {
        public CustomJTree() {
        }

        public void register() {
            controller.addListener(this);
        }

        @Override
        public void setModel(TreeModel model) {
            super.setModel(model);
            for (int i = 0; i < this.getRowCount(); i++) {
                this.expandRow(i);
            }
        }
    }

    private class AvailableModelsMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            TreePath selPath = availableTree.getPathForLocation(e.getX(), e.getY());
            if (selPath != null) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                if (node.isLeaf()) {
                    Object leaf = node.getUserObject();
                    if (Model.class.isInstance(leaf)) {
                        Model m = (Model) leaf;
                        descriptionArea.setText(m.getDescription());
                        if (e.getClickCount() == 2) {
                            controller.addModel(m);
                        }
                    } else {
                        DefaultCustomModel m = (DefaultCustomModel) leaf;
                        descriptionArea.setText(CUSTOM_DESCRIPTION);
                        if (e.getClickCount() == 2) {
                            controller.addModel(m);
                        }
                    }
                } else {
                    descriptionArea.setText(DEFAULT_DESCRIPTION);
                }
            }
        }
    }

    private class SaveTextAction extends AbstractAction {
        public SaveTextAction() {
            super("Save Text...");

        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(FittingMainView.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.save(new FileOutputStream(chooser.getSelectedFile()));
                } catch(FileNotFoundException ex) {
                    NarrowOptionPane.showMessageDialog(FittingMainView.this, ex.getMessage(), "Error", NarrowOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
