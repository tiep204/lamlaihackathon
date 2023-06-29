package ra.service;

import ra.model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductService implements IService<Product, String> {

    private List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            products.add(product);
        } else {
            products.set(products.indexOf(findById(product.getProductId())), product);
        }
    }

    @Override
    public Product findById(String s) {
        for (Product p : products) {
            if (p.getProductId().equals(s)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void delete(String s) {
        for (Product p : products) {
            if (findById(s).equals(p)) {
                products.remove(p);
                System.out.println("bạn đã xóa id: " + p.getProductId() + " thành công");
                return;
            }
        }
        System.err.println("Không có sản phẩm này");
    }

    public void sortProduct() {
        products.sort(Comparator.comparingDouble(Product::getProductPrice).reversed());
    }
}