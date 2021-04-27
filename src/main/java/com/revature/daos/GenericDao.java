package com.revature.daos;

import java.util.List;

public interface GenericDao<T> {
	
	// CRUD operations for all Daos
	public T add(T t);
	public T getById(Integer id);
	public List<T> getAll();
	public Integer update(T t);
	public Integer delete(T t);
}
