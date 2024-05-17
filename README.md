# TreeHelp 无限下钻树生成及查询
===============

## 安装环境

>+ jdk版本：1.8+
>+ mybatis plus：3.4.1
>+ fastjson:1.2.75
>+ 其他具体依赖包见pom.xml


### 结构说明
~~~
1.    包demo为测试代码，主要演示树节点生成及树列表查询的工具类使用方式
2.    包treehelp为树生成及查询工具类包，demo包下的操作均基于此包完成，为通用实现
3.    TreeNodeVo用于数据展示的VO,TreeEntity为用于数据库操作的实体，demo中具体数据库表所用到的VO和ENTITY均分别继承这两个文件
4.	  接口TreeCodeGenerate默认实现可查询和生成TREE_CODE、TREE_LEVEL、SORT这三个字段的方法，为泛型类，demo包的数据库表对应的Mapper继承这个类
5.	  GenerateTreeUtil提供将查询列表转为父子结构树的方法和生成TREE_CODE的实现
~~~

### GenerateTreeUtil类说明
	1.getNextCode
		/**
		 * @author: wc
		 * @description: 方法说明:
		 *                 1、根节点生成
		 *                    1) 库中没有任何节点 传参 code = null,生成第一个节点A01
		 *                 2、子节点生成
		 *                    1) getNextCode(code,2); code为父节点 如传入A01 生成A01A01
		 *                 3、同级下一个节点生成
		 *                    1) getNextCode(code,2,false); code 为同级最大节点 如A10 生成A11
		 * @param: code 初始code
		 * @param: numPlaceHolder 数字占位位数【父子之间的占位位数需相同，否则生成错误】
		 * @return: java.lang.String
		 */
	2.generateTree
	    /**
		 * @author: wc
		 * @description: 根据所传入的参数生成树型结构 使用方式：
		 *                  一、所需生成树的vo extends TreeNodeVo (key、value、title、pid均需按实际情况设置)
		 *                  二、传入树根节点和所需拼到树根下的节点list
		 * @param: rootList 树根节点
		 * @param: childrenList 树孩子节点
		 * @param: isExpanded 是否展开
		 * @return: java.util.List<TreeNodeVo> 返回树结构列表
		 */


### 功能介绍
### 1.1 树生成
#### 树生成包括树结构生成节点添加方法 关键在于生成TREE_CODE、TREE_LEVEL、SORT，三个字段配合使用可增加查询子节点的效率
#####  节点字段说明见下
+ 生成树的TREE_CODE,生成节点的TREE_LEVEL表示此根级别,SORT 表示生成树的本级别内的排序
+                 TREE_CODE          TREE_LEVEL   SORT
+	               A01					  1		    1
+	                --A0101			      2         1
+                   --A0102			      2		    2
+ 	               A02					  1		    2
+ 	                --A0201			      2         1
+ 	                --A0202			      2         2
+ 	                 --A020201			  3         1
+ 	               ...					  ...      ...
	调用generateCode只要传参正确便可生成此结构的内容


### 版本
+ V1.0       更新时间:2021-06-20

### 版本库：
+ Git获取：https://github.com/wangcongisqian/treeHelps.git

### 个人博客：
	博客园：https://www.cnblogs.com/ladyM/
	
	
	CSDN:https://blog.csdn.net/u013247068?spm=1001.2100.3001.5343
 <br>

免费使用


### 
1.0版本属于初级版本，只做了基础功能，由于本人能力有限，在实现中不合理的地方欢迎大神批评指正，同时在方法使用过程中有任何问题原因交流学习

<br>
本人QQ：913527675
