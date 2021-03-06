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
 * About.java
 *
 * Created on Jul 1, 2011, 1:23:20 PM
 */

package cfa.vo.iris;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author olaurino
 */
public class About extends javax.swing.JDialog {

    /** Creates new form About */
    public About(boolean modal) {
        super((JFrame)null,modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("Iris v2.0");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Virtual Astronomical Observatory");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("VAO Analysis Tool");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("for Spectral Energy Distributions");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("Developed at:");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("NASA/IPAC Extragalactic Database");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("for the");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText("Smithsonian Astrophysical Observatory");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText("Space Telescope Science Institute");
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setIcon(new ImageIcon(getClass().getResource("/vao.png")));
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setIcon(new ImageIcon(getClass().getResource("/Iris_logo.png")));
        jLabel11.setName("jLabel11"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel6)
                    .add(jLabel9)
                    .add(jLabel8)
                    .add(jLabel1)
                    .add(jLabel4)
                    .add(jLabel3)
                    .add(jLabel5)
                    .add(jLabel7)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(51, 51, 51)
                        .add(jLabel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 252, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 159, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(40, 40, 40)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .add(5, 5, 5)
                        .add(jLabel4)
                        .add(26, 26, 26)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel8)
                        .add(2, 2, 2)
                        .add(jLabel9)
                        .add(2, 2, 2)
                        .add(jLabel6)
                        .add(26, 26, 26)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables

}
