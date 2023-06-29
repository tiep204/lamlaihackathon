package ra.controller;

import java.util.List;

public interface IController<T, E> {
	List<T> getAll();
	
	void save(T t);
	
	T findById(E e);
	
	void delete(E e);
}