/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectdb.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import finalprojectdb.tables.ResultSetTableModel;


/**
 *
 * @author MichaelVMT
 */
public class dbConnect {
    private ResultSetTableModel tableData;
    
    public void runConnection(String host, String database, String user, String password) throws Exception{
        this.driverTest();
        
        Connection con = this.makeConnection(host, database, user, password);
        
        this.execQuery(con, "SELECT * FROM tv");
        
        con.close();
    }
    
    public void insertRow(String host, String database, String user, String password,
            String category, String name, int episode) throws Exception{
        this.driverTest();
        
        Connection con = this.makeConnection(host, database, user, password);
        
        this.execInsert(con, category, name, episode);
        
        con.close();
    }
    
    public void updateRow(String host, String database, String user, String password,
            int id, String category, String name, int episode) throws Exception{
        this.driverTest();
        
        Connection con = this.makeConnection(host, database, user, password);
        
        this.execUpdate(con, id, category, name, episode);
        
        con.close();
    }
    
    protected void driverTest() throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL Driver Found");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found ...");
            throw (e);
        }
    }
    
    protected Connection makeConnection(String host, String database, String user, String password) throws Exception{
        try{
            String url = "jdbc:mysql://" + host + ":3306/" + database;
            Connection con = DriverManager.getConnection(url, user, password);
            
            System.out.println("Connected to " + url);
            return con;
        } catch (java.sql.SQLException e){
            throw e;
        } 
    }
    
    protected void execQuery(Connection con,String sqlStatement) throws Exception{
        try{
            Statement cs = con.createStatement();
            ResultSet rs = cs.executeQuery(sqlStatement);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int colCount = rsmd.getColumnCount();
            
            this.tableData = new ResultSetTableModel();
            
            for(int i = 1; i < colCount + 1; i++){
                tableData.addColumnName(rsmd.getColumnName(i));
            }
            
            while(rs.next()){
                List row = new ArrayList<Object>();
                
                for(int i = 1; i <= colCount; i++){
                    row.add(rs.getObject(i));
                }
                tableData.addRow(row.toArray());
            }
            
            cs.close();
        } catch(Exception e){
            System.out.println("Error in excecuting Query: \"" + sqlStatement + "\" ");
            throw e;
        }
    }
    
    protected void execInsert(Connection con, String category, String name, int episode) throws Exception{
        String sqlStatement = "INSERT INTO tv (ID, Category, Name, Episode) "
                + "VALUES (NULL,'" + category + "', '" + name + "', '" + episode + "')";
        try{
            Statement cs = con.createStatement();
            cs.executeUpdate(sqlStatement);
        } catch (SQLException e){
            throw e;
        }
    }
    
    protected void execUpdate(Connection con, int id, String category, String name, int episode) throws Exception{
        String sqlStatement = "UPDATE tv SET `Category` = '" + category
                + "', `Name` = '" + name + "', `Episode` = '" + episode + "' WHERE `tv`.`ID` = " + id;
        
        try{
            Statement cs = con.createStatement();
            cs.executeUpdate(sqlStatement);
        } catch (SQLException e){
            throw e;
        }
    }
    
    public ResultSetTableModel getTableModel(){
        return tableData;
    }
}
