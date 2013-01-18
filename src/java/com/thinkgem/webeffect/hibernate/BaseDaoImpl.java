package com.thinkgem.webeffect.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


/**
 * 所有 DAO 的基类实现
 * 
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: BaseDaoImpl.java 3144 2010-06-17 08:46:51Z wzhen $
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T extends Serializable> extends
		org.springframework.orm.hibernate3.support.HibernateDaoSupport
		implements BaseDao<T> {

	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	private Class<?> entityClass = null;

	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public BaseDaoImpl() {
		entityClass = this.getSuperClassType(getClass());
	}

	/**
	 * 通过反射，获得定义Class声明时的父类的范型参数类型
	 */
	protected Class<?> getSuperClassType(Class<?> c) {
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) type).getActualTypeArguments();
			if (!(params[0] instanceof Class)) {
				return Object.class;
			}
			return (Class<?>) params[0];
		}
		return null;
	}

	/**
	 * 设置T的Class
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	// -------------------------------------------------------------------------
	// 基本检索
	// -------------------------------------------------------------------------

	/**
	 * 根据主键获取实体对象,不锁定对象。
	 */
	public T getById(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public List<T> getByProperty(String property, Object value) {
		Assert.hasText(property);
		return createCriteria(Restrictions.eq(property, value)).list();
	}

	/**
	 * 根据主键获取实体对象,不锁定对象。
	 */
	public T load(Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 根据主键获取实体对象， 如果没有相应的实体对象，抛出异常。
	 */
	public T load(Serializable id, LockMode lock) {
		T t = (T) getHibernateTemplate().load(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	/**
	 * 根据主键获取实体对象， 如果没有相应的实体对象，则返回 null。
	 */
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 根据主键获取实体对象， 如果没有相应的实体对象，抛出异常。
	 */
	public T get(Serializable id, LockMode lock) {
		T t = (T) getHibernateTemplate().get(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	/**
	 * 根据属性名和属性值查询唯一实体对象。
	 */
	public T get(String property, Object value) {
		return (T) createCriteria(Restrictions.eq(property, value))
				.uniqueResult();
	}

	/**
	 * 查找所有实体对象
	 */
	public List<T> findAll() {
		return createCriteria().list();
	}

	/**
	 * 查找所有实体对象，带排序
	 */
	public List<T> findAll(Order... orders) {
		Criteria criteria = createCriteria();
		if (orders != null) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
		return criteria.list();
	}

	/**
	 * 查找所有实体对象，带分页和排序
	 */
	public List<T> findAll(int pageIndex, int pageSize, Order... orders) {
		Criteria criteria = createCriteria();
		if (orders != null) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
		int firstResult = (pageIndex - 1) * pageSize;
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	/**
	 * 查找所有实体对象，带分页和排序
	 * 
	 * @param pageIndex
	 *            当前页面索引
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            配置
	 * @return 分页对象
	 */
	public Pagination findAllP(int pageIndex, int pageSize, Order... orders) {
		Criteria criteria = createCriteria();

		return findByCriteria(criteria, pageIndex, pageSize, null, orders);
	}

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
			int pageIndex, int pageSize, Order... orders) {
		Criteria criteria = createCriteria(Restrictions.eq(property, value));
		return findByCriteria(criteria, pageIndex, pageSize, null, orders);
	}

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
	public Pagination findAllPByNotProperty(String property, Object value,
			int pageIndex, int pageSize, Order... orders) {
		Criteria criteria = createCriteria(Restrictions.ne(property, value));
		return findByCriteria(criteria, pageIndex, pageSize, null, orders);
	}

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
	public Pagination findAllPByEqNeProperty(String eqP, Object eqV,
			String neP, Object neV, int pageIndex, int pageSize,
			Order... orders) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(eqP, eqV));
		criteria.add(Restrictions.ne(neP, neV));
		return findByCriteria(criteria, pageIndex, pageSize, null, orders);
	}

	/**
	 * 根据属性名和属性值查询实体对象列表。
	 */
	public List<T> find(String property, Object value) {
		return createCriteria(Restrictions.eq(property, value)).list();
	}

	/**
	 * 根据属性名和属性值查询实体对象列表。
	 */
	public List<T> findNot(String property, Object value) {
		return createCriteria(Restrictions.ne(property, value)).list();
	}

	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param exampleEntity
	 *            示例对象
	 * @param anyWhere
	 *            是否模糊查询，默认false。
	 * @param orders
	 *            排序。
	 * @param exclude
	 *            需要排除的属性
	 * @return 对象列表
	 */
	public List<T> find(T exampleEntity, boolean anyWhere, Order[] orders,
			String... exclude) {
		Criteria criteria = getCriteriaByExample(exampleEntity, anyWhere,
				orders, exclude);
		return criteria.list();
	}

	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param exampleEntity
	 *            示例对象
	 * @param anyWhere
	 *            是否模糊查询，默认false。
	 * @param orders
	 *            排序。
	 * @param pageIndex
	 *            当前页号
	 * @param pageSize
	 *            页面大小
	 * @param exclude
	 *            需要排除的属性
	 * @return 对象列表
	 */
	public List<T> find(T exampleEntity, boolean anyWhere, Order[] orders,
			int pageIndex, int pageSize, String... exclude) {
		int firstResult = (pageIndex - 1) * pageSize;
		Criteria criteria = getCriteriaByExample(exampleEntity, anyWhere,
				orders, exclude);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param exampleEntity
	 *            示例对象
	 * @param anyWhere
	 *            是否模糊查询，默认false。
	 * @param orders
	 *            排序。
	 * @param pageIndex
	 *            当前页号
	 * @param pageSize
	 *            页面大小
	 * @param exclude
	 *            需要排除的属性
	 * @return 页面对象
	 */
	public Pagination findP(T exampleEntity, boolean anyWhere, Order[] orders,
			int pageIndex, int pageSize, String... exclude) {
		Criteria criteria = getCriteriaByExample(exampleEntity, anyWhere,
				orders, exclude);
		return findByCriteria(criteria, pageIndex, pageSize, null, orders);
	}

	/**
	 * 通过示例对象获得检索标准对象
	 */
	protected Criteria getCriteriaByExample(T exampleEntity, boolean anyWhere,
			Order[] orders, String... exclude) {
		Criteria criteria = getSession().createCriteria(entityClass);
		Example example = Example.create(exampleEntity);
		example.setPropertySelector(NOT_BLANK);
		if (anyWhere) {
			example.enableLike(MatchMode.ANYWHERE);
			example.ignoreCase();
		}
		for (String p : exclude) {
			example.excludeProperty(p);
		}
		criteria.add(example);
		// 处理排序
		if (orders != null) {
			for (Order o : orders) {
				criteria.addOrder(o);
			}
		}
		// 处理many to one查询
		ClassMetadata cm = this.getSessionFactory().getClassMetadata(
				exampleEntity.getClass());
		String[] fieldNames = cm.getPropertyNames();
		for (String field : fieldNames) {
			Object o = cm.getPropertyValue(exampleEntity, field,
					EntityMode.POJO);
			if (o == null) {
				continue;
			}
			ClassMetadata subCm = this.getSessionFactory().getClassMetadata(
					o.getClass());
			if (subCm == null) {
				continue;
			}
			Serializable id = subCm.getIdentifier(o, EntityMode.POJO);
			if (id != null) {
				Serializable idName = subCm.getIdentifierPropertyName();
				criteria.add(Restrictions.eq(field + "." + idName, id));
			} else {
				criteria.createCriteria(field).add(Example.create(o));
			}
		}
		return criteria;
	}

	/**
	 * 不为空的EXAMPLE属性选择方式
	 */
	protected static final NotBlankPropertySelector NOT_BLANK = new NotBlankPropertySelector();

	protected static final class NotBlankPropertySelector implements
			PropertySelector {
		private static final long serialVersionUID = -1186249969334054335L;

		public boolean include(Object object, String property,
				org.hibernate.type.Type type) {
			return object != null
					&& !(object instanceof String && StringUtils
							.isBlank((String) object));
		}
	}

	/**
	 * 使用HSQL语句检索数据
	 */
	public List find(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	/**
	 * 使用带参数的HSQL语句检索数据
	 */
	public List find(String queryString, Object... values) {
		return getHibernateTemplate().find(queryString, values);
	}

	/**
	 * 使用指定的检索标准检索数据
	 */
	public List findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 使用指定的检索标准检索数据，返回部分记录
	 */
	public List findByCriteria(DetachedCriteria criteria, int pageIndex,
			int pageSize) {
		int firstResult = (pageIndex - 1) * pageSize;
		return getHibernateTemplate().findByCriteria(criteria, firstResult,
				pageSize);
	}

	/**
	 * 使用指定的检索标准检索数据，返回页码对象
	 */
	public Pagination findByCriteriaP(DetachedCriteria criteria, int pageIndex,
			int pageSize) {
		Pagination p = new Pagination(pageIndex, pageSize, count(criteria));
		p.setList(findByCriteria(criteria, pageIndex, pageSize));
		return p;
	}

	/**
	 * 使用指定的检索标准检索数据，返回部分记录
	 * 
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @param projection
	 * @param orders
	 * @return
	 */

	protected Pagination findByCriteria(Criteria criteria, int pageIndex,
			int pageSize, Projection projection, Order... orders) {
		int count = ((Number) criteria.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		Pagination p = new Pagination(pageIndex, pageSize, count);
		if (count < 1) {
			return p;
		}
		if (pageSize >= count) {
			pageIndex = 1;
		}

		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);// ROOT_ENTITY以实体类方式返回
		}
		if (orders != null) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
		int firstResult = (pageIndex - 1) * pageSize;
		if (firstResult >= count) {
			firstResult = 0;
		}
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pageSize);
		p.setList(criteria.list());
		return p;
	}

	/**
	 * 查找所有实体对象的数量
	 */
	public int count() {
		return ((Number) (createCriteria()
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	/**
	 * 根据属性查找实体对象的数量
	 */
	public int count(String property, Object value) {
		return ((Number) (createCriteria(Restrictions.eq(property, value))
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	/**
	 * 使用指定的检索标准获取满足标准的记录数
	 */
	public int count(DetachedCriteria criteria) {
		int count = ((Number) criteria.setProjection(Projections.rowCount())
				.getExecutableCriteria(getSession()).uniqueResult()).intValue();
		criteria.setProjection(null);
		return count;
	}

	/**
	 * 创建与会话绑定的检索标准对象
	 */
	public Criteria createCriteria(Criterion... criterions) {
		return this.createDetachedCriteria(criterions).getExecutableCriteria(
				getSession());
	}

	/**
	 * 创建与会话无关的检索标准对象
	 */
	public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
		DetachedCriteria dc = DetachedCriteria.forClass(this.entityClass);
		for (Criterion c : criterions) {
			dc.add(c);
		}
		return dc;
	}

	// -------------------------------------------------------------------------
	// 保存更新
	// -------------------------------------------------------------------------

	/**
	 * 存储实体到数据库
	 */
	public T save(T entity) {
		getHibernateTemplate().save(entity);
		return entity;
	}

	/**
	 * 增加或更新实体
	 */
	public T saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	/**
	 * 增加或更新集合中的全部实体
	 */
	public void saveOrUpdateAll(Collection<T> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * 更新实体
	 */
	public T update(T entity) {
		getHibernateTemplate().update(entity);
		return entity;
	}

	/**
	 * 更新实体并加锁
	 */
	public T update(T entity, LockMode lock) {
		getHibernateTemplate().update(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
		return entity;
	}

	/**
	 * 保存或更新对象拷贝
	 * 
	 * @return 已更新的持久化对象
	 */
	public T merge(T entity) {
		return getHibernateTemplate().merge(entity);
	}

	/**
	 * 删除指定的实体
	 */
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 加锁并删除指定的实体
	 */
	public void delete(T entity, LockMode lock) {
		getHibernateTemplate().delete(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	/**
	 * 根据主键删除指定实体
	 */
	public void deleteById(Serializable id) {
		T t = this.load(id);
		if (t != null)
			this.delete(t);
	}

	/**
	 * 根据主键加锁并删除指定的实体
	 */
	public void deleteById(Serializable id, LockMode lock) {
		T t = this.load(id);
		if (t != null)
			this.delete(t, lock);
	}

	/**
	 * 删除集合中的全部实体
	 */
	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 使用HSQL语句直接增加、更新、删除实体
	 */
	public int update(String queryString) {
		return getHibernateTemplate().bulkUpdate(queryString);
	}

	/**
	 * 使用带参数的HSQL语句增加、更新、删除实体
	 */
	public int update(String queryString, Object... values) {
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	/**
	 * 执行sql语句
	 */
	public Integer executeSql(String sql) {
		return this.getSession().createSQLQuery(sql).executeUpdate();
	}

	// -------------------------------------------------------------------------
	// 其它
	// -------------------------------------------------------------------------

	/**
	 * 加锁指定的实体
	 */
	public void lock(T entity, LockMode lock) {
		getHibernateTemplate().lock(entity, lock);
	}

	/**
	 * 刷新对象
	 */
	public void refresh(T entity) {
		getHibernateTemplate().refresh(entity);
	}

	/**
	 * 强制初始化指定的实体
	 */
	public void initialize(Object proxy) {
		getHibernateTemplate().initialize(proxy);
	}

	/**
	 * 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 清空缓冲数据
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}
}