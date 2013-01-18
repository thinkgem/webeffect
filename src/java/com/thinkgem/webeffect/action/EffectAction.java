package com.thinkgem.webeffect.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.thinkgem.webeffect.model.WeCategory;
import com.thinkgem.webeffect.model.WeComment;
import com.thinkgem.webeffect.model.WeEffect;
import com.thinkgem.webeffect.model.WeFile;
import com.thinkgem.webeffect.service.CategoryService;
import com.thinkgem.webeffect.service.EffectService;
import com.thinkgem.webeffect.util.Encrypt;
import com.thinkgem.webeffect.util.FileOperateUtils;
import com.thinkgem.webeffect.util.FileUtils;
import com.thinkgem.webeffect.util.Utils;

/**
 * 用户控制器
 * @author WangZhen <thinkgem@163.com>
 * @version $Id$
 */
@Scope("prototype")
@Controller("effectAction")
public class EffectAction extends ValidateAction {
	
	private static final long serialVersionUID = 5087739323925878936L;
	
	private int id;//编号
	private int categoryId;//当前分类
	private WeEffect effect;//特效对象
	private List<WeCategory> categoryList;//分类列表
	private List<WeEffect> effectTopClickList;//热门排行
	private List<WeEffect> effectTopDownList;//下载排行
	
	//上传特效字段
	private List<Object> upload;
    private List<String> uploadContentType;
    private List<String> uploadFileName;
    
    //下载特效字段
    private String fileName;
    private InputStream inputStream;
    
    //查询字符串
    private String query;
    
    //评论相关字段
    private WeComment comment;
    
    //用户名
    private String username;
	    
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private EffectService effectService;
	
	/**
	 * 特效首页
	 * @return
	 */
	public String index(){
		categoryList = categoryService.list();
		pagination = effectService.list(categoryId, pageIndex, pageSize, query);
		effectTopClickList = effectService.topClickList(categoryId, 7);
		effectTopDownList = effectService.topDownList(categoryId, 7);
		return SUCCESS;
	}
	
	/**
	 * 上传特效
	 * @return
	 */
	public String upload(){
		categoryList = categoryService.list();
		if(isPost()){
			effect.setCategoryName(categoryService.get(effect.getWeCategory().getId()).getName());
			effect.setUsername(this.getCurrentUser().getUsername());
			effect.setCreated(new Timestamp(new Date().getTime()));
			effect.setUpdated(effect.getCreated());
			effect.setClickNum(0);
			effect.setDownNum(0);
			effectService.saveOrUpdate(effect);
			return renderText("success");
		}
		return SUCCESS;
	}
	public void validateUpload(){
		if(isPost()){
			if (effect != null){
				this.rangeLengthValidate("标题", effect.getTitle(), 1, 200);
				this.rangeLengthValidate("作者", effect.getTitle(), 1, 200);
				this.requiredStringValidate("描述", effect.getDescription());
				if(this.requiredValidate("特效文件", effect.getWeFile())){
					this.requiredValidate("特效文件", effect.getWeFile().getId());
				}
			}
		}
	}
	
	/**
	 * 上传特效文件
	 * @return
	 */
	public String uploadfile(){
		if (upload == null){
			return renderText("上传失败！");
		}
		File upload = (File)this.upload.get(0);
		String uploadFileName = this.uploadFileName.get(0);
		String name = "", ext = "";
		int size = 0;
			
		//获得上传路径
        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String path = ServletActionContext.getServletContext().getRealPath("uploads/" + date);
        //校验文件路径
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        try{        	
    		//获得文件名
        	name = Encrypt.fileMd5(upload.getPath());
        	if(effectService.fileExists(name)){
        		return renderText("此文件已经上传，请不要重复上传！");
        	}
        	ext = FileUtils.getExtension(uploadFileName);
        	//保存文件
            InputStream is = new FileInputStream(upload);
            OutputStream os = new FileOutputStream(path + "/" + name + "." + ext);
            byte[] buffer = new byte[8*1024];
            int length = size = is.available();
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.close();
			is.close();
        } catch (FileNotFoundException e) {
        	log.error("文件未找到，信息：" + e.getMessage());
            e.printStackTrace();
            return renderText("上传文件失败！");
        } catch (IOException e){
        	log.error("读写文件异常，信息："+e.getMessage());
            e.printStackTrace();
            return renderText("上传文件失败！");
        } catch (Exception e) {
        	log.error("上传文件异常，信息："+e.getMessage());
			e.printStackTrace();
			return renderText("上传文件失败！");
		}
        
        String path2 = ServletActionContext.getServletContext().getRealPath("preview/" + date);
        //校验文件路径
        File file2 = new File(path2);
        if (!file2.exists()) file2.mkdirs();
        
        //解压文件
        String zipFileName = path + "/" + name + "." + ext;
        String descFileName = path2 + "/" + name;
        if (!FileOperateUtils.unZipFiles(zipFileName, descFileName)){
        	FileOperateUtils.deleteFile(zipFileName);
        	return renderText("上传格式不正确！");
        }
        
        WeFile f = new WeFile(uploadFileName, name, ext, size, date);
        effectService.saveFile(f);
        
		return renderText(String.valueOf(f.getId()));
	}
	
