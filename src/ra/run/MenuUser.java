package ra.run;

import ra.config.Inputmethods;
import ra.controller.CartController;
import ra.controller.ProductController;
import ra.model.CartItem;
import ra.model.Product;
import ra.service.CartService;

import java.util.List;

public class MenuUser {
    private ProductController productController;
    private CartController cartController;

    public MenuUser(ProductController productController) {
        this.productController = productController;
        cartController = new CartController();
        while (true) {
            System.out.println("**************************MENU-USER**************************");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Thêm vào giỏ hàng");
            System.out.println("3. Xem tất cả sản phẩm giỏ hàng");
            System.out.println("4. Thay đổi số lượng sản phẩm trong giỏ hàng");
            System.out.println("5. Xóa 1 sản phẩm trong giỏ hàng");
            System.out.println("6. Xóa toàn bộ sản phẩm trong giỏ hàng");
            System.out.println("7. Quay lại");
            System.out.print("Mời bạn lựa chọn: ");
            int choose = Inputmethods.getByte();
            switch (choose) {
                case 1:
                    showListProduct();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    showListCart();
                    break;
                case 4:
                    changeQuantitty();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    deleteAll();
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Vui lòng nhập lại (1 -> 7)");
                    break;
            }
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
            if (p.isStatus()==true){
                p.displayData();
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
    }
    public void addToCart(){
        System.out.println("Nhập id của sp cần mua");
        String idPro = Inputmethods.getString();
        Product p = productController.findById(idPro);
        if(p==null){
            System.err.println("id không tồn tại");
            return;
        }
        if(p.getStock()<=0){
            System.err.println("sản phẩm hêt hàng");
            return;
        }

        System.out.println("Nhập só lương thêm mới");
        // kiểm tra stock còn hay hết
        int quantity;
        while (true) {
             quantity = Inputmethods.getInteger();
            if (quantity <= p.getStock()){
                break;
            }
            System.err.println("số lượng trong kho chỉ còn "+p.getStock()+", vui lòng giảm só lương");
        }
        // cho phép mua
        CartItem cartItem = cartController.findByProductId(idPro);
        if(cartItem==null){
//            thêm mơi
            cartController.save(new CartItem(cartController.getNewCartItemId(),p,p.getProductPrice(),quantity));
        }else {
            // đã tồn tại sản phẩm
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            cartController.changeQuantity(cartItem);
        }
        // giảm stock đi
        p.setStock(p.getStock()-quantity);
        productController.save(p);
    }
    public void showListCart() {
        if (cartController.getAll().size() == 0) {
            System.err.println("Không có sản phẩm nào trong giỏ hàng");
            return;
        }
        double total =0;
        for (CartItem ci : cartController.getAll()) {
            total += ci.getPrice()*ci.getQuantity();
            System.out.println(ci);
        }
        System.out.println("Total : " +total);
    }
    public  void changeQuantitty(){
        System.out.println("Nhập vào itemId muoons thay đổi số lượng");
        int cartItemId = Inputmethods.getInteger();
        CartItem cartItem = cartController.findById(cartItemId);
        if (cartItem==null){
            System.err.println("id không tồn tại");
            return;
        }
        cartItem.setPrice(cartItem.getProduct().getProductPrice());
        System.out.println("Nhập vào số lượng cần thay đổi");
        int newQuantity;
        while (true){
            newQuantity = Inputmethods.getInteger();
            if(newQuantity <= cartItem.getQuantity()+cartItem.getProduct().getStock()){
                Product p = cartItem.getProduct();
                p.setStock(p.getStock()+cartItem.getQuantity()); // trả từ giỏ hàng về kho

                cartItem.setQuantity(newQuantity);
                cartController.changeQuantity(cartItem);
                // giảm stock trong kho

                p.setStock(p.getStock()-newQuantity);
                productController.save(p);
                break;
            }
            System.err.println("tối đa chỉ có thể mua "+ cartItem.getQuantity()+cartItem.getProduct().getStock() + " sp");
        }

    }
    public  void delete(){

        System.out.println("Nhập vào itemId muoons thay đổi số lượng");
        int cartItemId = Inputmethods.getInteger();
        CartItem cartItem = cartController.findById(cartItemId);
        if(cartItem ==null){
            System.err.println("Không tồn tại id");
            return;
        }
        cartController.delete(cartItemId);
        Product p = cartItem.getProduct();
        p.setStock(p.getStock()+cartItem.getQuantity());
        productController.save(p);
    }
    public  void deleteAll(){

        List<CartItem> cart = cartController.getAll();
        for (CartItem ci: cart) {
            Product p =ci.getProduct();
            p.setStock(p.getStock()+ci.getQuantity());
            productController.save(p);
        }
        cart.clear();
    }
}