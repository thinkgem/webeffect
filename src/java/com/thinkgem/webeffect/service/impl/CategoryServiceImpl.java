package com.thinkgem.webeffect.service.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.webeffect.dao.WeCategoryDao;
import com.thinkgem.webeffect.dao.WeEffectDao;
import com.thinkgem.webeffect.model.WeCategory;
import com.thinkgem.webeffect.service.CategoryService;

/**
 * 特效分类服务
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private WeCategoryDao categoryDao;
	@Autowired
	private WeEffectDao effectDao;
	
	@Transactional(readOnly=true)
	public WeCategory get(int id) {
		return categoryDao.get(id);
	}

	@Transactional(readOnly=true)
	public List<WeCategory> list() {
		return categoryDao.findAll(Order.desc("weight"),Order.asc("id"));
	}

	public void saveOrUpdate(WeCategory category) {
		categoryDao.saveOrUpdate(category);	
	}

	public void delete(int id) {
		categoryDao.deleteById(id);
	}

	public void saveOrUpdateAll(List<WeCategory> categoryList) {
		categoryDao.saveOrUpdateAll(categoryList);
		//更新关联表
		for(int i=0; i<categoryList.size(); i++){
			WeCategory category = categoryList.get(i);
			effectDao.update("update WeEffect set categoryName = ? where weCategory.id = ?", category.getName(), category.getId());
		}
				
	}
}
