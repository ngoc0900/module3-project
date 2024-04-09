package program;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import entity.Account;
import entity.Product;

import java.util.List;
import java.util.Scanner;

public class Program {
    public static final Scanner scanner = new Scanner(System.in);
    public static final AccountDAO accountDAO = new AccountDAOImpl();
    public static final Account account = new Account();
    public static final ProductDAO productDao = new ProductDAOImpl();
    public static final Product product = new Product();


    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        do {
            try {
                System.out.println("**************** CHÀO MỪNG ĐẾN VỚI ****************");
                System.out.println("1. Đăng kí");
                System.out.println("2. Đăng nhập");
                System.out.println("3. Thoát");
                System.out.println("Mời bạn chọn : ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Mời nhập thông tin đăng kí");
                        account.inputAccount(scanner);
                        if (accountDAO.signIn(account)) {
                            System.out.println("Đăng kí thành công ");
                        } else {
                            System.err.println("Đăng kí thất bại");
                        }
                        break;

                    case 2:
                        System.out.println("Mời nhập vào tên đăng nhập");
                        String userName = scanner.nextLine();
                        System.out.println("Mời nhập vào password");
                        String password = scanner.nextLine();
                        Account account1 = accountDAO.login(userName, password);
                        if (account1 != null) {
                            if (account1.getPermission()) {
                                userManagement();
                            } else {
                                adminManagement(account1.getUserName());
                            }
                        } else {
                            System.err.println("Sai thông tin đăng nhập");
                        }
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Mời chọn lại từ 1-3 !");
                }
            } catch (Exception e) {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }

        } while (true);
    }

    public static void adminManagement(String username) {
        do {
            try {
                System.out.println("XIN CHÀO ADMIN " + username);
                System.out.println("**************** WAREHOUSE MANAGEMENT ****************");
                System.out.println("1. Quản lý sản phẩm");
                System.out.println("2. Quản lý tài khoản");
                System.out.println("3. Quản lý phiếu nhập");
                System.out.println("4. Quản lý phiếu xuất");
                System.out.println("5. Thoát");
                System.out.println("Mời bạn chọn : ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        quanLyProduct();
                        break;
                    case 2:
                        quanLyAccount();
                        break;
                    case 3:
                        quanLyPhieuNhap();
                        break;
                    case 4:
                        quanLyPhieuXuat();
                        break;
                    case 5:
//                    System.exit(0);
                        menu();
                        break;
                    default:
                        System.out.println("Mời chọn lại từ 1-5 !");
                }
            } catch (Exception e) {
                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
            }

        } while (true);
    }

    public static void quanLyProduct() {
        do {
            try {
                System.out.println("**************** PRODUCT MANAGEMENT ****************");
                System.out.println("1. Danh sách sản phẩm");
                System.out.println("2. Thêm mới sản phẩm");
                System.out.println("3. Cập nhật sản phẩm");
                System.out.println("4. Tìm kiếm sản phẩm");
                System.out.println("5. Cập nhật trạng thái sản phẩm");
                System.out.println("6. Thoát");
                System.out.println("Mời bạn chọn : ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println(" Danh sách sản phẩm");
                        List<Product> products = productDao.fillAll();
                        if (productDao.fillAll().isEmpty()) {
                            System.out.println("Danh sách trống");
                            return;
                        }
                        for (Product product : products) {
                            product.displayProduct();
                        }
                        break;
                    case 2:
                        System.out.println("Thêm mới sản phẩm");
                        System.out.println("Bạn muốn thêm bao nhiêu sản phẩm: ");
                        int n = Integer.parseInt(scanner.nextLine());
                        for (int i = 0; i < n; i++) {
                            product.inputData(scanner, i + 1);
                        }
                        if (productDao.save(product)) {
                            System.out.println("Tạo tài khoản thành công ");
                        } else {
                            System.err.println("Tạo tài khoản thất bại");
                        }
                        break;
                    case 3:
                        System.out.println("Cập nhật sản phẩm");
                        System.out.println("Nhập vào mã sản phẩm bạn muốn sửa");
                        String id = scanner.nextLine();
                        Product product1 = productDao.findById(id);
                        if (product1 == null) {
                            System.err.println("Rất tiêc không tìm thấy sản phẩm nào có id = " + id);
                            return;
                        }
                        System.out.println("Có phải bạn muốn cập nhật sản phẩm này không ? ");
                        product1.displayProduct();
                        System.out.println("Nhập vào thông tin mới ");
                        product1.editData();
                        productDao.save(product1);
                        System.out.println("Cập nhật thành công ");
                        break;
                    case 4:
                        System.out.println("Mời bạn nhập tên danh mục muốn tìm kiếm");
                        String keyword = scanner.nextLine();
                        List<Product> list = productDao.searchByName(keyword);
                        int count = 0;
                        for (Product product2 : list) {
                            product2.displayProduct();
                            count++;
                        }
                        if (count == 0) {
                            System.err.println("Không tìm thấy danh mục muốn tìm");
                        }
                        break;
                    case 5:
                        System.out.println("Cập nhật trạng thái sản phẩm");
                        System.out.println("Nhập vào mã sản phẩm bạn muốn sửa");
                        String id1 = scanner.nextLine();
                        Product product3 = productDao.findById(id1);
                        if (product3 == null) {
                            System.err.println("Rất tiêc không tìm thấy sản phẩm nào có id = " + id1);
                            return;
                        }
                        System.out.println("Có phải bạn muốn cập nhật sản phẩm này không ? ");
                        product3.displayProduct();
                        System.out.println("Nhập vào thông tin mới ");
                        System.out.println("Nhập vào trạng thái sản phẩm (true: Hoạt động, false: Không hoạt động),:");
//                        System.out.println("Chọn trạng thái tài khoản");
//                        System.out.println("1. Hoạt động (true)");
//                        System.out.println("2. Không hoạt động (false)");
//                        System.out.print("Nhập lựa chọn của bạn: ");
//                        boolean newStatus;
//                        int choice1 = scanner.nextInt();
//                        switch (choice1) {
//                            case 1:
//                                newStatus = true;
//                                break;
//                            case 2:
//                                newStatus = false;
//                                break;
                            product3.setProductStatus(Boolean.parseBoolean(scanner.nextLine()));
                            if (productDao.changeStatusById(product3)) {
                                System.out.println("Cập nhật trạng thái tài khoản thành công");
                            } else {
                                System.err.println("Cập nhật thất bại !");
                            }
                            break;
                            case 6:
                                return;
                            default:
                                System.out.println("Mời chọn lại từ 1-6 !");
                        }
                }catch(Exception e){
//                System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
                }

            } while (true) ;
        }


        public static void quanLyAccount () {
            do {
                try {
                    System.out.println("**************** ACCOUNT MANAGEMENT ****************");
                    System.out.println("1. Danh sách tài khoản");
                    System.out.println("2. Tạo tài khoản mới");
                    System.out.println("3. Cập nhật trạng thái tài khoản");
                    System.out.println("4. Thoát");
                    System.out.println("Mời bạn chọn : ");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println(" Danh sách tài khoản");
                            List<Account> accounts = accountDAO.getAllAccount();
                            for (Account account1 : accounts) {
                                account1.displayAccount();
                            }
                            break;
                        case 2:
                            System.out.println(" Tạo tài khoản mới");
                            System.out.println("Mời nhập thông tin tài khoản");
                            account.inputAccount(scanner);
                            if (accountDAO.signIn(account)) {
                                System.out.println("Tạo tài khoản thành công ");
                            } else {
                                System.err.println("Tạo tài khoản thất bại");
                            }
                            break;
                        case 3:
                            System.out.println("Mời bạn nhập vào id cần sửa ");
                            int id = Integer.parseInt(scanner.nextLine());
                            Account acountEdit = accountDAO.findById(id);
                            if (acountEdit == null) {
                                System.err.println("Rất tiêc không tìm thấy tài khoản nào có id = " + id);
                            } else {
                                System.out.println("Có phải bạn muốn thay đổi trạng thái tài khoản này không ? ");
                                acountEdit.displayAccount();
                                System.out.println("Nhập vào thông tin mới ");
                                System.out.println("Nhập vào trạng thái tài khoản (true: Hoạt động, false: Không hoạt động):");
                                acountEdit.setAccountStatus(Boolean.parseBoolean(scanner.nextLine()));
                                if (accountDAO.changeStatusAccount(acountEdit)) {
                                    System.out.println("Cập nhật trạng thái tài khoản thành công");
                                } else {
                                    System.err.println("Cập nhật thất bại !");
                                }
                            }
                            break;
                        case 4:
//                        System.exit(0);
//                            adminManagement();
//                            break;
                            return;
                        default:
                            System.out.println("Mời chọn lại từ 1-4 !");
                    }
                } catch (Exception e) {
                    System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
                }

            } while (true);
        }

        public static void quanLyPhieuNhap () {
            do {
                try {
                    System.out.println("**************** RECEIPT MANAGEMENT ****************");
                    System.out.println("1. Danh sách phiếu nhập");
                    System.out.println("2. Tạo phiếu nhập");
                    System.out.println("3. Cập nhật thông tin phiếu nhập");
                    System.out.println("4. Chi tiết phiếu nhập");
                    System.out.println("5. Duyệt phiếu nhập");
                    System.out.println("6. Tìm kiếm phiếu nhập");
                    System.out.println("7. Thoát ");
                    System.out.println("Mời bạn chọn :  ");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
//                        System.exit(0);
//                            adminManagement();
//                            break;
                            return;
                        default:
                            System.out.println("Mời chọn lại từ 1-7 !");
                    }
                } catch (Exception e) {
                    System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
                }

            } while (true);
        }

        public static void quanLyPhieuXuat () {
            do {
                try {
                    System.out.println("**************** BILL MANAGEMENT ****************");
                    System.out.println("1. Danh sách phiếu xuất");
                    System.out.println("2. Tạo phiếu xuất");
                    System.out.println("3. Cập nhật thông tin phiếu xuất");
                    System.out.println("4. Chi tiết phiếu xuất");
                    System.out.println("5. Duyệt phiếu xuất");
                    System.out.println("6. Tìm kiếm phiếu xuất");
                    System.out.println("7. Thoát");
                    System.out.println("Mời bạn chọn : ");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
//                        System.exit(0);
//                            adminManagement();
//                            break;
                            return;
                        default:
                            System.out.println("Mời chọn lại từ 1-7 !");
                    }
                } catch (Exception e) {
                    System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
                }

            } while (true);
        }


        public static void userManagement () {

            do {
                try {
                    System.out.println("XIN CHÀO USER " + account.getUserName());
                    System.out.println("**************** WAREHOUSE MANAGEMENT ****************");
                    System.out.println("1. Danh sách phiếu nhập theo trạng thái");
                    System.out.println("2. Tạo phiếu nhập");
                    System.out.println("3. Cập nhật phiếu nhập");
                    System.out.println("4. Tìm kiếm phiếu nhập");
                    System.out.println("5. Danh sách phiếu xuất theo trạng thái");
                    System.out.println("6. Tạo phiếu xuất");
                    System.out.println("7. Cập nhật phiếu xuất");
                    System.out.println("8. Tìm kiếm phiếu xuất");
                    System.out.println("9. Thoát");
                    System.out.println("Mời bạn chọn : ");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
//                    System.exit(0);
                            menu();
                            break;
                        default:
                            System.out.println("Mời chọn lại từ 1-9 !");
                    }
                } catch (Exception e) {
                    System.err.println("Định dạng không hợp lệ, vui lòng nhập lại !");
                }

            } while (true);
        }
    }
