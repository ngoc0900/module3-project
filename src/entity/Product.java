package entity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Product {
    private String productId;
    private String productName;
    private String manufacturer;   // Nhà sản xuất
    private Date created;          // Ngày tạo
    private short batch;            // Lô chứa sản phẩm
    private int quantity;          // Số lượng sản phẩm
    private Boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Date created, short batch, int quantity, Boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public short getBatch() {
        return batch;
    }

    public void setBatch(short batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    public static final Scanner scanner = new Scanner(System.in);

    public void inputData(Scanner scanner, int i) {
        System.out.println("Sản phẩm thứ "+ i );
        inputPrdId();
        inputPrdName();
        inputNSX();
        inputCreated();
        inputBatch();
        inputSoLuong();
        inputPrdStatus();
    }

    public void editData(){
        inputPrdName();
        inputNSX();
        inputCreated();
        inputBatch();
        inputSoLuong();
        inputPrdStatus();
    }



    public void inputPrdId() {
        boolean check = false;
        do {
            System.out.println("Mời nhập mã sản phẩm");
            String id = scanner.nextLine();
            if (!id.isEmpty() && id.length() <= 5) {
                check = true;
                productId = id;
            } else {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        } while (!check);
    }

    public void inputPrdName() {
        boolean check = false;
        do {
            System.out.println("Mời nhập tên sản phẩm");
            String name = scanner.nextLine();
            if (!name.isEmpty() && name.length() <= 150) {
                check = true;
                productName = name;
            } else {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        } while (!check);
    }

    public void inputNSX(){
        boolean check = false;
        do {
            System.out.println("Mời nhập nhà sản xuất");
            String  nsx = scanner.nextLine();
            if (!nsx.isEmpty() && nsx.length() <= 200) {
                check = true;
                manufacturer = nsx;
            } else {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        } while (!check);
    }



    public void inputCreated(){
        boolean check = false;
        do {
            System.out.println("Nhập vào ngày tạo(dd/mm/yyyy): ");
            String created = scanner.nextLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                java.util.Date utilDate =  formatter.parse(created);
                long timeInMillis = utilDate.getTime();
                java.sql.Date sqlDate = new  java.sql.Date(timeInMillis);
                this.created  = sqlDate;
            } catch (ParseException e) {
                check = true;
                throw new RuntimeException(e);
            }
        }while (check);
    }

    public void inputBatch(){
        do {
            try {
                System.out.println("Mời nhập lô chứa sản phẩn");
                short loSp = Short.parseShort(scanner.nextLine());
                batch = loSp;
                break;
            }catch (Exception e){
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }
        } while (true);
    }
    public void inputSoLuong(){

        System.out.print("Nhập vào số lượng sản phẩm (mặc định là 0 nếu không nhập): ");
        String input = scanner.nextLine();

        // Nếu người dùng không nhập gì, trả về 0
        if (input.isEmpty()) {
            this.quantity = 0;
            return ;
        }
        // Chuyển đổi chuỗi thành số nguyên
        try {
            this.quantity =  Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Số lượng nhập không hợp lệ. Sẽ được mặc định là 0.");

        }

    }

    public void inputPrdStatus(){
        boolean check = false;
        do {
            System.out.println("Nhập vào trạng thái sản phẩm (true: Hoạt động, false: Không hoạt động), mặc định là true nếu không nhập:");
            String statusInput = scanner.nextLine().trim().toLowerCase();
            if (statusInput.isEmpty() || statusInput.equals("true") || statusInput.equals("false")){
                check = true;
                productStatus = statusInput.isEmpty() ? true : Boolean.parseBoolean(statusInput);
            } else {
                System.err.println("Vui lòng nhập 'true' hoặc 'false'.");
            }
        }while (!check);
    }

    public void displayProduct(){
        System.out.printf("Mã sản phẩm: %s | Tên sản phẩm: %s | Nhà sản xuất: %s | ",productId,productName,manufacturer);
        System.out.print("Ngày tạo:  "+ created);
        System.out.print(" | Lô chứa sản phẩm: "+ batch);
        System.out.printf("  | Số lượng sản phẩm: %d | Trạng thái: %s \n",quantity, (this.productStatus ? "Hoạt động" : "Không hoạt động"));
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }
}