	/**
	 * 查看特效
	 * @return
	 */
	public String view(){
		effectService.clickNumAdd(id);
		effect = effectService.get(id);
		effect.setWeFile(effectService.getFile(effect.getWeFile().getId()));
		return SUCCESS;
	}
	
	/**
	 * 编辑特效
	 * @return
	 */
	public String edit(){
		categoryList = categoryService.list();		
		if(isPost()){
			WeEffect e = effectService.get(effect.getId());
			if (getCurrentUser().getRole().intValue() != 1 && !e.getUsername().equals(getCurrentUser().getUsername())){
				return renderText("你没有操作权限");
			}
			e.setWeCategory(effect.getWeCategory());
			e.setCategoryName(categoryService.get(e.getWeCategory().getId()).getName());
			e.setTitle(effect.getTitle());
			e.setAuthor(effect.getAuthor());
			e.setDescription(effect.getDescription());
			e.setUpdated(new Timestamp(new Date().getTime()));
			e.setWeFileNew(effect.getWeFile());
			effectService.saveOrUpdate(e);
			return renderText("success");
		}else{
			effect = effectService.get(id);
			effect.setWeFile(effectService.getFile(effect.getWeFile().getId()));
		}
		return SUCCESS;
	}
	public void validateEdit(){
		if(isPost()){
			if (effect != null){
				this.rangeLengthValidate("标题", effect.getTitle(), 1, 200);
				this.rangeLengthValidate("作者", effect.getTitle(), 1, 200);
				this.requiredStringValidate("描述", effect.getDescription());
			}
		}
	}

	/**
	 * 删除特效
	 * @return
	 */
	public String delete(){
		WeEffect e = effectService.get(id);
		if (getCurrentUser().getRole().intValue() != 1 && !e.getUsername().equals(getCurrentUser().getUsername())){
			return renderText("你没有操作权限");
		}
		return renderText(effectService.delete(id) ? SUCCESS : "删除失败！");
	}
	
	/**
	 * 下载特效
	 */
	public String download(){
		effectService.downNumAdd(id);
		WeFile f = effectService.getFile(id);
		fileName = f.getTitle();
		String path = "/uploads/" + f.getPath() + "/" + f.getName() + "." + f.getExt();
		inputStream = ServletActionContext.getServletContext().getResourceAsStream(path);
		if (inputStream==null){
            this.addActionError("文件不存在！");
            return INPUT;
        }
        return SUCCESS;
	}
	
	/**
	 * 获得评论列表
	 * @return
	 */
	public String comment(){
		pagination = effectService.commentList(id, pageIndex, pageSize);
		return SUCCESS;
	}
	
	/**
	 * 添加评论
	 */
	public String addComment(){
		if (comment.getContent() == null){
			return renderText(INPUT);
		}
		comment.setContent(Utils.html2Text(comment.getContent()));
		if ("".equals(comment.getContent()) || comment.getContent().length()>255){
			return renderText(INPUT);
		}
		comment.setCreated(new Timestamp(new Date().getTime()));
		comment.setUsername(this.getCurrentUser().getUsername());
		effectService.saveComment(comment);
		return renderText(SUCCESS);
	}
	
	/**
	 * 删除评论
	 * @return
	 */
	public String deleteComment(){
		effectService.deleteComment(id);
		return renderText(SUCCESS);
	}
	
	/**
	 * 获得昵称
	 * @return
	 */
	public String getNickname(){
		return renderText(effectService.getNickName(username));
	}
	
	
	//----------- get set
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public List<WeCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<WeCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Object> getUpload() {
		return upload;
	}

	public void setUpload(List<Object> upload) {
		this.upload = upload;
	}

	public List<String> getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public WeEffect getEffect() {
		return effect;
	}

	public void setEffect(WeEffect effect) {
		this.effect = effect;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		String downFileName = fileName;
		try {
			if(ServletActionContext.getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE")!=-1){
				downFileName=URLEncoder.encode(this.fileName,"UTF-8");//IE浏览器
			}else{
				downFileName= new String(this.fileName.getBytes(),"ISO-8859-1");//firefox浏览器
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downFileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<WeEffect> getEffectTopClickList() {
		return effectTopClickList;
	}

	public void setEffectTopClickList(List<WeEffect> effectTopClickList) {
		this.effectTopClickList = effectTopClickList;
	}

	public List<WeEffect> getEffectTopDownList() {
		return effectTopDownList;
	}

	public void setEffectTopDownList(List<WeEffect> effectTopDownList) {
		this.effectTopDownList = effectTopDownList;
	}

	public WeComment getComment() {
		return comment;
	}

	public void setComment(WeComment comment) {
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
