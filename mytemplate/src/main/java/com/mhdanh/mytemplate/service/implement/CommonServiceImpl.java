package com.mhdanh.mytemplate.service.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.dao.CommonDao;
import com.mhdanh.mytemplate.service.CommonService;

@Service
@Transactional
public abstract class CommonServiceImpl<T> implements CommonService<T> {

	@Autowired
	private CommonDao<T> commonDao;
	
	public List<T> getAll() {
		return this.commonDao.getAll();
	}

	public T getById(int id) {
		return this.commonDao.get(id);
	}

	public boolean add(T obj) {
		return this.commonDao.save(obj);
	}

	public boolean delete(int id) {
		return this.commonDao.delete(id);
	}

	public boolean update(T obj) {
		return this.commonDao.update(obj);
	}

	public boolean checkDuplicate(Class<T> clazz, boolean isEqual,
			String columnName, Object value) {
		return this.commonDao.checkDuplicate(clazz, isEqual, columnName, value);
	}

	public List<T> getListByColumn(Class<T> clazz, boolean isEqual,
			String columnName, Object value) {
		return this.commonDao.getListByColumn(clazz, isEqual, columnName, value);
	}

	public List<T> getListByProperty(Class<T> clazz, boolean isEqual,
			String columnName, Object value) {
		return this.commonDao.getListByProperty(clazz, isEqual, columnName, value);
	}

	public List<T> getListByMoreColumns(Class<T> clazz, Map<String, Object> map) {
		return this.commonDao.getListByMoreColumns(clazz, map);
	}

	public boolean checkDuplicateMoreColumns(Class<T> clazz,
			Map<String, Object> map) {
		return this.commonDao.checkDuplicateMoreColumns(clazz, map);
	}

	public int saveAndGetId(T obj) {
		return this.commonDao.saveAndRetrieveId(obj);
	}
}
