package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.CategoryDao;
import com.mhdanh.mytemplate.domain.Category;

@Transactional
@Repository
public class CategoryDaoImpl extends CommonDaoImpl<Category> implements CategoryDao{


}
