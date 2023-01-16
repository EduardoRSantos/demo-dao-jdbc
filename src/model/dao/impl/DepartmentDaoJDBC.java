package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
    private Connection conn;
    
    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Department obg) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        ps = conn.prepareStatement("INSERT INTO department VALUES (default, ?)",Statement.RETURN_GENERATED_KEYS);
        
        ps.setString(1, obg.getName());
        
        ps.executeUpdate();
        
        rs = ps.getGeneratedKeys();
        if(rs.next()){
            int id = rs.getInt(1);
            obg.setId(id);
        }
       }catch(SQLException e){
           throw new DbException(e.getMessage());
       }finally{
           DB.CloseStatement(ps);
           DB.CloseResultSet(rs);
       }
    }
    
    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            ps = conn.prepareStatement("SELECT * FROM department WHERE id = ?");
            
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
            }else{
                throw new DbException("No department found");
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.CloseStatement(ps);
            DB.CloseResultSet(rs);
        }
    }
    
    @Override
    public void update(Department obg) {
        PreparedStatement ps = null;
        
        try{
            
            ps = conn.prepareStatement("UPDATE department set Name = ? WHERE id = ?");
        
            ps.setInt(2, obg.getId());
            ps.setString(1, obg.getName());
            
            ps.executeUpdate();
            
            
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.CloseStatement(ps);
        }
        
    }
        
    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        
        try{
            
            ps = conn.prepareStatement("DELETE FROM department WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.CloseStatement(ps);
        }
    }

    @Override
    public List<Department> findAll() {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            
            ps = conn.prepareStatement("SELECT * FROM department");
            
            rs = ps.executeQuery();
            
            List<Department> list = new ArrayList<>();
            
            while(rs.next()){
                list.add(new Department(rs.getInt("Id"),rs.getString("Name")));
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.CloseStatement(ps);
            DB.CloseResultSet(rs);
        }
        
    }
    
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
            Department dep = new Department();
            dep.setId(rs.getInt("Id"));
            dep.setName(rs.getString("Name"));
            return dep;
    }
}
