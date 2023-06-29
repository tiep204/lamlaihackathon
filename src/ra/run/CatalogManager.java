package ra.run;

import ra.config.Inputmethods;
import ra.controller.CatalogController;
import ra.model.Catalog;

public class CatalogManager {
	private CatalogController catalogController;
	
	public CatalogManager(CatalogController catalogController) {
		this.catalogController = catalogController;
		boolean flag = true;
		while (flag) {
			System.out.println("********************CATALOG-MANAGEMENT********************");
			System.out.println("1. Nhập số danh mục thêm mới và nhập thông tin cho từng danh mục");
			System.out.println("2. Hiển thị thông tin tất cả các danh mục");
			System.out.println("3. Sửa tên danh mục theo mã danh mục");
			System.out.println("4. Xóa danh muc theo mã danh mục (lưu ý ko xóa khi có sản phẩm)");
			System.out.println("5. Quay lại");
			System.out.print("Mời bạn lựa chọn: ");
			int choose = Inputmethods.getInteger();
			switch (choose) {
				case 1:
					addNewCatalog();
					break;
				case 2:
					showListCatalog();
					break;
				case 3:
					editCatalog();
					break;
				case 4:
					deleteCatalog();
					break;
				case 5:
					flag = false;
					return;
				default:
					System.err.println("Vui lòng chọn từ 1-5");
			}
		}
	}
	
	public void addNewCatalog() {
		System.out.print("Bạn muốn nhập vào nhiêu danh mục: ");
		int n = Inputmethods.getInteger();
		for (int i = 0; i < n; i++) {
			System.out.println("Danh mục thứ " + (i + 1));
			Catalog catalog = new Catalog();
			catalog.setCatalogId(catalogController.getNewId());
			catalog.inputData();
			catalogController.save(catalog);
		}
	}
	
	public void showListCatalog() {
		System.out.printf("%-10s %-20s \n","ID","Tên");
		System.out.println("---------------------------------");
		if (catalogController.getAll().size() == 0) {
			System.err.println("Chưa có danh mục nào");
			return;
		}
		for (Catalog c : catalogController.getAll()) {
			c.displayData();
		}
		System.out.println("---------------------------------");
	}
	
	public void editCatalog() {
		System.out.print("Bạn muốn sửa danh mục có ID là: ");
		int id = Inputmethods.getInteger();
		Catalog catalog = catalogController.findById(id);
		if (catalog == null) {
			System.err.println("Không tồn tại danh mục này");
			return;
		}
		Catalog newCatalog = new Catalog();
		newCatalog.setCatalogId(catalog.getCatalogId());
		newCatalog.inputData();
		catalogController.save(newCatalog);
	}
	
	public void deleteCatalog() {
		System.out.print("Nhập id danh mục muốn xóa: ");
		int id = Inputmethods.getInteger();
		catalogController.delete(id);
	}
}