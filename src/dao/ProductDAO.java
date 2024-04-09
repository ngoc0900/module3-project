package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {
 List<Product> fillAll();
 Product findById(String idFind);
 boolean save(Product product);
 boolean changeStatusById(Product product);

 List<Product> searchByName(String prdName);
}
