package com.thinkgem.webeffect.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

/**
 * WeFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "unchecked", "serial" })
public class WeFile implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String name;
	private String ext;
	private Integer size;
	private String path;
	private Set weEffects = new HashSet(0);

	// Constructors

	/** default constructor */
	public WeFile() {
	}

	/** minimal constructor */
	public WeFile(String title, String name, String ext, Integer size,
			String path) {
		this.title = title;
		this.name = name;
		this.ext = ext;
		this.size = size;
		this.path = path;
	}

	/** full constructor */
	public WeFile(String title, String name, String ext, Integer size,
			String path, Set weEffects) {
		this.title = title;
		this.name = name;
		this.ext = ext;
		this.size = size;
		this.path = path;
		this.weEffects = weEffects;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Set getWeEffects() {
		return this.weEffects;
	}

	public void setWeEffects(Set weEffects) {
		this.weEffects = weEffects;
	}
	
	// custom
	
	public String getPreviewName(){
		String path = ServletActionContext.getServletContext().getRealPath("preview/" + this.path + "/" + this.name);
		String previewName = "";
		File dirFile = new File(path);
		if (dirFile.exists() && dirFile.isDirectory()){
			File[] files = dirFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String name = files[i].getName();
					String ext = name.substring(name.lastIndexOf(".")+1).toLowerCase();
					if ("".equals(previewName) && ("html".equals(ext) || "htm".equals(ext))){
						previewName = name;
					}
					if ("index.html".equals(name) || "index.htm".equals(name) 
						|| "default.html".equals(name) || "default.htm".equals(name)){
						previewName = name;
					}
				}
			}
		}
		try {
			return URLEncoder.encode(previewName, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return previewName;
	}

}