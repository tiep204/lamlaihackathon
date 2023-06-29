package ra.run;

import ra.config.Inputmethods;
import ra.controller.CatalogController;
import ra.controller.ProductController;
import ra.model.Product;
import ra.service.ProductService;

import java.util.Collections;
import java.util.Comparator;

public class ProductManager {
    private ProductController productController;
    private CatalogController catalogController;
    static ProductService productService = new ProductService();

    public ProductManager(ProductController productController, CatalogController catalogController) {
        this.productController = productController;
        this.catalogController = catalogController;
        while (true) {
            System.out.println("********************PRODUCT-MANAGEMENT********************");
            System.out.println("1. Nhập số sản sản phẩm và nhập thông tin sản phẩm");
            System.out.println("2. Hiển thị thông tin các sản phẩm");
            System.out.println("3. Sắp xếp sản phẩm theo giá giảm dần");
            System.out.println("4. Xóa sản phẩm theo mã");
            System.out.println("5. Tìm kiếm sách theo tên sách");
            System.out.println("6. Thay đổi thông tin của sách theo mã sách");
            System.out.println("7. Quay lại");
            System.out.print("Mời bạn lựa chọn: ");
            int choose = Inputmethods.getInteger();
            switch (choose) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    showListProduct();
                    break;
                case 3:
                    sortProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProductByName();
                    break;
                case 6:
                    editProduct();
                    break;
                case 7:
                    return;
                default:
                    break;
            }
        }
    }

    public void addNewProduct() {
        System.out.print("Bạn muốn nhập vào nhiêu sản phẩm: ");
        int n = Inputmethods.getInteger();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i + 1));
            Product product = new Product();
            System.out.println("Mời bạn nhập Id sản phẩm ");
            while (true) {
                product.setProductId(Inputmethods.getString());
                if (product.getProductId().matches("^P\\d{4}$")) {
                    break;
                } else {
                    System.err.println("Vui lòng nhập id sản phẩm có ký tự P và 4 ký tự số");
                }
            }
            product.inputData(catalogController.getAll());
            productController.save(product);
        }
    }

    public void showListProduct() {
        if (productController.getAll().size() == 0) {
            System.err.println("Không có sản phẩm nào");
            return;
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-25s %-20s %-30s %-15s %-20s %-10s \n", "ID", "Tên", "Giá", "Mô Tả", "Số lượng", "Danh mục", "Trạng Thái");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        for (Product p : productController.getAll()) {
            p.displayData();
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void deleteProduct() {
        System.out.print("Nhập vào Mã sản phẩm: ");
        String id = Inputmethods.getString();
        productController.delete(id);
    }

    public void searchProductByName() {
        boolean flag = false;
        System.out.print("Nhập vào tên sách bạn muốn tìm: ");
        String text = Inputmethods.getString();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-25s %-20s %-30s %-15s %-20s %-10s \n", "ID", "Tên", "Giá", "Mô Tả", "Số lượng", "Danh mục", "Trạng Thái");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        for (Product p : productController.getAll()) {
            if (p.getProductName().toLowerCase().contains(text.toLowerCase())) {
                p.displayData();
                flag = true;
            }
        }
        if (!flag) {
            System.err.println("Không có sản phẩm nào");
        }
    }

    public void editProduct() {
        System.out.print("Nhập vào Mã sản phẩm: ");
        String id = Inputmethods.getString();
        Product product = productController.findById(id);
        if (product == null) {
            System.err.println("Không có sản phẩm bạn muốn tìm");
            return;
        }
        Product newProduct = new Product();
        newProduct.setProductId(id);
        newProduct.inputData(catalogController.getAll());
        productController.save(newProduct);
    }

    public void sortProduct() {
        productController.sort();
        showListProduct();
    }
}