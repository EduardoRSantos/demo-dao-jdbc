package model.dao.impl;

import db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

    private Connection conn;
    
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void insert(Seller obg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Seller obg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Seller findById(Integer id) {
       PreparedStatement st = null;
       ResultSet rs = null;
       
       try{
           st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE seller.Id = ?"
            );
           st.setInt(1, id);
           rs = st.executeQuery();
           if(rs.next()){
               Department dep = new Department();
               dep.setId(rs.getInt("DepartmentId"));
               dep.setName(rs.getString("DepName"));
               
               Seller obg = new Seller();
               obg.setId(rs.getInt("Id"));
               obg.setName(rs.getString("Name"));
               obg.setEmail(rs.getString("Email"));
               obg.setBaseSalary(rs.getDouble("BaseSalary"));
               obg.setBirthDate(rs.getDate("BirthDate"));
               obg.setDepartment(dep);
               return obg;
           }
           return null;
  
       }catch(SQLException e){
           
       }finally{
           DB.CloseStatement(st);
           DB.CloseResultSet(rs);
       }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
