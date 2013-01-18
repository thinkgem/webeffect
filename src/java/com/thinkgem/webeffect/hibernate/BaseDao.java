package com.thinkgem.webeffect.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;


/**
 * 所有 DAO 的基类接口
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: BaseDao.java 3144 2010-06-17 08:46:51Z wzhen $
 */
public interface BaseDao<T extends Serializable> {
		
	// -------------------------------------------------------------------------
	// 基本检索
	// -------------------------------------------------------------------------
	/**
	 * 根据主键获取实体对象,不锁定对象。
	 */
	public T getById(Serializable id);
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<T> getByProperty(String property, Object value);
	/**
	 * 根据主键获取实体对象,不锁定对象。
	 */
	public T load(Serializable id);
	
	/**
	 * 根据主键获取实体对象， 如果没有相应的实体对象，抛出异常。
	 */
	public T load(Serializable id, LockMode lock);
	
	/**
	 * 根据主键获取实体对象， 如果没有相应的实体对象，则返回 null。
	 */
	public T get(Serializable id);
	
	/**
	 * 根据主键获取实体对象， 如果没有相应的实体对象，抛出异常。
	 */
	public T get(Serializable id, LockMode lock);
	
	/**
	 * 根据属性名和属性值查询唯一实体对象。
	 */
	public T get(String property, Object value);
	

	/**
	 * 查找所有实体对象
	 */
	public List<T> findAll();

	/**
	 * 查找所有实体对象，带排序
	 */
	public List<T> findAll(Order... orders);

	/**
	 * 查找所有实体对象，带分页和排序
	 */
	public List<T> findAll(int pageIndex, int pageSize, Order... orders);
	
	/**
	 * 查找所有实体对象，带分页和排序
	 * @param pageIndex 当前页面索引
	 * @param pageSize 页面大小
	 * @param orders 配置
	 * @return 分页对象
	 */
	public Pagination findAllP(int pageIndex, int pageSize, Order... orders);
	
	/**
	 * 查找所有实体对象，带分页和排序
	 * 
	 * @param pageIndex
	 *            当前页面索引
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            配置
	 * @return 分页对象
	 */
	public Pagination findAllPByProperty(String property, Object value,
			int pageIndex, int pageSize, Order... orders);
	/**
	 * 查找所有实体对象，带分页和排序
	 * 
	 * @param pageIndex
	 *            当前页面索引
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            配置
	 * @return 分页对象
	 */
	public Pagination findAllPByNotProperty(String property, Object value,
			int pageIndex, int pageSize, Order... orders);
	/**
	 * 查找所有实体对象，带分页和排序
	 * 
	 * @param pageIndex
	 *            当前页面索引
	 * @param property
	 *            不与属性名相等
	 * @param value
	 *            不与属性值相等的数据
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            配置
	 * @return 分页对象
	 */
	public Pagination findAllPByEqNeProperty(String eqP,Object eqV,String neP, Object neV,
			int pageIndex, int pageSize, Order... orders);
	/**
	 * 根据属性名和属性值查询实体对象列表。
	 */
	public List<T> find(String property, Object value);
	public List<T> findNot(String property, Object value);
	
	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param exampleEntity 示例对象
	 * @param anyWhere 是否模糊查询，默认false。
	 * @param orders 排序。
	 * @param exclude 需要排除的属性
	 * @return 对象列表
	 */
	public List<T> find(T exampleEntity, boolean anyWhere, Order[] orders, String... exclude);

	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param exampleEntity 示例对象
	 * @param anyWhere 是否模糊查询，默认false。
	 * @param orders 排序。
	 * @param pageIndex 当前页号
	 * @param pageSize 页面大小
	 * @param exclude 需要排除的属性
	 * @return 对象列表
	 */
	public List<T> find(T exampleEntity, boolean anyWhere, Order[] orders, int pageIndex, int pageSize, String... exclude);
	
	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param exampleEntity 示例对象
	 * @param anyWhere 是否模糊查询，默认false。
	 * @param orders 排序。
	 * @param pageIndex 当前页号
	 * @param pageSize 页面大小
	 * @param exclude 需要排除的属性
	 * @return 页面对象
	 */
	public Pagination findP(T exampleEntity, boolean anyWhere, Order[] orders, int pageIndex, int pageSize, String... exclude);
	
