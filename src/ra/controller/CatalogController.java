package ra.controller;

import ra.model.Catalog;
import ra.service.CatalogService;

import java.util.List;

public class CatalogController implements IController<Catalog, Integer> {
	private CatalogService catalogService = new CatalogService();
	
	@Override
	public List<Catalog> getAll() {
		return catalogService.getAll();
	}
	
	@Override
	public void save(Catalog catalog) {
		catalogService.save(catalog);
	}
	
	@Override
	public Catalog findById(Integer integer) {
		return catalogService.findById(integer);
	}
	
	@Override
	public void delete(Integer integer) {
		catalogService.delete(integer);
	}
	
	public int getNewId() {
		return catalogService.getNewId();
	}
}
