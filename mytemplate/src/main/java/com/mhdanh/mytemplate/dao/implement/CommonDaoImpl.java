package com.mhdanh.mytemplate.dao.implement;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.dao.CommonDao;

@Repository
@Transactional
public abstract class CommonDaoImpl<T> extends HibernateDaoSupport implements
		CommonDao<T> {

	private Class<T> entityClass;

	private static final Logger LOGGER = Logger.getLogger(CommonDaoImpl.class);
	public static final String LOGMSGR = "Hydra's function to skip runtime error and go to another function!";
	@SuppressWarnings("unchecked")
	public CommonDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	public void setSession(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	@Override
	public boolean save(T bean) {
		Boolean flag = false;
		try {
			getHibernateTemplate().save(bean);
			flag = true;
		} catch (Exception e) {
			LOGGER.error(LOGMSGR, e);
		}
		return flag;
	}

	@Override
	public boolean saveOrUpdate(T bean) {
		Boolean flag = false;
		try {
			getHibernateTemplate().saveOrUpdate(bean);
			flag = true;
		} catch (Exception e) {
			LOGGER.error(LOGMSGR, e);
			flag = false;
		}

		return flag;
	}

	@Override
	public boolean merge(T bean) {
		Boolean flag = false;
		try {
			getHibernateTemplate().merge(bean);
			flag = true;
		} catch (Exception e) {
			LOGGER.error(LOGMSGR, e);
			flag = false;
		}

		return flag;
	}

	@Override
	public boolean update(T bean) {
		Boolean flag = false;
		try {
			getHibernateTemplate().update(bean);
			flag = true;
		} catch (Exception e) {
			LOGGER.error(LOGMSGR, e);
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean delete(Serializable id) {
		Boolean flag = false;
		try {
			T t = get(id);
			getHibernateTemplate().delete(t);
			flag = true;
		} catch (Exception e) {
			LOGGER.error(LOGMSGR, e);
			flag = false;
		}
		return flag;
	}

	@Override
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getListByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getListByCriteria(DetachedCriteria detachedCriteria,
			int offset, int size) {
		return (List<T>) getHibernateTemplate().findByCriteria(
				detachedCriteria, offset, size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkDuplicate(Class<T> clazz, boolean isEqual,
			String columnName, Object value) {

		Boolean flag = false;
		try {
			List<T> list = new ArrayList<T>();
			if (isEqual) {
				list = getListByCriteria(DetachedCriteria.forClass(clazz).add(
						Property.forName(columnName).eq(value)));
			} else {
				list = getListByCriteria(DetachedCriteria.forClass(clazz).add(
						Property.forName(columnName).like(value)));
			}
			if (!list.isEmpty()) {
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("Error at checkDuplicate", e);
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getListByColumn(Class<T> clazz, boolean isEqual,
			String columnName, Object value) {
		List<T> list = new ArrayList<T>();
		try {
			if (isEqual) {
				list = getListByCriteria(DetachedCriteria.forClass(clazz).add(
						Property.forName(columnName).eq(value)));
			} else {
				list = getListByCriteria(DetachedCriteria.forClass(clazz).add(
						Property.forName(columnName).like(value)));
			}
		} catch (Exception e) {
			LOGGER.error("Error at getListByColumn", e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getListByProperty(Class<T> clazz, boolean isEqual,
			String columnName, Object value) {
		List<T> list = new ArrayList<T>();
		try {
			if (isEqual) {
				list = getListByCriteria(DetachedCriteria.forClass(clazz).add(
						Property.forName(columnName).eq(value)));
			} else {
				list = getListByCriteria(DetachedCriteria.forClass(clazz).add(
						Property.forName(columnName).ne(value)));
			}
		} catch (Exception e) {
			LOGGER.error("Error at getListByColumn", e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getListByMoreColumns(Class<T> clazz, Map<String, Object> map) {
		List<T> list = new ArrayList<T>();
		DetachedCriteria detachedCriteria = null;
		try {
			detachedCriteria = DetachedCriteria.forClass(clazz);
			for (Entry<String, Object> entry : map.entrySet()) {
				detachedCriteria.add(Property.forName(entry.getKey()).eq(
						entry.getValue()));
			}
		} catch (Exception e) {
			LOGGER.error("Error at getListByColumns", e);
		} finally {
			list = getListByCriteria(detachedCriteria);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkDuplicateMoreColumns(Class<T> clazz,
			Map<String, Object> map) {
		List<T> list = new ArrayList<T>();
		DetachedCriteria detachedCriteria = null;
		try {
			detachedCriteria = DetachedCriteria.forClass(clazz);
			for (Entry<String, Object> entry : map.entrySet()) {
				detachedCriteria.add(Property.forName(entry.getKey()).eq(
						entry.getValue()));
			}
			list = getListByCriteria(detachedCriteria);
			if (!list.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Error at checkDuplicateMoreColumns", e);
		}

		return false;
	}

	// Get Session
	public Session getSession() {

		return currentSession();
	}

	@Override
	public int saveAndRetrieveId(T bean) {
		int id = 0;
		id = (Integer) getHibernateTemplate().save(bean);
		return id;
	}

}
