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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa.vo.sed.science.stacker;

import cfa.vo.iris.gui.NarrowOptionPane;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.iris.utils.List;
import static cfa.vo.sed.science.stacker.SedStackerAttachments.REDSHIFT;
import static cfa.vo.sed.science.stacker.SedStackerAttachments.NORM_CONSTANT;
import static cfa.vo.sed.science.stacker.SedStackerAttachments.ORIG_REDSHIFT;
import cfa.vo.sedlib.Param;
import cfa.vo.sedlib.Segment;
import cfa.vo.sedlib.common.SedException;
import java.beans.PropertyVetoException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import spv.util.UnitsException;

/**
 *
 * @author jbudynk
 */
public class AddSedsFrame extends javax.swing.JInternalFrame {

    private SedlibSedManager manager;
    private SedStack stack;
    private JTable sedsTable;
    private SedStackerFrame stackerFrame;
    
    public AddSedsFrame(SedlibSedManager manager, SedStack stack, JTable sedsTable, SedStackerFrame stackerFrame) {
	
	this.manager = manager;
	this.stack = stack;
	this.sedsTable = sedsTable;
	this.openSeds = (List) manager.getSeds();
	this.stackerFrame = stackerFrame;
	
	initComponents();
	
	jTable1.setModel(new AddSedsTableModel(openSeds));
	jTable1.getColumnModel().getColumn(1).setPreferredWidth(10);
	
    }
    
    private List<ExtSed> openSeds = new List(); //ObservableCollections.observableList(new ArrayList());

    public static final String PROP_OPENSEDS = "openSeds";

    /**
     * Get the value of openSeds
     *
     * @return the value of openSeds
     */
    public List<ExtSed> getOpenSeds() {
	return openSeds;
    }

    /**
     * Set the value of openSeds
     *
     * @param openSeds new value of openSeds
     */
    public final void setOpenSeds(List<ExtSed> openSeds) {
	List<ExtSed> oldOpenSeds = this.openSeds;
	this.openSeds = openSeds;
	firePropertyChange(PROP_OPENSEDS, oldOpenSeds, openSeds);
    }

    private boolean segmentsAsSeds = false;

    public static final String PROP_SEGMENTSASSEDS = "segmentsAsSeds";

    /**
     * Get the value of segmentsAsSeds
     *
     * @return the value of segmentsAsSeds
     */
    public boolean isSegmentsAsSeds() {
	return segmentsAsSeds;
    }

    /**
     * Set the value of segmentsAsSeds
     *
     * @param segmentsAsSeds new value of segmentsAsSeds
     */
    public void setSegmentsAsSeds(boolean segmentsAsSeds) {
	boolean oldSegmentsAsSeds = this.segmentsAsSeds;
	this.segmentsAsSeds = segmentsAsSeds;
	firePropertyChange(PROP_SEGMENTSASSEDS, oldSegmentsAsSeds, segmentsAsSeds);
    }

    public SedStack getStack() {
	return stack;
    }

