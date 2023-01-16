package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        PreparedStatement st = null;
        ResultSet rs = null;
       
        
        try{
            st = conn.prepareStatement(
                "INSERT INTO seller "
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, obg.getName());
            st.setString(2, obg.getEmail());
            st.setDate(3, new java.sql.Date(obg.getBirthDate().getTime()));
            st.setDouble(4, obg.getBaseSalary());
            st.setInt(5, obg.getDepartment().getId());
            
            int rows = st.executeUpdate();
            
            if(rows > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obg.setId(id);
                }
            }else {
            throw new DbException("Unexpected error! No rows affected");
        }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.CloseStatement(st);
            DB.CloseResultSet(rs);
        }
        
    }

    @Override
    public void update(Seller obg) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
            "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?");
            
            st.setString(1, obg.getName());
            st.setString(2, obg.getEmail());
            st.setDate(3, new java.sql.Date(obg.getBirthDate().getTime()));
            st.setDouble(4, obg.getBaseSalary());
            st.setInt(5, obg.getDepartment().getId());
            st.setInt(6, obg.getId());
            
            st.executeUpdate();
            
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.CloseStatement(st);
            DB.CloseResultSet(rs);
        }
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
               Department dep = instantiateDepartment(rs);
               Seller obg = instantiateSeller(rs, dep);
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

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
            Department dep = new Department();
            dep.setId(rs.getInt("DepartmentId"));
            dep.setName(rs.getString("DepName"));
            return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {

            Seller obg = new Seller();
            obg.setId(rs.getInt("Id"));
            obg.setName(rs.getString("Name"));
            obg.setEmail(rs.getString("Email"));
            obg.setBaseSalary(rs.getDouble("BaseSalary"));
            obg.setBirthDate(rs.getDate("BirthDate"));
            obg.setDepartment(dep);
            return obg;
    }

    @Override
    public List<Seller> FindByDepartment(Department department) {
       PreparedStatement st = null;
       ResultSet rs = null;
       
       try{
           st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE DepartmentId = ? "
                    +"ORDER BY Name"
            );
           st.setInt(1, department.getId());
           
           rs = st.executeQuery();
           
           List<Seller> list = new ArrayList<>();
           Map<Integer, Department> map = new HashMap<>();
           
           while(rs.next()){

               Department dep = map.get(rs.getInt("DepartmentId"));
               
               if(dep == null){
                dep = instantiateDepartment(rs);
                map.put(rs.getInt("DepartmentId"), dep);
               }
               
               Seller obg = instantiateSeller(rs, dep);
               list.add(obg);
               
           }
           return list;
       
       }catch(SQLException e){
           
       }finally{
           DB.CloseStatement(st);
           DB.CloseResultSet(rs);
       }
        return null;
    }
    
    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
       ResultSet rs = null;
       
       try{
           st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"ORDER BY Name"
            );
     
           rs = st.executeQuery();
           
           List<Seller> list = new ArrayList<>();
           Map<Integer, Department> map = new HashMap<>();
           
           while(rs.next()){

               Department dep = map.get(rs.getInt("DepartmentId"));
               
               if(dep == null){
                dep = instantiateDepartment(rs);
                map.put(rs.getInt("DepartmentId"), dep);
               }
               
               Seller obg = instantiateSeller(rs, dep);
               list.add(obg);
               
           }
           return list;
       
       }catch(SQLException e){
           
       }finally{
           DB.CloseStatement(st);
           DB.CloseResultSet(rs);
       }
        return null;
    }
    
}
