package db;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DB {
        Connection conn = null;
        
    public static Connection ConnectDb(){
        String url = "jdbc:mysql://localhost/coursejdbc";
        String user = "root";
        String password = "";
        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection conn = DriverManager.getConnection(url,user,password);
           return conn;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    public static void CloseConnection(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void CloseStatement(Statement st){
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
        public static void CloseResultSet(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
