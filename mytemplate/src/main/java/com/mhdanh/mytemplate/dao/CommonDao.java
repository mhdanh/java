package com.mhdanh.mytemplate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

public interface CommonDao<T> {
	/*
	 * Basic interface for CRUD operations and common queries
	 */
	List<T> getAll();

	boolean save(T bean);

	boolean update(T bean);

	boolean saveOrUpdate(T bean);

	boolean merge(T bean);

	boolean delete(Serializable id);

	int saveAndRetrieveId(T bean);

	T get(Serializable id);

	/*
	 * Get List by criteria
	 * 
	 * @param detachCriteria the domain query criteria, it includes condition
	 * and orders
	 * 
	 * @return
	 */

	List<T> getListByCriteria(DetachedCriteria detachedCriteria);

	List<T> getListByCriteria(DetachedCriteria detachedCriteria, int offset,
			int size);

	/**
	 * [Check Duplicate Column from CommonDao ] I will try to write it - send 4
	 * Nguyen Huu Tuan Phong Parameter need: Class of Persistent Hibernate -
	 * Name of column name need to check duplicate - Value of column name need
	 * to check duplicate
	 */

	boolean checkDuplicate(Class<T> clazz, boolean isEqual, String columnName,
			Object value);

	boolean checkDuplicateMoreColumns(Class<T> clazz, Map<String, Object> map);

	List<T> getListByColumn(Class<T> clazz, boolean isEqual, String columnName,
			Object value);

	List<T> getListByProperty(Class<T> clazz, boolean isEqual,
			String columnName, Object value);

	List<T> getListByMoreColumns(Class<T> clazz, Map<String, Object> map);
}
