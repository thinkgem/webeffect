package com.thinkgem.webeffect.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 加密解密
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: Encrypt.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public abstract class Encrypt {

	private static String digest(String s, String a) {
		try {
			MessageDigest md = MessageDigest.getInstance(a);
			md.update(s.getBytes());
			byte[] bytes = md.digest();
			String hax = "";
			for (int i = 0; i < bytes.length; i++) {
				hax += Integer
						.toHexString((0x000000ff & bytes[i]) | 0xffffff00)
						.substring(6);
			}
			return hax;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String md5(String s) {
		return digest(s, "MD5");
	}

	public static String sha(String s) {
		return digest(s, "SHA");
	}

	/**
	 * 返回文件的md5值
	 * 
	 * @param filePath
	 *            文件的全路径
	 * @return 文件md5值
	 */
	public static String fileMd5(String filePath) throws Exception {
		String hashType = "MD5";
		return getHash(filePath, hashType);
	}

	public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getHash(String fileName, String hashType)
			throws Exception {
		InputStream fis;
		fis = new FileInputStream(fileName);
		byte[] buffer = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());

	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
}
