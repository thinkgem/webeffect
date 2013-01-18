package com.thinkgem.webeffect.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.webeffect.dao.WeUserDao;
import com.thinkgem.webeffect.hibernate.Pagination;
import com.thinkgem.webeffect.model.WeUser;
import com.thinkgem.webeffect.service.UserService;
import com.thinkgem.webeffect.util.Encrypt;

/**
 * 特效用户服务
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private WeUserDao userDao;
	
	@Transactional(readOnly=true)
	public WeUser getById(int id){
		return userDao.get((Integer)id);
	}

	@Transactional(readOnly=true)
	public Pagination list(int pageIndex, int pageSize) {		
		return userDao.findAllP(pageIndex, pageSize);
	}

	@SuppressWarnings("unchecked")
	public WeUser login(String username, String password) {
		password = Encrypt.md5(Encrypt.sha(password));
		List<WeUser> list = userDao.findByCriteria(
				userDao.createDetachedCriteria(
						Restrictions.eq("username", username),
						Restrictions.eq("password", password)
						),1,1);
		if(list.size() > 0){
			WeUser user = list.get(0);
			user.setLoginNum(user.getLoginNum()==null?1:user.getLoginNum()+1);
			user.setLoginTime(new Timestamp(new Date().getTime()));
			userDao.update("update WeUser set loginNum=?, loginTime=? where id=?",
					new Object[]{user.getLoginNum(), user.getLoginTime(), user.getId()});
			return list.get(0);
		}
		return null;
	}

	public void save(WeUser user) {
		user.setPassword(Encrypt.md5(Encrypt.sha(user.getPassword())));
		if(user.getLoginNum()==null||user.getLoginNum().intValue()==0){
			user.setLoginNum(1);
		}
		if(user.getLoginTime()==null){
			user.setLoginTime(new Timestamp(new Date().getTime()));
		}
		userDao.save(user);		
	}
	
	public void update(WeUser user) {		
		userDao.update(user);		
	}
	
	@Transactional(readOnly=true)
	public boolean usernameExists(String username) {
		Object user = ServletActionContext.getRequest().getSession().getAttribute("user");
		//如果已登录，则排除当前登录用户
		if (user!=null){
			return userDao.count(userDao.createDetachedCriteria(
					Restrictions.not(Restrictions.eq("username",((WeUser)user).getUsername())),
					Restrictions.eq("username", username))) > 0;
		}else{	
			return userDao.count("username", username) > 0;
		}
	}

	public void delete(int id) {
		userDao.deleteById((Integer)id);
	}
}
