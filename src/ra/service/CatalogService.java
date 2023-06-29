package ra.service;

import ra.model.Catalog;

import java.util.ArrayList;
import java.util.List;

public class CatalogService implements IService<Catalog, Integer> {
	
	private List<Catalog> listCatalog = new ArrayList<>();
	
	@Override
	public List<Catalog> getAll() {
		return listCatalog;
	}
	
	@Override
	public void save(Catalog catalog) {
		if (findById(catalog.getCatalogId()) == null) {
			listCatalog.add(catalog);
		} else {
			listCatalog.set(listCatalog.indexOf(findById(catalog.getCatalogId())), catalog);
		}
	}
	
	@Override
	public Catalog findById(Integer integer) {
		for (Catalog c : listCatalog) {
			if (c.getCatalogId() == integer) {
				return c;
			}
		}
		System.out.println("Không tìm thấy id");
		return null;
	}
	
	@Override
	public void delete(Integer integer) {
		for (Catalog cat : listCatalog) {
			if (cat.getCatalogId() == integer) {
				listCatalog.remove(cat);
				System.out.println("Xóa thành công id " + integer);
				return;
			}
		}
		System.out.println("Không tìm thấy id");
	}
	
	public int getNewId() {
		return listCatalog.size()+1;
	}
}