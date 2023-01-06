package testapp;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;


public class tablemanager{
	
	JTable jTable;
	DefaultTableModel model;
	
	tablemanager(JTable jTable,DefaultTableModel model){
		this.jTable=jTable;
		this.model=model;
	}
	public void addcol() {
		model.addColumn(" ");
	}
	public void addrow() {
		model.addRow(new Object[]{" "," "," "});
	}
	public void delcol() {
		int selectedCol = jTable.getSelectedColumn();
        int finalCol = (selectedCol==-1)?(model.getColumnCount()-1):selectedCol;
        jTable.removeColumn(jTable.getColumnModel().getColumn(finalCol));
        model.setColumnCount(model.getColumnCount()-1);
	}
	public void delrow() {
		int selectedRow = jTable.getSelectedRow();
        int finalRow = (selectedRow==-1)?(model.getRowCount()-1):selectedRow;
        model.removeRow(finalRow);
	}
}