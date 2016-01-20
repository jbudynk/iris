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
 * PluginManager.java
 *
 * Created on Aug 12, 2011, 10:02:37 AM
 */
package cfa.vo.iris.sdk;

import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.gui.NarrowOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.jdesktop.application.Action;

public class PluginManagerFrame extends javax.swing.JInternalFrame {

    private List<PluginJar> jars = new ArrayList();
    private PluginManager manager;
    private TreeModel pluginsTree;
    public static final String PROP_PLUGINSTREE = "pluginsTree";
    private final String COMPONENTS_DIR = "/components/";

    /**
     * Get the value of pluginsTree
     *
     * @return the value of pluginsTree
     */
    public TreeModel getPluginsTree() {
        return pluginsTree;
    }

    /**
     * Set the value of pluginsTree
     *
     * @param pluginsTree new value of pluginsTree
     */
    public void setPluginsTree(TreeModel pluginsTree) {
        TreeModel oldPluginsTree = this.pluginsTree;
        this.pluginsTree = pluginsTree;
        firePropertyChange(PROP_PLUGINSTREE, oldPluginsTree, pluginsTree);
    }

    public List<PluginJar> getPluginJars() {
        return jars;
    }

    /** Creates new form PluginManager */
    public PluginManagerFrame(PluginManager manager, File configurationDir) {
        this.manager = manager;
        jars.addAll(manager.getPluginJars());
        refreshTree();
        initComponents();
        jTextField1.setText(configurationDir.getAbsolutePath() + COMPONENTS_DIR);
        jTree1.addMouseListener(ma);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Plugin Manager");

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTree1.setAutoscrolls(true);
        jTree1.setName("jTree1"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${pluginsTree}"), jTree1, org.jdesktop.beansbinding.BeanProperty.create("model"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTree1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(PluginManagerFrame.class, this);
        jButton1.setAction(actionMap.get("Load")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jScrollPane2.setBorder(null);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.background"));
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Plugins can provide additional functionalities to Iris. They can be developed by third party developers or by you using the Iris Software Development Kit. For more information refer to the Iris documentation.\nPlease make sure that the author of the plugins is trustworthy, since there is currently no security mechanism in place.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(null);
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jLabel1.setText("Plugin Directory:");
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField1.setEditable(false);
        jTextField1.setName("jTextField1"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jLabel1)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshTree() {
        jars = manager.getPluginJars();
        
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Installed Plugins");
        for (PluginJar jar : jars) {
            DefaultMutableTreeNode jarName = new DefaultMutableTreeNode(jar);
            top.add(jarName);
            for (IrisPlugin plugin : jar.getPlugins()) {
                DefaultMutableTreeNode pluginName = new DefaultMutableTreeNode(plugin.getName());
                DefaultMutableTreeNode pluginDescription = new DefaultMutableTreeNode("Description: " + plugin.getDescription());
                DefaultMutableTreeNode pluginVersion = new DefaultMutableTreeNode("Version: " + plugin.getVersion());
                DefaultMutableTreeNode pluginAuthor = new DefaultMutableTreeNode("Author: " + plugin.getAuthor());
                jarName.add(pluginName);
                pluginName.add(pluginDescription);
                pluginName.add(pluginVersion);
                pluginName.add(pluginAuthor);
                DefaultMutableTreeNode comps = new DefaultMutableTreeNode("Components");
                pluginName.add(comps);
                for (IrisComponent component : plugin.getComponents()) {
                    DefaultMutableTreeNode compNode = new DefaultMutableTreeNode(component.getName());
                    DefaultMutableTreeNode compDescription = new DefaultMutableTreeNode("Description: " + component.getDescription());
                    comps.add(compNode);
                    compNode.add(compDescription);
                }
            }
        }
        setPluginsTree(new DefaultTreeModel(top));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTree jTree1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private static LoadPluginDialog getPluginDialog() {
        return Holder.LOADPLUGINDIALOG;
    }

    private static class Holder {

        private static final LoadPluginDialog LOADPLUGINDIALOG = new LoadPluginDialog(null, true);
    }

    @Action(name = "Load")
    public void load() {
        try {
            LoadPluginDialog dia = getPluginDialog();
            dia.reset();
            dia.setVisible(true);
            if (dia.isSetURL()) {
                URL url = dia.getURL();
                manager.loadJar(url);
                jars = manager.getPluginJars();
                refreshTree();
            }
        } catch (Exception ex) {
            Logger.getLogger(PluginManager.class.getName()).log(Level.SEVERE, null, ex);
            NarrowOptionPane.showMessageDialog(null, ex.getClass() + ex.getMessage(), "Error", NarrowOptionPane.ERROR_MESSAGE);
        }
    }
    private final MouseAdapter ma = new MouseAdapter() {

        private void myPopupEvent(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            JTree tree = (JTree) e.getSource();
            TreePath path = tree.getPathForLocation(x, y);
            if (path == null) {
                return;
            }

            tree.setSelectionPath(path);

            final DefaultMutableTreeNode obj = (DefaultMutableTreeNode) path.getLastPathComponent();

            if (obj.getUserObject() instanceof PluginJar) {
                String label = "Remove";
                JPopupMenu popup = new JPopupMenu();
                JMenuItem item = new JMenuItem(label);
                item.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        PluginJar jar = (PluginJar) obj.getUserObject();
                        manager.unloadJar(jar);
                        refreshTree();
                    }
                });
                popup.add(item);
                popup.show(tree, x, y);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                myPopupEvent(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                myPopupEvent(e);
            }
        }
    };
}
