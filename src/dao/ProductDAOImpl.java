package dao;

import entity.Account;
import entity.Product;
import util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static program.Program.account;
import static program.Program.product;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> fillAll() {
        Connection connection = ConnectionDB.opentConnection();
        List<Product> products = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call findAllProduct()}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("Product_Id"),
                        resultSet.getString("Product_Name"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getDate("Created"),
                        resultSet.getShort("Batch"),
                        resultSet.getInt("Quantity"),
                        resultSet.getBoolean("Product_Status")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Product findById(String idFind) {
        Connection connection = ConnectionDB.opentConnection();
        Product product = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call findByIdProduct(?)}");
            callableStatement.setString(1, idFind);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product(
                        resultSet.getString("Product_Id"),
                        resultSet.getString("Product_Name"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getDate("Created"),
                        resultSet.getShort("Batch"),
                        resultSet.getInt("Quantity"),
                        resultSet.getBoolean("Product_Status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return product;
    }

    @Override
    public boolean save(Product product) {
        Connection connection = ConnectionDB.opentConnection();
        try {
            CallableStatement callableStatement;
            if (findById(product.getProductId()) == null) {
                // Thêm mới
                callableStatement = connection.prepareCall("{call addNewProduct(?,?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductId());
                callableStatement.setString(2, product.getProductName());
                callableStatement.setString(3, product.getManufacturer());
                callableStatement.setDate(4, product.getCreated());
                callableStatement.setShort(5, product.getBatch());
                callableStatement.setInt(6, product.getQuantity());
                callableStatement.setBoolean(7, product.getProductStatus());
                int check = callableStatement.executeUpdate();
                if (check > 0) {
                    return true;
                }
            } else {
                // cập nhật thông tin
                callableStatement = connection.prepareCall("{call editProduct(?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductName());
                callableStatement.setString(2, product.getManufacturer());
                callableStatement.setDate(3, product.getCreated());
                callableStatement.setShort(4, product.getBatch());
                callableStatement.setInt(5, product.getQuantity());
                callableStatement.setBoolean(6, product.getProductStatus());
                int check = callableStatement.executeUpdate();
                if (check > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
//            System.err.println("Tên đăng kí bị trùng");
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean changeStatusById(Product product) {
        Connection connection = ConnectionDB.opentConnection();
        try {
            String sql = "UPDATE PRODUCT SET Product_Status = ? WHERE Product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1,product.getProductStatus());
            statement.setString(2,product.getProductId());
            int check = statement.executeUpdate();
            if(check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<Product> searchByName(String prdName) {
        Connection connection = ConnectionDB.opentConnection();
        List<Product> products = new ArrayList<>();
        try {
            // thực hiện lệnh SQL thông qua preparedStament
            String sql = "SELECT * FROM PRODUCT WHERE Product_name LIKE ? LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + prdName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getString("Product_Id"));
                product.setProductName(resultSet.getString("Product_Name"));
                product.setManufacturer(resultSet.getString("Manufacturer"));
                product.setCreated(resultSet.getDate("Created"));
                product.setBatch(resultSet.getShort("Batch"));
                product.setQuantity(resultSet.getInt("Quantity"));
                product.setProductStatus(resultSet.getBoolean("Product_Status"));
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return products;
    }
}
