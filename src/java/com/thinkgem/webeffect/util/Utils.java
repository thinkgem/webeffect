package com.thinkgem.webeffect.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 常用工具助手
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: Utils.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public abstract class Utils {

	/**
	 * 通过生日计算 年龄
	 * @param birthDay 生日日期
	 * @return 年龄
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
        	return -1;
//            throw new IllegalArgumentException(
//                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }
		return age;
	}
	
	/**
	 * 截取字符串
	 * 
	 * @param str 目标字符串
	 * @param length 截取长度
	 */
	public static String subString(String str, int length) {
		// 限制输出字符串
		String result = "";
		if (str.length() < length) {
			result = str;
		} else {
			result = str.substring(0, length) + "...";
		}
		return result;
	}

	/**
	 * 过滤SQL危险字符
	 * 
	 * @param str 目标字符
	 */
	public static String replaceSql(String str) {
		return str.toLowerCase().replace("<", "&lt;").replace(">", "&gt;")
				.replace(" ", "").replace("'", "").replace(";", "").replace(
						"select", "").replace("and", "").replace("exec", "")
				.replace("insert", "").replace("delete", "").replace("update",
						"").replace("count", "").replace("*", "").replace("%",
						"").replace("chr", "").replace("char", "").replace(
						"mid", "").replace("master", "")
				.replace("truncate", "").replace("declare", "").trim();
	}

	/**
	 * 获取服务端IP
	 * 
	 * @param url 服务器域名
	 */
	public static String getServerIP(String url) {
		try {
			return InetAddress.getByName(url).getHostAddress();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * 获得客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 日期时间格式化
	 * 
	 * @param date 格式化的日期对象
	 * @param format 格式化字符串 如：yyyy-MM-dd HH:mm:ss
	 * @return 格式化后的字符串
	 */
	public static String dataFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 验证后缀名
	 */
	public static boolean testpostfix(String filename) {

		// 定义可上传文件的 类型
		List<String> fileTypes = new ArrayList<String>();

		// 图片
		fileTypes.add("jpg");
		fileTypes.add("jpeg");
		fileTypes.add("bmp");
		fileTypes.add("gif");
		fileTypes.add("png");
		fileTypes.add("dwg");

		// 附件
		fileTypes.add("rar");
		fileTypes.add("zip");
		// 文档
		fileTypes.add("txt");
		fileTypes.add("doc");
		fileTypes.add("docx");
		fileTypes.add("xls");
		fileTypes.add("xls");
		fileTypes.add("ppt");
		fileTypes.add("pptx");
		fileTypes.add("rtf");
		fileTypes.add("chm");
		fileTypes.add("pdf");

		// 得到文件尾数 并 进行小写转换
		String postfix = filename.substring(filename.lastIndexOf(".") + 1)
				.toLowerCase();
		return fileTypes.contains(postfix) ? true : false;
	}

	/**
	 * 过滤web格试文本内容(script,style,html)
	 * 
	 * @param inputString
	 * @return 文本字符串
	 */
	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>

			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>

			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			// htmlStr = htmlStr.replace("", "\n\r\t");
			htmlStr = htmlStr.replace("&nbsp;", "");
			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	/**
	 * 压缩图片方法
	 * 
	 * @param oldFile 将要压缩的图片
	 * @param width 压缩宽
	 * @param height 压缩高
	 * @param quality 压缩清晰度 建议为1.0
	 * @param smallIcon 压缩图片后,添加的扩展名（在图片后缀名前添加）
	 * @param percentage 是否等比压缩 若true宽高比率将将自动调整
	 * @author zhengsunlei
	 * @return 如果处理正确返回压缩后的文件名 null则参数可能有误
	 */
	public static boolean doCompress(String oldFile, int width, int height,
			float quality, String newFile, boolean percentage) {
		if (oldFile != null && width > 0 && height > 0) {
			// String newImage = null;
			try {
				File file = new File(oldFile);
				// 文件不存在
				if (!file.exists()) {
					return false;
				}
				/* 读取图片信息 */
				Image srcFile = ImageIO.read(file);
				int new_w = width;
				int new_h = height;
				if (percentage) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) srcFile.getWidth(null))
							/ (double) width + 0.1;
					double rate2 = ((double) srcFile.getHeight(null))
							/ (double) height + 0.1;
					double rate = rate1 > rate2 ? rate1 : rate2;
					new_w = (int) (((double) srcFile.getWidth(null)) / rate);
					new_h = (int) (((double) srcFile.getHeight(null)) / rate);
				}
				/* 宽高设定 */
				BufferedImage tag = new BufferedImage(new_w, new_h,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
				// /*压缩后的文件名 */
				// String filePrex = oldFile.substring(0,
				// oldFile.lastIndexOf('.'));
				// newImage = filePrex + smallIcon +
				// oldFile.substring(filePrex.length());
				/* 压缩之后临时存放位置 */
				FileOutputStream out = new FileOutputStream(newFile);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				/* 压缩质量 */
				jep.setQuality(quality, true);
				encoder.encode(tag, jep);
				out.close();
				srcFile.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
