package dao;

import entity.Account;

import java.util.List;

public interface AccountDAO {
    boolean signIn(Account account);
    Account login(String userName, String password);
    List<Account> getAllAccount();
    Account findById(int accountId);
    boolean changeStatusAccount(Account account);
}
