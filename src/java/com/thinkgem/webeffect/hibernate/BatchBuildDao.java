package com.thinkgem.webeffect.hibernate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Batch Build DAO Tools
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: BatchBuildDao.java 2500 2010-05-21 20:35:46Z wzhen $
 */
public class BatchBuildDao {

	String filePath = "D:\\Workspaces\\MyEclipse\\webeffect\\src\\java\\com\\thinkgem\\webeffect\\";
	String packageName = "com.thinkgem.webeffect";
	boolean isUpdateFile = false;
	boolean isAnnotation = true;

	public BatchBuildDao() {
//		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> classNameList = new ArrayList<String>();
		classNameList = this.getClassNameList(this.filePath + "model\\");
		String content = "";
		for (String className : classNameList) {
			content = "/**\n * "+className+" DAO Interface\n * @author Batch Build DAO Tools\n * @version $Id$\n */\n"
					+ "package " + this.packageName + ".dao;\n\n" 
					+ "import " + this.packageName + ".hibernate.BaseDao;\n"
					+ "import " + this.packageName + ".model." + className + ";\n\n"
					+ "public interface " + className + "Dao" + " extends BaseDao<" + className + "> {\n\n" 
					+ "}\n";			
			if(this.createFile(filePath + "dao\\" + className + "Dao.java", content)){
				content = "/**\n * "+className+" DAO Implement\n * @author Batch Build DAO Tools\n * @version $Id$\n */\n"
						+ "package " + this.packageName + ".dao.impl;\n\n"
						+ "import org.springframework.stereotype.Repository;\n"
						+ "import " + this.packageName + ".hibernate.BaseDaoImpl;\n"
						+ "import " + this.packageName + ".dao." + className + "Dao;\n"
						+ "import " + this.packageName + ".model." + className + ";\n\n";
				if (isAnnotation){
					content += "@Repository\n";
				}
				content += "public class " + className + "DaoImpl extends BaseDaoImpl<" + className + "> implements " + className + "Dao {\n\n"
						+ "}";
				if (this.createFile(filePath + "dao\\impl\\" + className + "DaoImpl.java", content)){
					content = "<bean id=\"" + className.substring(0, 1).toLowerCase() + className.substring(1) + "Dao\" class=\"" + this.packageName + ".dao.impl." + className + "DaoImpl\">\n"
							+ "    <property name=\"sessionFactory\"><ref bean=\"sessionFactory\" /></property>\n"	
							+ "</bean>\n";
					System.out.print(content);
				}
			}
		}
		if (isAnnotation){
			content = "<!-- 使用annotation自动注册bean,并检查@Required,@Autowired的属性已被注入 -->\n"
					+ "<context:component-scan base-package=\""+packageName+"\" />";
			System.out.print(content);
		}
	}

	public List<String> getClassNameList(String path) {
		List<String> list = new ArrayList<String>();
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String fileName = files[i].getName();
				if (".java".equals(fileName
						.substring(fileName.lastIndexOf(".")))) {
					list.add(fileName.substring(0, fileName.lastIndexOf(".")));
				}
			}
		}
		return list;
	}

	public boolean createFile(String fileName, String content) {
		if (!isUpdateFile && new File(fileName).exists()){
			return false;
		}
		try {
			FileWriter fw = new FileWriter(fileName);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		new BatchBuildDao();
	}

}
