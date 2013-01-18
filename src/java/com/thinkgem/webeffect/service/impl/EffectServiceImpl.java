package com.thinkgem.webeffect.service.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.webeffect.dao.WeCommentDao;
import com.thinkgem.webeffect.dao.WeEffectDao;
import com.thinkgem.webeffect.dao.WeFileDao;
import com.thinkgem.webeffect.dao.WeUserDao;
import com.thinkgem.webeffect.hibernate.Pagination;
import com.thinkgem.webeffect.model.WeComment;
import com.thinkgem.webeffect.model.WeEffect;
import com.thinkgem.webeffect.model.WeFile;
import com.thinkgem.webeffect.model.WeUser;
import com.thinkgem.webeffect.util.FileOperateUtils;

/**
 * 特效服务
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Service
@Transactional
public class EffectServiceImpl implements com.thinkgem.webeffect.service.EffectService {

	@Autowired
	private WeEffectDao effectDao;	
	@Autowired
	private WeFileDao fileDao;	
	@Autowired
	private WeCommentDao commentDao;
	@Autowired
	private WeUserDao userDao;	
	
	
	@Transactional(readOnly=true)
	public WeEffect get(int id) {
		return effectDao.get(id);
	}
	
	public void saveOrUpdate(WeEffect effect) {
		//判断是否修改了文件
		if (effect.getWeFileNew()!=null){
			if (effect.getWeFileNew().getId() != null && effect.getWeFileNew().getId().intValue() > 0){
				//获得旧文件编号
				Integer fileId = effect.getWeFile().getId();
				//设置新文件
				effect.setWeFile(effect.getWeFileNew());				
				//保存更新特效
				effectDao.saveOrUpdate(effect);
				//删除旧文件
				deleteFile(fileId);
			}
		}else{
			effectDao.saveOrUpdate(effect);
		}		
	}

	@Transactional(readOnly=true)
	public Pagination list(int categoryId, int pageIndex, int pageSize, String query) {
		DetachedCriteria dc = effectDao.createDetachedCriteria();
		if (categoryId > 0){
			dc.add(Restrictions.eq("weCategory.id", (Integer)categoryId));
		}
		if (query != null && !"".equals(query)){
			dc.add(Restrictions.or(Restrictions.like("title", "%"+query+"%"),Restrictions.like("author", "%"+query+"%")));
		}
		dc.addOrder(Order.desc("updated"));
		return effectDao.findByCriteriaP(dc, pageIndex, pageSize);
	}
	
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<WeEffect> topClickList(int categoryId, int top) {
		DetachedCriteria dc = effectDao.createDetachedCriteria();
		if (categoryId > 0){
			dc.add(Restrictions.eq("weCategory.id", (Integer)categoryId));
		}
		dc.addOrder(Order.desc("clickNum"));
		return effectDao.findByCriteria(dc, 1, top);
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<WeEffect> topDownList(int categoryId, int top) {
		DetachedCriteria dc = effectDao.createDetachedCriteria();
		if (categoryId > 0){
			dc.add(Restrictions.eq("weCategory.id", (Integer)categoryId));
		}
		dc.addOrder(Order.desc("downNum"));
		return effectDao.findByCriteria(dc, 1, top);
	}
	
	@Transactional(readOnly=true)
	public WeFile getFile(int id){
		return fileDao.get((Integer)id);
	}
	
	public void saveFile(WeFile file){
		fileDao.save(file);
	}
	
	public boolean delete(int id) {
		WeEffect e = effectDao.get(id);
		Integer fileId = e.getWeFile().getId();
		commentDao.update("delete from WeComment where weEffect.id=?", (Integer)id);
		effectDao.delete(e);
		return deleteFile(fileId);
	}
	
	public boolean deleteFile(int id){
		WeFile f = fileDao.get((Integer)id);		
		//从资源文件中删除		
		String uploads = ServletActionContext.getServletContext().getRealPath("uploads/" + f.getPath() + "/" + f.getName() + "." + f.getExt());
		String preview = ServletActionContext.getServletContext().getRealPath("preview/" + f.getPath() + "/" + f.getName());
		if (FileOperateUtils.delFile(uploads) && FileOperateUtils.delFile(preview)){
			//从数据库中删除
			fileDao.delete(f);
			return true;
		}
		return false;
	}

	@Transactional(readOnly=true)
	public boolean fileExists(String name) {
		return fileDao.count("name", name) > 0;
	}

	public void clickNumAdd(int id) {
		effectDao.update("update WeEffect set clickNum=clickNum+1 where id=?", (Integer)id);
	}

	public void downNumAdd(int fileId) {
		effectDao.update("update WeEffect set downNum=downNum+1 where weFile.id=?", (Integer)fileId);
	}

	public Pagination commentList(int effectId, int pageIndex, int pageSize) {
		DetachedCriteria dc = commentDao.createDetachedCriteria();
		if (effectId > 0){
			dc.add(Restrictions.eq("weEffect.id", (Integer)effectId));
		}
		dc.addOrder(Order.asc("id"));
		return commentDao.findByCriteriaP(dc, pageIndex, pageSize);
	}

	public void deleteComment(int commentId) {
		commentDao.deleteById(commentId);
	}

	public void saveComment(WeComment comment) {
		commentDao.save(comment);
	}

	public String getNickName(String username) {
		WeUser user = userDao.get("username", username);
		return user == null ? "" : user.getNickname();
	}
}
