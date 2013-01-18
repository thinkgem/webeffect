/*
SQLyog Ultimate v8.32 
MySQL - 5.1.41 : Database - webeffect
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`webeffect` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `webeffect`;

/*Table structure for table `we_category` */

DROP TABLE IF EXISTS `we_category`;

CREATE TABLE `we_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='特效分类';

/*Data for the table `we_category` */

insert  into `we_category`(`id`,`name`,`description`,`weight`) values (1,'鼠标特效','',1),(2,'页面特效','',1),(3,'背景特效','',1),(4,'导航菜单','',1),(5,'状态栏类','',1),(6,'文本特效','',1),(7,'链接特效','',1),(8,'图形特效','',1),(9,'窗口特效','',1),(10,'按钮特效','',1),(11,'日期时间','',1),(12,'CSS相关','',1),(13,'代码生成','',1),(14,'计数转换','',1),(15,'系统相关','',1),(16,'综合特效','网页特效综合技术',10),(17,'广告代码','',0);

/*Table structure for table `we_comment` */

DROP TABLE IF EXISTS `we_comment`;

CREATE TABLE `we_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `effect_id` int(11) NOT NULL COMMENT '评论的特效',
  `username` varchar(20) NOT NULL COMMENT '评论者用户名',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `created` datetime NOT NULL COMMENT '发表时间',
  PRIMARY KEY (`id`),
  KEY `FK_we_comment` (`effect_id`),
  CONSTRAINT `FK_we_comment` FOREIGN KEY (`effect_id`) REFERENCES `we_effect` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `we_comment` */

/*Table structure for table `we_effect` */

DROP TABLE IF EXISTS `we_effect`;

CREATE TABLE `we_effect` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `category_id` int(11) NOT NULL COMMENT '分类编号',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `title` varchar(200) NOT NULL COMMENT '特效标题',
  `description` text NOT NULL COMMENT '描述/使用说明',
  `file_id` int(11) NOT NULL COMMENT '文件编号',
  `author` varchar(200) NOT NULL COMMENT '作者',
  `username` varchar(20) NOT NULL COMMENT '上传者',
  `created` datetime NOT NULL COMMENT '上传时间',
  `updated` datetime NOT NULL COMMENT '最后修改时间',
  `click_num` int(11) NOT NULL DEFAULT '1' COMMENT '点击次数',
  `down_num` int(11) NOT NULL DEFAULT '1' COMMENT '下载次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `category_id` (`category_id`),
  KEY `title` (`title`),
  KEY `FK_we_effect2` (`file_id`),
  KEY `author` (`author`),
  CONSTRAINT `FK_we_effect` FOREIGN KEY (`category_id`) REFERENCES `we_category` (`id`),
  CONSTRAINT `FK_we_effect2` FOREIGN KEY (`file_id`) REFERENCES `we_file` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='特效表';

/*Data for the table `we_effect` */

insert  into `we_effect`(`id`,`category_id`,`category_name`,`title`,`description`,`file_id`,`author`,`username`,`created`,`updated`,`click_num`,`down_num`) values (3,12,'CSS相关','带宜忌数据的万年历','带宜忌数据的万年历的',3,'未知','admin','2010-09-02 17:36:08','2010-09-03 11:21:32',37,2),(6,2,'页面特效','超酷3D照片展示广告代码','超酷3D照片展示广告代码',6,'gem','admin','2010-09-03 09:24:38','2010-09-03 09:51:12',27,1),(8,16,'综合特效','jquery制作一只飞行乌鸦','jquery制作一只飞行乌鸦，可控制帧数及飞行速度.zip',8,'www','admin','2010-09-03 11:46:25','2010-09-03 11:46:25',15,0),(9,1,'鼠标特效','鼠标经过<strong>商品</strong>列表，显示其产品缩略图','鼠标经过商品列表，显示其产品缩略图。<strong>高亮字体</strong>',9,'dd','admin','2010-09-03 11:46:45','2010-09-04 10:37:59',29,0),(10,16,'综合特效','用于弹出登录框的JQuery浮动层，点击登陆立即弹出','用于弹出登录框的JQuery浮动层，点击登陆立即弹出，页面无法操作。.zip',10,'dd','admin','2010-09-03 11:47:01','2010-09-03 13:41:29',40,0),(12,16,'综合特效','jquery五屏焦点图切换效果，兼容当前主流浏览器。','jquery五屏焦点图切换效果，兼容当前主流浏览器。.zip',12,'dd','admin','2010-09-03 11:56:29','2010-09-03 13:41:47',8,0),(13,16,'综合特效','五屏flash+js焦点图切换，定时切换，自定义时间及图片','五屏flash+js焦点图切换，定时切换，自定义时间及图片',13,'gg','admin','2010-09-03 11:56:45','2010-09-03 14:36:47',41,3),(14,16,'综合特效','淘宝男人画报焦点图切换代码','淘宝男人画报焦点图切换代码',15,'ddd','user','2010-09-09 16:22:15','2010-09-10 09:53:41',10,3);

/*Table structure for table `we_file` */

DROP TABLE IF EXISTS `we_file`;

