数据库——SQL语言：


		SQL是结构化查询语言（Structured Query Language）的缩写，其功能包括四个部分：
														数据查询、数据操纵、数据定义、数据控制

		SQL语言简洁、方便实用、功能齐全，已成为目前应用最广泛的关系数据库语言

		SQL既是自含式语言（联机交互）、又是嵌入式语言（宿主语言）




		1.基本概念：
				基本表（BASE TABLE）：是独立存在的表，不是由其他的表导出的表，一个关系对应一个基本
									 表，一个或多个基本表对应一个存储文件
									（是实实在在存在物理文件中的）

				视图（VIEW）：是一个虚拟的表，是从一个或几个基本表导出的表，数据库中只存放视图的定
							 义而不存放视图对应的数据，这些数据仍存放在导出的视图的基本表中，当基
							 本表中的数据发送变化时，从视图中查询出来的数据也随之改变


		2.数据定义：
				SQL语言使用 “数据定义语言（DDL）” 实现其数据定义功能，可对数据库用户、基本表、视
				图、索引进行定义和撤销

				如以下 “数据定义语言（DDL）”：
										创建模式：create schema
										删除模式：drop schema

										创建表：create table
										删除表：drop table
										修改表：alter table

										创建索引：create index
										删除索引：drop index



				(2)基本语法：

						1.定义基本表：
							create table<表明>(<列名>[，{<列名>|<表约束>}])

							例如建立一张学生表：
								create table STUDENT 
								(SNO CHAR(8) NOT NULL UNIQUE，
								 SN VARCHAR(20)，
								 AGE INT，
								 SEX CHAR(2) DEFAULT'男'，
								 DEPT VARCHAR(20))；


						2.修改基本表：
							alter table<表名>
							[add<新列名><数据类型>[完整性约束]]
							[alter column <列名><数据类型>]
							[drop <column 列名>|<完整性约束名>]；

							add子句：增加新列和新的完整性约束条件
							alter column子句：用于修改列
							drop子句：删除指定列和完整性约束条件

							例：
								alter table STUDENT add CLASS_NO CHAR(6)


						3.删除表：
							drop table <表名>

							例：drop table STUDENT

							基本表删除后，表中的数据和表上建立的索引都会被删除，而建立在该表上的视
							图不会删除，系统将保留其定义，但已无法使用


						4.定义视图：
							create view <视图名>[(<列名>[，<列名>]...)]
							as <子查询>
							with check option

							例：
								create view IS_STUDENT
								as
								select SNO，SNAME，SAGE from STUDENT where SDEPT='IS'
								with check option


						5.删除视图：
							drop view <视图名>

							例：drop view IS_STUDENT



				(3)查询语法：
						select语句一般格式为：
						select [all|Distinct] <列名> [{，<列名>}]
						from <表名或视图名> [{，<表名或视图名>}]
						[where <检索条件>]
						[group by <列名1>[having <条件表达式>]]
						[order by <列名2>[ASX|DESC]]；

						select语句的执行过程是：
							(1)根据where子句的检索条件，从from子句指定的基本表或视图中选取满足条
							   件的元组，再按照select子句中指定的列，投影得到结果表

							(2)若有group子句，则将查询结果按照<列名1>相同的值进行分组

							(3)若group子句后有having短语，则只输出满足having条件的元组

							(4)若有order子句，查询结果还要按照<列名2>的值进行排序


						1.单表查询

						例：
							查询姓名中第二个汉字是 “力” 的教师号和姓名
							select TNO，TNAME from Teacher where TNAME like '_ _力%'
							（_ _ 一个汉字占两个字符）


						2.连接查询：
								表的连接方法有两种：
									a.表之间满足一定的条件的行进行连接，此时from子句中指明进行连      
									  接的表名，where子句指明连接的列名及其连接的条件

									  例：
										select Teacher.TNO，Teacher.TNAME，TC.CNO from 
										Teacher，TC where (Teacher.TNO=TC.TNO) and 
										(Teacher.TNAME='刘伟')

									b.利用关键字JOIN进行连接							

									  例：
										select Teacher.TNO，Teacher.TNAME，TC.CNO
										from Teacher inner join TC on Teacher.TNO=TC.TNO
										and Teacher.TNAME='刘伟'


						3.嵌套查询：
								例：
									查询与刘成在同一系学习的学生：
									select SNO，SNAME from Student where SDEPT IN 
										(select SDEPT from Student where SNAME='刘成')