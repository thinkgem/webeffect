package com.thinkgem.webeffect.service;

import com.thinkgem.webeffect.hibernate.Pagination;
import com.thinkgem.webeffect.model.WeUser;

/**
 * 特效用户服务接口
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
public interface UserService {
	
	/**
	 * 通过编号获得用户对象信息
	 * @param id 用户编号
	 * @return
	 */
	WeUser getById(int id);
	
	/**
	 * 登陆验证
	 * @param username 用户名
	 * @param password 密码
	 * @return 登陆成功返回用户的对象，失败返回空
	 */
	WeUser login(String username, String password);
	
	/**
	 * 保存新用户对象
	 * @param user 用户对象
	 */
	void save(WeUser user);
	
	/**
	 * 更新用户对象
	 * @param user 用户对象
	 */
	void update(WeUser user);
	
	/**
	 * 删除用户
	 * @param id 用户编号
	 */
	void delete(int id);
		
	/**
	 * 获得用户列表
	 * @param pageIndex 当前页
	 * @param pageSize 页面大小
	 * @return 页码对象
	 */
	Pagination list(int pageIndex, int pageSize);
	
	/**
	 * 检查用户名是否存在
	 * @param username 用户名
	 * @return true: 存在
	 */
	boolean usernameExists(String username);

}