CREATE TABLE `we_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(128) NOT NULL COMMENT '文件标题',
  `name` char(32) NOT NULL COMMENT '文件名',
  `ext` varchar(16) NOT NULL COMMENT '扩展名',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `path` varchar(128) NOT NULL COMMENT '文件存放路径',
  PRIMARY KEY (`id`),
  KEY `title` (`name`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `we_file` */

insert  into `we_file`(`id`,`title`,`name`,`ext`,`size`,`path`) values (3,'带宜忌数据的万年历.zip','16531ea687739bf409c6534dcb1ca25a','zip',97046,'201009'),(6,'超酷3D照片展示广告代码.zip','4825e092563414a1ab4e2a736d5605b1','zip',1054804,'201009'),(8,'jquery制作一只飞行乌鸦，可控制帧数及飞行速度.zip','39270987aeced767107be2a2ee9f6a7c','zip',530313,'201009'),(9,'鼠标经过商品列表，显示其产品缩略图。.zip','07524ed73f5b8bba95295f69f2b8ee42','zip',19247,'201009'),(10,'用于弹出登录框的JQuery浮动层，点击登陆立即弹出，页面无法操作。.zip','ef4f297b5ce895f387878ffc1b0148ad','zip',29968,'201009'),(12,'jquery五屏焦点图切换效果，兼容当前主流浏览器。.zip','026b9927bb9e9ea5228bbc1d77b6be66','zip',119351,'201009'),(13,'五屏flash+js焦点图切换，定时切换，自定义时间及图片.zip','d11be4b37153c7999e782912e18737d1','zip',270591,'201009'),(15,'淘宝男人画报焦点图切换代码.zip','e82a3f5c76365ef5493daa7240979fa1','zip',218523,'201009');

/*Table structure for table `we_function` */

DROP TABLE IF EXISTS `we_function`;

CREATE TABLE `we_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `uri` varchar(50) NOT NULL COMMENT 'URI',
  `description` varchar(50) NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='功能表';

/*Data for the table `we_function` */

insert  into `we_function`(`id`,`uri`,`description`) values (1,'/index','引导页'),(2,'/login','登陆系统'),(3,'/logout','退出系统'),(4,'/effect/index','特效首页'),(5,'/effect/upload','上传特效'),(6,'/effect/uploadfile','上传特效文件'),(7,'/effect/edit','编辑特效'),(8,'/effect/delete','删除特效'),(9,'/effect/view','查看特效'),(10,'/effect/download','下载特效'),(11,'/effect/comment','浏览评论'),(12,'/effect/addComment','发表评论'),(13,'/effect/getNickname','获得昵称'),(14,'/setting/category/index','分类管理首页'),(15,'/setting/category/delete','删除分类'),(16,'/setting/category/update','更新分类'),(17,'/setting/user/index','用户管理首页'),(18,'/setting/user/add','添加用户'),(19,'/setting/user/edit','编辑用户'),(20,'/setting/user/delete','删除用户'),(21,'/center/changeInfo','修改个人信息'),(22,'/center/changePassword','修改个人密码');

/*Table structure for table `we_role` */

DROP TABLE IF EXISTS `we_role`;

CREATE TABLE `we_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `we_role` */

insert  into `we_role`(`id`,`name`) values (1,'系统管理员'),(2,'高级用户'),(3,'普通用户');

/*Table structure for table `we_role_function` */

DROP TABLE IF EXISTS `we_role_function`;

CREATE TABLE `we_role_function` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `function_id` int(11) NOT NULL COMMENT '功能表编号',
  PRIMARY KEY (`role_id`,`function_id`),
  KEY `FK_we_role_function_to_function` (`function_id`),
  CONSTRAINT `FK_we_role_function_to_function` FOREIGN KEY (`function_id`) REFERENCES `we_function` (`id`),
  CONSTRAINT `FK_we_role_function_to_role` FOREIGN KEY (`role_id`) REFERENCES `we_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `we_role_function` */

/*Table structure for table `we_user` */

DROP TABLE IF EXISTS `we_user`;

CREATE TABLE `we_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `role` int(2) NOT NULL COMMENT '角色',
  `login_num` int(11) NOT NULL DEFAULT '1' COMMENT '登陆次数',
  `login_time` datetime NOT NULL COMMENT '登陆时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`,`password`),
  KEY `desciption` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `we_user` */

insert  into `we_user`(`id`,`username`,`password`,`nickname`,`description`,`role`,`login_num`,`login_time`) values (1,'admin','0c7540eb7e65b553ec1ba6b20de79608','管理员','最高管理员',1,93,'2010-09-10 09:51:43'),(2,'user','68f32b5f0943904f5eac13096f25d756','用户','',2,17,'2010-09-10 09:54:03'),(3,'thinkgem','0b598b95b1398dc1cb9f013e92aa6b75','琛琛','thinkgem',1,2,'2010-09-09 16:22:54'),(6,'dddd','930c25aaf1c10aa7807421f67fec9c56','ddaaaa','dfsdfa',2,4,'2010-09-09 16:23:12'),(7,'eeee','a8733a13f573ce69a4e6e5b9ebb602e4','eeee','eeeeaaa',1,1,'2010-09-03 11:19:09');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