    public void setStack(SedStack stack) {
	this.stack = stack;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        selectAllButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Add SEDs");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Open SEDs"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        selectAllButton.setText("Select All");
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.setToolTipText("Add all selected SEDs");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Treat Segments as SEDs");
        jCheckBox1.setToolTipText("Convert the Segments in the selected SEDs to individual SEDs.");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${segmentsAsSeds}"), jCheckBox1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAllButton)
                    .addComponent(addButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(jCheckBox1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
	if (jTable1.isEditing()) {
	    jTable1.getCellEditor().stopCellEditing();
	}
	jTable1.selectAll();
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
	
	if (jTable1.isEditing()) {
	    jTable1.getCellEditor().stopCellEditing();
	}
	
	if (jTable1.getSelectedRowCount() == 0) {
	    NarrowOptionPane.showMessageDialog(null, "No SEDs selected.", "ERROR", NarrowOptionPane.ERROR_MESSAGE);
	    return;
	}
	
	try {
	    
	    // find any empty seds
	    int c = 0;
	    java.util.List<String> emptySeds = new ArrayList();
	    for (int i : jTable1.getSelectedRows()) {
		
		    ExtSed sed = openSeds.get(i);
		    if (sed.getNumberOfSegments() == 0) {
			emptySeds.add(sed.getId());
			c--;
		    }
		c++;
	    }
	    
	    for (int i : jTable1.getSelectedRows()) {
		
		try {
		    
		    ExtSed sed = openSeds.get(i);
		    
		    // if an SED is empty, do not add it to Stack.
		    if (sed.getNumberOfSegments() == 0) {
			throw new StackException();
		    }
		    Object redshift = jTable1.getValueAt(i, 1);
		    
		    // Check for invalid redshift values
		    try {
			if (redshift != null && isNumeric(redshift.toString()) && Double.valueOf((String) redshift) < 0) {

			    NarrowOptionPane.showMessageDialog(null, "Invalid redshift values", "ERROR", NarrowOptionPane.ERROR_MESSAGE);
			    throw new StackException();

			} else if (redshift == null) {

			    ;

			} else if (!isNumeric(redshift)) {

			    NarrowOptionPane.showMessageDialog(null, "Invalid redshift values", "ERROR", NarrowOptionPane.ERROR_MESSAGE);
			    throw new StackException();

			} else {}
		    } catch (NumberFormatException ex) {
			if (redshift.toString().length() == 0)
			    redshift = null;
		    }
		    
		    if (!isSegmentsAsSeds()) {

			sed.addAttachment(ORIG_REDSHIFT, redshift);
			sed.addAttachment(REDSHIFT, sed.getAttachment(ORIG_REDSHIFT));
			sed.addAttachment(NORM_CONSTANT, 1.0);
			stack.add(sed);

		    } else {

			for (int j=0; j<openSeds.get(i).getNumberOfSegments(); j++) {

			    Segment seg = sed.getSegment(j);
			    ExtSed nsed = new ExtSed(sed.getId()+": "+seg.getTarget().getName().getValue(), false);
			    nsed.addSegment(seg);
			    nsed.addAttachment(ORIG_REDSHIFT, redshift);
			    nsed.addAttachment(REDSHIFT, nsed.getAttachment(ORIG_REDSHIFT));
			    nsed.addAttachment(NORM_CONSTANT, 1.0);
			    stack.add(nsed);

			}
		    }

		} catch (SedException ex) {
		    Logger.getLogger(AddSedsFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnitsException ex) {
		    Logger.getLogger(AddSedsFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (StackException ex) {
		    ; 
		    // TODO: is this really what i want to do here?? it works the way i expect... i want to "pass," 
		    // and let the code continue, without adding the SEDs to the Stack.
		}
	    }
	    
	    // inform the user that if one or more of the SEDs selected were empty,
	    // that they were not added to the Stack.
	    if (c != jTable1.getSelectedRowCount()) {
		
		NarrowOptionPane.showMessageDialog(null, 
			"SEDs '"+emptySeds+"' are empty. These SEDs were not added to the Stack.", 
			"WARNING", 
			NarrowOptionPane.WARNING_MESSAGE);
	    }
	    
	} catch (java.lang.NullPointerException ex) {
	    Logger.getLogger(AddSedsFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	try {
	    sedsTable.setModel(new StackTableModel(stack));
	} catch (java.lang.NullPointerException ex) {
	    Logger.getLogger(AddSedsFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	this.setVisible(false);
	try {
	    stackerFrame.setSelected(true);
	} catch (PropertyVetoException ex) {
	    Logger.getLogger(SedStackerFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
	this.setVisible(false);
	try {
	    stackerFrame.setSelected(true);
	} catch (PropertyVetoException ex) {
	    Logger.getLogger(SedStackerFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JButton selectAllButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    // update the SEDs in the AddSedsFrame table for changes in the SED Builder SEDs.
    public void updateSeds(SedStack stack) {
	setStack(stack);
	setOpenSeds((List) manager.getSeds());
	jTable1.setModel(new AddSedsTableModel(openSeds));
    }
    
    
    public class AddSedsTableModel extends AbstractTableModel {
	
	String[][] data = new String[][]{};
	String[] columnNames = new String[] {"Sed ID", "Redshift"};
	
	public AddSedsTableModel(List seds) {
	    
	    //if (isSegmentsAsSeds() == false) {
	    
		data = new String[openSeds.size()][2];

		// populate the table with the Sed ID's and redshifts
		for (int i=0; i<openSeds.size(); i++) {
		    
		    String redshift = null;
		    
		    try {
			java.util.List<? extends Param> params = openSeds.get(i).getSegment(0).getCustomParams();
			for (Param param : params) {
			    if (param.getName().equals("iris:final redshift")) {
				    redshift = param.getValue();
			    }
			}
		    } catch (IndexOutOfBoundsException ex) {
			
		    } 

		    if (redshift == null) {
			redshift = (String) openSeds.get(i).getAttachment(ORIG_REDSHIFT);
		    }

		    data[i][0] = openSeds.get(i).getId();
		    data[i][1] = redshift;
		}
//		int c = 0;
//		ArrayList<Integer> gdIndices = new ArrayList();
//		for (int i=0; i<openSeds.size(); i++) {
//		    ExtSed sed = openSeds.get(i);
//		    if (sed.getNumberOfSegments() != 0) {
//			c++;
//			gdIndices.add(i);
//		    }
//		}
//	    
//		data = new String[c][2];
//
//		// populate the table with the Sed ID's and redshifts
//		//for (int i=0; i<openSeds.size(); i++) {
//		for (int i=0; i<c; i++) {
//		    
//		    Integer gd = gdIndices.get(i);
//		    String redshift = null;
//		    
//		    java.util.List<? extends Param> params = openSeds.get(gd).getSegment(0).getCustomParams();
//		    for (Param param : params) {
//			if (param.getName().equals("iris:final redshift")) {
//				redshift = param.getValue();
//			}
//		    }
//
//		    if (redshift == null) {
//			redshift = (String) openSeds.get(gd).getAttachment(ORIG_REDSHIFT);
//		    }
//
//		    data[i][0] = openSeds.get(gd).getId();
//		    data[i][1] = redshift;
//		}
//	    } else {
//		
//		// count the number of rows for the data table
//		int counter = 0;
//		for (int i=0; i<openSeds.size(); i++) {
//		    for (int j=0; j<openSeds.get(i).getNumberOfSegments(); j++) {
//			counter++;
//		    }
//		}
//		data = new String[counter][2];
//		
//		// populate the table with the Sed ID + Segment ID, and the redshifts
//		for (int i=0; i<openSeds.size(); i++) {
//		    
//		    ExtSed sed = openSeds.get(i);
//		    
//		    for (int j=0; j<sed.getNumberOfSegments(); j++) {
//			Segment seg = sed.getSegment(j);
//			String id = sed.getId()+": "+seg.getTarget().getName().getValue();
//			
//			String redshift = null;
//
//			java.util.List<? extends Param> params = seg.getCustomParams();
//			for (Param param : params) {
//			    if (param.getName().equals("iris:final redshift")) {
//				    redshift = param.getValue();
//			    }
//			}
//
//			if (redshift == null) {
//			    redshift = (String) sed.getAttachment(ORIG_REDSHIFT);
//			}
//
//			data[j][0] = id;
//			data[j][1] = redshift;
//		    }
//		
//		}
//	    }
	}

	@Override
	public int getRowCount() {
	    return data.length;
	}

	@Override
	public int getColumnCount() {
	    return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
	    return data[row][column];
	}
	
	@Override
	public String getColumnName(int col) {
	    return columnNames[col];
	}
	
	@Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
	    return (columnIndex == 1);
        }
	
	@Override
	public void setValueAt(Object value, int row, int col) {
	    this.data[row][col] = (String) value;
	}
	
    }
    
    private static boolean isNumeric(Object str) {
	NumberFormat formatter = NumberFormat.getInstance();
	ParsePosition pos = new ParsePosition(0);
	formatter.parse((String) str, pos);
	return str.toString().length() == pos.getIndex();
    }

}
