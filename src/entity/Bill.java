package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Bill {
    private int billId;
    private String billCode;
    private Boolean billType;
    private int accountId;
    private Date created;
    private Date authDate;
    private Boolean billStatus;

    public Bill() {
    }

    public Bill(int billId, String billCode, Boolean billType, int accountId, Date created, Date authDate, Boolean billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.accountId = accountId;
        this.created = created;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Boolean getBillType() {
        return billType;
    }

    public void setBillType(Boolean billType) {
        this.billType = billType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Boolean getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Boolean billStatus) {
        this.billStatus = billStatus;
    }

    public static final Scanner scanner = new Scanner(System.in);

    public void inputData(Scanner scanner){

    }

    public void inputCode() {
        boolean check = false;
        do {
            System.out.println("Mời nhập mã code");
            String code = scanner.nextLine();
            if (!code.isEmpty() && code.length() <= 10) {
                check = true;
                billCode = code;
            } else {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        } while (!check);
    }
    public void inputType(){
        boolean check = false;
        do {
            System.out.println("Mời nhập loại phiếu");
            String type = scanner.nextLine().trim().toLowerCase();
            if (!type.isEmpty() || type.equals("true") || type.equals("false")){
                check = true;
                billStatus = Boolean.parseBoolean(type);
            } else {
                System.err.println("Vui lòng nhập 'true' hoặc 'false'.");
            }
        } while (!check);
    }

    public void inputAccontId(){

    }

    public void inputCreated(){
        System.out.println("Nhập vào ngày tạo(dd/mm/yyyy): ");
        String created = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            java.util.Date utilDate =  formatter.parse(created);
            long timeInMillis = utilDate.getTime();
            java.sql.Date sqlDate = new  java.sql.Date(timeInMillis);
            this.created  = sqlDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
