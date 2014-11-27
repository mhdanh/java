package com.mhdanh.mytemplate.service;

import java.util.List;
import java.util.Map;

public interface CommonService<T> {
	List<T> getAll();

	T getById(int id);

	boolean add(T obj);

	int saveAndGetId(T obj);

	boolean delete(int id);

	boolean update(T obj);

	boolean checkDuplicate(Class<T> clazz, boolean isEqual, String columnName,
			Object value);

	boolean checkDuplicateMoreColumns(Class<T> clazz, Map<String, Object> map);

	List<T> getListByColumn(Class<T> clazz, boolean isEqual, String columnName,
			Object value);

	List<T> getListByProperty(Class<T> clazz, boolean isEqual,
			String columnName, Object value);

	List<T> getListByMoreColumns(Class<T> clazz, Map<String, Object> map);
}
