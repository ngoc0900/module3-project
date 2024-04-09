package entity;

import java.util.Scanner;

public class Account {
    private int accountId;
    private String userName;
    private String password;
    private Boolean permission;
    private Boolean accountStatus;

    public Account() {
    }

    public Account(int accountId, String userName, String password, Boolean permission, Boolean accountStatus) {
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.accountStatus = accountStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void inputAccount(Scanner scanner){
        boolean check = false;
        do {
            System.out.println("Nhập vào tên người dùng ");
            String name = scanner.nextLine();
            if (!name.isEmpty() && name.length() >= 2 && name.length() <= 30){
                check = true;
                userName = name;
            } else {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        }while (!check);
        do {
            System.out.println("Nhập vào password");
            String password1 = scanner.nextLine();
            if (!password1.isEmpty() && password1.length() >= 3 && password1.length() <= 30){
                check = true;
                password = password1;
            } else {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        }while (!check);

        do {
                System.out.println("Nhập vào tư cách đăng nhập (true: user, false: admin), mặc định là true nếu không nhập:");
                String permissionInput = scanner.nextLine().trim().toLowerCase();
                if (permissionInput.isEmpty() || permissionInput.equals("true") || permissionInput.equals("false")){
                    check = true;
                    permission = permissionInput.isEmpty() ? true : Boolean.parseBoolean(permissionInput);
                } else {
                    check = false;
                    System.err.println("Vui lòng nhập 'true' hoặc 'false'.");
                }
        }while (!check);

        do {
                System.out.println("Nhập vào trạng thái tài khoản (true: Hoạt động, false: Không hoạt động), mặc định là true nếu không nhập:");
                String statusInput = scanner.nextLine().trim().toLowerCase();
                if (statusInput.isEmpty() || statusInput.equals("true") || statusInput.equals("false")){
                    check = true;
                    accountStatus = statusInput.isEmpty() ? true : Boolean.parseBoolean(statusInput);
                } else {
                    check = false;
                    System.err.println("Vui lòng nhập 'true' hoặc 'false'.");
                }
        }while (!check);

    }

    public void displayAccount(){
        System.out.printf("Mã tài khoản: %d | Tên tài khoản: %s | ",this.accountId,this.userName);
        System.out.printf("Quyền tài khoản: %s | ", (this.permission ? "User" : "Admin"));
        System.out.printf("Trạng thái: %s \n", (this.accountStatus ? "Hoạt động" : "Không hoạt động"));
        System.out.println("-------------------------------------------------------------------------------------------------");
    }




}
