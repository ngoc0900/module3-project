package dao;

import entity.Account;
import util.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public boolean signIn(Account account) {
        Connection connection = ConnectionDB.opentConnection();
        try {
            String sql = "INSERT INTO ACCOUNT(User_name,Password,Permission,Acc_status) VALUE (?,?,?,?) ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,account.getUserName());
            statement.setString(2,account.getPassword());
            statement.setBoolean(3,account.getPermission());
            statement.setBoolean(4,account.getAccountStatus());
            int check = statement.executeUpdate();
            if(check>0){
                return true;
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.err.println("Tên đăng kí bị trùng");
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Account login(String userName, String password) {
        Connection connection = ConnectionDB.opentConnection();
        Account account = new Account();
        try {
            String sql = "SELECT * FROM ACCOUNT WHERE User_name = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                return null;
            }
            while (resultSet.next()){
                account.setAccountId(resultSet.getInt("Acc_id"));
                account.setUserName(resultSet.getString("User_name"));
                account.setPassword(resultSet.getString("Password"));
                account.setPermission(resultSet.getBoolean("Permission"));
                account.setAccountStatus(resultSet.getBoolean("Acc_status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return account;
    }

    @Override
    public List<Account> getAllAccount() {
        Connection connection = ConnectionDB.opentConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            // thực hiện câu lệnh SQL thông qua PreparedStatement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ACCOUNT");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Account account = new Account();
                account.setAccountId(resultSet.getInt("Acc_id"));
                account.setUserName(resultSet.getString("User_name"));
                account.setPassword(resultSet.getString("Password"));
                account.setPermission(resultSet.getBoolean("Permission"));
                account.setAccountStatus(resultSet.getBoolean("Acc_status"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return accounts;

    }

    @Override
    public Account findById(int accountId) {
        Connection connection = ConnectionDB.opentConnection();
        Account account = new Account();
        try {
            String sql = "SELECT * FROM ACCOUNT WHERE Acc_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,accountId);
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                return null;
            }
            while (resultSet.next()){
                account.setAccountId(resultSet.getInt("Acc_id"));
                account.setUserName(resultSet.getString("User_name"));
                account.setPassword(resultSet.getString("Password"));
                account.setPermission(resultSet.getBoolean("Permission"));
                account.setAccountStatus(resultSet.getBoolean("Acc_status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return account;
    }

    @Override
    public boolean changeStatusAccount(Account account) {
        Connection connection = ConnectionDB.opentConnection();
        try {
            String sql = "UPDATE ACCOUNT SET Acc_status = ? WHERE Acc_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1,account.getAccountStatus());
            statement.setInt(2,account.getAccountId());
            int check = statement.executeUpdate();
            if(check > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }
}