	/**
	 * 使用HSQL语句检索数据
	 */	
	@SuppressWarnings("unchecked")
	public List find(String queryString);

	/**
	 * 使用带参数的HSQL语句检索数据
	 */
	@SuppressWarnings("unchecked")
	public List find(String queryString, Object... values);
	
	/**
	 * 使用指定的检索标准检索数据
	 */
	@SuppressWarnings("unchecked")
	public List findByCriteria(DetachedCriteria criteria);
	
	/**
	 * 使用指定的检索标准检索数据，返回部分记录
	 */
	@SuppressWarnings("unchecked")
	public List findByCriteria(DetachedCriteria criteria, int pageIndex, int pageSize);
	
	/**
	 * 使用指定的检索标准检索数据，返回页码对象
	 */
	public Pagination findByCriteriaP(DetachedCriteria criteria, int pageIndex, int pageSize);
		
	/**
	 * 查找所有实体对象的数量
	 */
	public int count();
	
	/**
	 * 根据属性查找实体对象的数量
	 */
	public int count(String property, Object value);
	
	/**
	 * 使用指定的检索标准获取满足标准的记录数
	 */
	public int count(DetachedCriteria criteria);
		
	/**
	 * 创建与会话绑定的检索标准对象
	 */
	public Criteria createCriteria(Criterion... criterions);

	/**
	 * 创建与会话无关的检索标准对象
	 */
	public DetachedCriteria createDetachedCriteria(Criterion... criterions);

	// -------------------------------------------------------------------------
	// 保存更新
	// -------------------------------------------------------------------------
	
	/**
	 * 存储实体到数据库
	 */
	public T save(T entity);

	/**
	 * 增加或更新实体
	 */
	public T saveOrUpdate(T entity);

	/**
	 * 增加或更新集合中的全部实体
	 */
	public void saveOrUpdateAll(Collection<T> entities);
	
	/**
	 * 更新实体
	 */
	public T update(T entity);

	/**
	 * 更新实体并加锁
	 */
	public T update(T entity, LockMode lock);
	
	/**
	 * 保存或更新对象拷贝
	 * @return 已更新的持久化对象
	 */
	public T merge(T entity);
	
	/**
	 * 删除指定的实体
	 */
	public void delete(T entity);

	/**
	 * 加锁并删除指定的实体
	 */
	public void delete(T entity, LockMode lock);

	/**
	 * 根据主键删除指定实体
	 */
	public void deleteById(Serializable id);

	/**
	 * 根据主键加锁并删除指定的实体
	 */
	public void deleteById(Serializable id, LockMode lock);

	/**
	 * 删除集合中的全部实体
	 */
	public void deleteAll(Collection<T> entities);
	
	/**
	 * 使用HSQL语句直接增加、更新、删除实体
	 */
	public int update(String queryString);

	/**
	 * 使用带参数的HSQL语句增加、更新、删除实体
	 */
	public int update(String queryString, Object... values);
	
	/**
	 * 执行sql语句
	 */
	public Integer executeSql(String sql);
	
	// -------------------------------------------------------------------------
	// 其它
	// -------------------------------------------------------------------------
	
	/**
	 * 加锁指定的实体
	 */
	public void lock(T entity, LockMode lock);

	/**
	 * 刷新对象
	 */
	public void refresh(T entity);
	
	/**
	 * 强制初始化指定的实体
	 */
	public void initialize(Object proxy);
	
	/**
	 * 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	 */
	public void flush();

	/**
	 * 清空缓冲数据
	 */
	public void clear();

}
