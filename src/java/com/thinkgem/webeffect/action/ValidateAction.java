package com.thinkgem.webeffect.action;

import com.thinkgem.webeffect.util.Validate;

/**
 * 字段验证操作类
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: ValidateAction.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public class ValidateAction extends BaseAction {
	
	private static final long serialVersionUID = 1053168307737491204L;
	
	/**
	 * 验证码
	 */
	protected String randomCode;

	/**
	 * 空值验证
	 * @param s 提示名称
	 * @param value 验证对象
	 * @return true 通过   false 未通过
	 */
	public boolean requiredValidate(String s, Object value){
		boolean result = Validate.required(value);
		if (!result) this.addActionError(s + " 不能为空");
		return result;
	}
	
	/**
	 * 空字符串验证
	 * @param s 提示名称
	 * @param value 验证字符串
	 * @return boolean
	 */
	public boolean requiredStringValidate(String s, String value){
		boolean result = Validate.requiredString(value);
		if (!result) this.addActionError(s + " 不能为空");
		return result;
	}
	
	/**
	 * 字符串最小长度验证
	 * @param s 提示名称
	 * @param value 验证字符串
	 * @param min 最小长度值
	 * @return boolean
	 */
	public boolean minLengthValidate(String s, String value, int min){
		boolean result = Validate.minLength(value, min);
		if (!result) this.addActionError(s + " 不能短于 " + min);
		return result;
	}
	
	/**
	 * 字符串最大长度验证
	 * @param s 提示名称
	 * @param value 验证字符串
	 * @param max 最大长度值
	 * @return boolean
	 */
	public boolean maxLengthValidate(String s, String value, int max){
		boolean result = Validate.maxLength(value, max);
		if (!result) this.addActionError(s + " 不能长于 " + max);
		return result;
	}
	
	/**
	 * 字符串长度范围验证
	 * @param s 提示名称
	 * @param value 验证字符串
	 * @param min 最小长度
	 * @param max 最大长度
	 * @return boolean
	 */
	public boolean rangeLengthValidate(String s, String value, int min, int max){
		boolean result = Validate.rangeLength(value, min, max);
		if (!result) this.addActionError(s + " 长度不在  " + min + "-" + max + " 之间");
		return result;
	}
	
	/**
	 * 数字验证
	 * @param s 提示名称
	 * @param value 验证字符串
	 * @return boolean
	 */
	public boolean isNumberValidate(String s, String value){
		boolean result = Validate.isNumber(value);
		if (!result) this.addActionError(s + " 不是数值");
		return result;
	}
	
	/**
	 * 最小整数验证
	 * @param s 提示名称
	 * @param value 验证整数
	 * @param min 最小整数值
	 * @return boolean
	 */
	public boolean minValidate(String s, int value, int min){
		boolean result = Validate.min(value, min);
		if (!result) this.addActionError(s + " 不能小于 " + min);
		return result;
	}
	
	/**
	 * 最大整数验证
	 * @param s 提示名称
	 * @param value 验证整数
	 * @param max 最大整数值
	 * @return boolean
	 */
	public boolean maxValidate(String s, int value, int max){
		boolean result = Validate.max(value, max);
		if (!result) this.addActionError(s + " 不能大于 " + max);
		return result;
	}
	
	/**
	 * 整数范围验证
	 * @param s 提示名称
	 * @param value 验证整数
	 * @param min 最小整数值
	 * @param max 最大整数值
	 * @return
	 */
	public boolean rangeValidate(String s, int value, int min, int max){
		boolean result = Validate.range(value, min, max);
		if (!result) this.addActionError(s + " 不在  " + min + "-" + max + " 之间");
		return result;
	}
	
	/**
	 * E-mail地址验证
	 * @param value 验证E-mail地址
	 * @return boolean
	 */
	public boolean emailValidate(String value){
		boolean result = Validate.email(value);
		if (!result) this.addActionError("E-mail 地址验证失败");
		return result;
	}
	
	/**
	 * URL验证
	 * @param value 验证URL
	 * @return boolean
	 */
	public boolean urlValidate(String value){
		boolean result = Validate.url(value);
		if (!result) this.addActionError("URL 地址验证失败");
		return result;
	}
	
	/**
	 * 日期验证
	 * @param value 验证日志字符串
	 * @return boolean
	 */
	public boolean dateValidate(String value){
		boolean result = Validate.date(value);
		if (!result) this.addActionError("日期 验证失败");
		return result;
	}
	
	/**
	 * 对象等值验证
	 * @param s 提示名称
	 * @param value1 验证对象1
	 * @param value2 验证对象2
	 * @return boolean
	 */
	public boolean equalValidate(String s, Object value1, Object value2){
		boolean result = Validate.equal(value1, value2);
		if (!result) this.addActionError(s + " 不能匹配");
		return result;	
	}
	
	/**
	 * 字符串等值验证
	 * @param s 提示名称
	 * @param value1 验证字符串1
	 * @param value2 验证字符串2
	 * @return boolean
	 */
	public boolean equalStringValidate(String s, String value1, String value2){
		boolean result = Validate.equalString(value1, value2);
		if (!result) this.addActionError(s + " 不能匹配");
		return result;
	}
		
	/**
	 * 用户名验证，4-20位字母或数字开头，允许字母数字下划线
	 * @param value 验证用户名字符串 
	 * @return boolean
	 */
	public boolean usernameValidate(String value){
		boolean result = Validate.username(value);
		if (!result) this.addActionError("用户名：4~20个字符，包括字母、数字、下划线,字母或数字开头");
		return result;
	}
	
	/**
	 * 真是姓名验证，2-5个汉字
	 * @param value 验证用户名字符串 
	 * @return boolean
	 */
	public boolean realnameValidate(String value){
		boolean result = Validate.realname(value);
		if (!result) this.addActionError("真实姓名只能为2-5个汉字");
		return result;
	}
	
	/**
	 * 身份证验证，15-18位
	 * @param value 验证用户名字符串 
	 * @return boolean
	 */
	public boolean idcardValidate(String value){
		boolean result = Validate.idcard(value);
		if (!result) this.addActionError("身份证 验证失败");
		return result;
	}
	
	
	/**
	 * 随机验证码验证
	 * @param value 用户输入验证码字符串
	 * @return boolean
	 */
	public boolean randomCodeValidate(String value){
		boolean result = Validate.randomCode(value);
		if (!result) this.addActionError("随机验证码 验证失败");
		return result;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}
	
}
