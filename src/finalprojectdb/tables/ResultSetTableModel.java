/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectdb.tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author MichaelVMT
 */
public class ResultSetTableModel extends AbstractTableModel{
    
    private final List<Object[]>rows;
    private final List<String>colNames;
    
    public ResultSetTableModel(){
        this.rows = new ArrayList<>();
        this.colNames = new ArrayList<>();
    }
    
    @Override
    public int getRowCount(){
        return rows.size();
    }
    
    @Override
    public int getColumnCount(){
        return colNames.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        return rows.get(rowIndex)[columnIndex];
    }
    
    @Override
    public String getColumnName(int col){
        System.out.println("getColumnName(" + col + ")");
        return colNames.get(col);
    }
    
    public void addRow(Object[] row){
        rows.add(row);
    }
    
    public void addColumnName(String name){
        colNames.add(name);
    }
    
    
    
    
}
