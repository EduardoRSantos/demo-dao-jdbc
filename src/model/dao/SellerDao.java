package model.dao;

import java.util.List;
import model.entities.Seller;

public interface SellerDao {
    
    void instet(Seller obg);
    void update(Seller obg);
    void deleteById(Integer id);
    void findById(Integer id);
    List<Seller> findAll();
   
}
