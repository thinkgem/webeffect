package com.thinkgem.webeffect.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.thinkgem.webeffect.model.WeCategory;
import com.thinkgem.webeffect.service.CategoryService;
import com.thinkgem.webeffect.service.EffectService;
import com.thinkgem.webeffect.util.Validate;

/**
 * 特效分类控制器
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Scope("prototype")
@Controller("categoryAction")
public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = -4427797236687774982L;
	
	private int id;

	private List<WeCategory> categoryList;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private EffectService effectService;
	
	public String index(){
		categoryList = categoryService.list();
		return SUCCESS;
	}
	
	/**
	 *  删除资源分类
	 * @return
	 */
	public String delete(){
		pagination = effectService.list(id, 1, 1, null);
		if(pagination.getList().size()>0){
			return renderText("删除失败，分类不为空，请删除改分类特效后再操作！");
		}
		categoryService.delete(id);
		return renderText(SUCCESS);
	}
	public void validateDel(){
		if(id==0) this.addActionError("编号不能为0");
	}
	
	/**
	 * 批量更新分类
	 * @return
	 */
	public String update(){
		//数据校验
		for(int i=0; i<categoryList.size(); i++){
			WeCategory category = categoryList.get(i);
			if (Validate.required(category)){
				if (!Validate.rangeLength(category.getName(), 1, 50)){
					return renderText("列表中的名称长度在1-50个字符之间！");					
				}
				if (!Validate.rangeLength(category.getDescription(), 0, 255)){
					return renderText("列表中的描述长度在0-50个字符之间！");					
				}
				if (!Validate.required(category.getWeight())){
					category.setWeight(0);
				}
			}else{
				categoryList.remove(i);
			}
		}
		//更新分类
		categoryService.saveOrUpdateAll(categoryList);
		return renderText(SUCCESS);
	}
	public void validateUpdate(){
		
	}

	//--------------- get set
	
	public List<WeCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<WeCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
