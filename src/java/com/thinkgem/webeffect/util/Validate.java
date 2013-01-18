package com.thinkgem.webeffect.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;

import cn.idchecker.check.Checker;

import com.opensymphony.xwork2.util.URLUtil;

/**
 * 字段验证类
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: Validate.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public abstract class Validate {
	
	/**
	 * 空值验证
	 * @param value 验证对象
	 * @return true 通过   false 未通过
	 */
	public static boolean required(Object value){
		return value != null;
	}
	
	/**
	 * 空字符串验证
	 * @param value 验证字符串
	 * @return boolean
	 */
	public static boolean requiredString(String value){
		return value != null && value.trim().length() > 0;
	}
	
	/**
	 * 字符串最小长度验证
	 * @param value 验证字符串
	 * @param min 最小长度值
	 * @return boolean
	 */
	public static boolean minLength(String value, int min){
		return required(value) && value.length() >= min;
	}
	
	/**
	 * 字符串最大长度验证
	 * @param value 验证字符串
	 * @param max 最大长度值
	 * @return boolean
	 */
	public static boolean maxLength(String value, int max){
		return required(value) && value.length() <= max;
	}
	
	/**
	 * 字符串长度范围验证
	 * @param value 验证字符串
	 * @param min 最小长度
	 * @param max 最大长度
	 * @return boolean
	 */
	public static boolean rangeLength(String value, int min, int max){
		return required(value) && value.length() >= min && value.length() <= max;
	}
	
	/**
	 * 数字验证
	 * @param value 验证字符串
	 * @return boolean
	 */
	public static boolean isNumber(String value){
		return required(value) && matches("^[0-9]+$", value);
	}
	
	/**
	 * 最小整数验证
	 * @param value 验证整数
	 * @param min 最小整数值
	 * @return boolean
	 */
	public static boolean min(int value, int min){
		return value >= min;
	}
	
	/**
	 * 最大整数验证
	 * @param value 验证整数
	 * @param max 最大整数值
	 * @return boolean
	 */
	public static boolean max(int value, int max){
		return value <= max;
	}
	
	/**
	 * 整数范围验证
	 * @param value 验证整数
	 * @param min 最小整数值
	 * @param max 最大整数值
	 * @return
	 */
	public static boolean range(int value, int min, int max){
		return value >= min && value <= max;
	}
	
	/**
	 * E-mail地址验证
	 * @param value 验证E-mail地址
	 * @return boolean
	 */
	public static boolean email(String value){ 
//		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		String regex = "(^[a-zA-Z0-9]|^[a-zA-Z0-9][\\w-_\\.]*[a-zA-Z0-9])@(\\w+\\.)+\\w+$";
		return required(value) && matches(regex, value);
	}
	
	/**
	 * URL验证
	 * @param value 验证URL
	 * @return boolean
	 */
	public static boolean url(String value){
		return required(value) && URLUtil.verifyUrl(value); 
	}
	
	/**
	 * 日期验证
	 * @param value 验证日志字符串
	 * @return boolean
	 */
	public static boolean date(String value){
		String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\/\\/\\s\\-]?((((0?" +
	      "[13578])|(1[02]))[\\/\\/\\s\\-]?((0?[1-9])|([1-2][0-9])|(3[01])))" +
	      "|(((0?[469])|(11))[\\/\\/\\s\\-]?((0?[1-9])|([1-2][0-9])|(30)))|" +
	      "(0?2[\\/\\/\\s\\-]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12" +
	      "35679])|([13579][01345789]))[\\/\\/\\s\\-]?((((0?[13578])|(1[02]))" +
	      "[\\/\\/\\s\\-]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +
	      "[\\/\\/\\s\\-]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/\\/\\s\\-]?((0?[" +
	      "1-9])|(1[0-9])|(2[0-8]))))))" +
	      "(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		return required(value) && matches(regex, value);
	}
	
	/**
	 * 对象等值验证
	 * @param value1 验证对象1
	 * @param value2 验证对象2
	 * @return boolean
	 */
	public static boolean equal(Object value1, Object value2){
		return required(value1) && value1.equals(value2);		
	}
	
	/**
	 * 字符串等值验证
	 * @param value1 验证字符串1
	 * @param value2 验证字符串2
	 * @return boolean
	 */
	public static boolean equalString(String value1, String value2){
		return required(value1) && value1.equals(value2);		
	}
		
	/**
	 * 用户名验证，4-20位字母或数字开头，允许字母数字下划线
	 * @param value 验证用户名字符串 
	 * @return boolean
	 */
	public static boolean username(String value){
		return required(value) && matches("^[a-zA-Z0-9][a-zA-Z0-9_]{3,19}$",value);
	}
	
	/**
	 * 真是姓名验证，2-5个汉字
	 * @param value 验证用户名字符串 
	 * @return boolean
	 */
	public static boolean realname(String value){
		return required(value) && matches("^[\u4e00-\u9fa5]{2,5}$",value);
	}
	
	/**
	 * 身份证验证，15-18位
	 * @param value 验证用户名字符串 
	 * @return boolean
	 */
	public static boolean idcard(String value){		
		//return required(value) && matches("^[1-9]([0-9]{16}[xX]|[0-9]{14}|[0-9]{17})$",value);
		return required(value) && new Checker(value).check();
	}
	
	
	/**
	 * 随机验证码验证
	 * @param value 用户输入验证码字符串
	 * @return boolean
	 */
	public static boolean randomCode(String value){
		String randomCode = (String)ServletActionContext.getRequest().getSession().getAttribute("randomCode");
		return required(randomCode) && randomCode.equals(value);
	}
	
	/**
	 * 使用正则表达式验证
	 * @param regex 正则表达式字符串
	 * @param value 验证字符串
	 * @return boolean
	 */
	public static boolean matches(String regex, String value) {
	   Pattern p = Pattern.compile(regex);  
	   Matcher m = p.matcher(value);
	   return m.find();
	}
}
