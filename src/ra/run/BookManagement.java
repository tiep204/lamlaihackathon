package ra.run;

import ra.config.Inputmethods;
import ra.controller.CatalogController;
import ra.controller.ProductController;
import ra.model.Catalog;
import ra.model.Product;

import java.util.List;

public class BookManagement {
	
	static ProductController productController = new ProductController();
	static CatalogController catalogController = new CatalogController();
	
	public static void main(String[] args) {
		List<Catalog> listCat = catalogController.getAll();
		Catalog cat1 = new Catalog(1,"Quần");
		Catalog cat2 = new Catalog(2,"Áo");
		listCat.add(cat1);
		listCat.add(cat2);
		List<Product> listPro = productController.getAll();
		Product p1 =  new Product("P0001","Quần hoa",100,"",100,cat1,false);
		Product p2 =  new Product("P0002","Quần dài",150,"",120,cat1,true);
		Product p3 =  new Product("P0003","Áo phông",160,"",80,cat2,true);
		Product p4 =  new Product("P0004","Áo dài",200,"",90,cat2,true);
		listPro.add(p1);
		listPro.add(p2);
		listPro.add(p3);
		listPro.add(p4);
		while (true) {
			System.out.println("**************************BASIC-MENU**************************");
			System.out.println("1. Quản lý danh mục ");
			System.out.println("2. Quản lý sản phẩm ");
			System.out.println("3. Menu User");
			System.out.println("4. Thoát");
			System.out.print("Mời bạn lựa chọn: ");
			int choose = Inputmethods.getInteger();
			switch (choose) {
				case 1:
					new CatalogManager(catalogController);
					break;
				case 2:
					new ProductManager(productController, catalogController);
					break;
				case 3:
					new MenuUser(productController);
					break;
					case 4:
					System.exit(0);
					break;
				default:
					System.err.println("Vui lòng nhập lại (1 -> 3)");
					break;
			}
		}
	}
}