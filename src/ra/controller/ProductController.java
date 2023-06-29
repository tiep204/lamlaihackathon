package ra.controller;

import ra.model.Product;
import ra.service.ProductService;

import java.util.List;

public class ProductController implements IController<Product, String> {
	private ProductService productService = new ProductService();
	
	
	@Override
	public List<Product> getAll() {
		return productService.getAll();
	}
	
	@Override
	public void save(Product product) {
		productService.save(product);
	}
	
	@Override
	public Product findById(String s) {
		return productService.findById(s);
	}
	
	@Override
	public void delete(String s) {
		productService.delete(s);
	}
	public void sort(){
		productService.sortProduct();
	}
}