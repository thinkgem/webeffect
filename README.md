#WebEffect网页特效集锦系统
=========

##介绍

网页特效是用程序代码在网页中实现特殊效果或者特殊功能的一种技术，它为网页添加活跃气氛，增加了网站的亲和力。在公司资源管理中由于每个员工都会收集些网页特效代码，为了实现资源共享，为了在辛勤的工作中能给网页制作提供一些便利，从而产生了网页特效集锦系统。

##使用技术

Java JDK1.6、Spring Framework 3.0、Status2.1、Hibernate 3、Freemarker 2.3

##快速安装部署

1. 安装Tomcat6 Web容器及MySql5数据库并启动服务。
2. 将项导入到Eclipse中，编译并部署到Tomcat发布目录下
3. 将db\webeffect.sql文件导入到MySql数据库
4. 将MySql的用户名设置为root，密码设置为空，也可直接修改软件的连接数据库配置/WEB-INF/classes/jdbc.properties
5. 打开浏览器，输入地址：http://localhost:8080/webeffect
6. 管理员用户名：admin 密码：admin
7. 普通用户名：user 密码：user

##更多文档

* [网页特效集锦系统 - 需求说明书.doc](https://github.com/thinkgem/webeffect/raw/master/%E7%BD%91%E9%A1%B5%E7%89%B9%E6%95%88%E9%9B%86%E9%94%A6%E7%B3%BB%E7%BB%9F%20-%20%E9%9C%80%E6%B1%82%E8%AF%B4%E6%98%8E%E4%B9%A6.doc)
* [网页特效集锦系统 - 数据库设计.doc](https://github.com/thinkgem/webeffect/raw/master/%E7%BD%91%E9%A1%B5%E7%89%B9%E6%95%88%E9%9B%86%E9%94%A6%E7%B3%BB%E7%BB%9F%20-%20%E6%95%B0%E6%8D%AE%E5%BA%93%E8%AE%BE%E8%AE%A1.doc)
