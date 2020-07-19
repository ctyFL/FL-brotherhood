阅读Class文件，了解Class文件格式：
————目的：1.了解虚拟机是如何运行我们的程序的，指令执行的顺序 2.了解虚拟机做了哪些优化 3.了解了Class文件格式之后，我们就可以采用直接操作字节码的方式来完成一些动态的功能（这些功能用普通的Java程序开发方式不太好实现）

		Class文件是JVM的输入，Java虚拟机规范中定义了Class文件的结构，Class文件是JVM实现平台无关、技术无关的基础
				1.Class文件是一组以8字节为单位的 “字节流”，各个数据项目按顺序紧凑排列
				2.对于占用空间大于8字节的数据项，按照高位在前的方式分割成多个8字节进行储存
				3.Class文件格式里面只有两种数据类型：二无符号数、表
					（1）无符号数：基本数据类型，以U1（1个字节）、U2（2个字节）、U4、U8来代表几个字节的无符号数
					（2）表：由多个无符号数和其他表构成的复核数据类型，通常以 “_info” 结尾


		ClassFile结构：
			每个Class字节码文件都对应如下所示的ClassFile结构（凡是符合虚拟机规范的这个类统统都有这样的一个结构）：
				u4（表示在Class十六进制文件中占4位）

				ClassFile {
					u4		magic;（魔数：唯一作用是确定这个文件是否是一个能被虚拟机所接受的文件，魔数值固定为： 0xCAFEBABE，不会改变）
					u2		minor_version;（副版本号）
					u2		major_version;（主版本号）
					u2		constant_pool_count;（常量池的数量）
					cp_info constant_poll[constant_pool_count-1];（常量池的信息）
					u2		access_flags;（访问标记，public、final、abstract等）
					u2		this_class;（自己这个类定义的名称）
					u2		super_class;（副类）
					u2		interfaces_count;（接口的数量）
					u2		interfaces[interfaces_count];
					u2		fields_count;（属性的数量）
					field_info fields[fields_count];（字段属性的信息）
					u2		methods_count；(方法的数量)
					method_info methods[methods_count];（方法定义的信息）
					u2		attributes_count;（辅助信息数量，为了给虚拟机处理这个类提供的一些信息）
					attribute_info attributes[attributes_count];（罗列这些attribute）
				}

使用javap工具生成非正式的 “虚拟机汇编语言” ，格式如下：
	<index><opcode>[<operand1>[<operand2>...]][<comment>]
	<index>是指令操作码在数组中的下标，该数组以字节形式来储存当前方法的Java虚拟机代码；也可以是相对于方法起始的字节偏移量
	<opcode>是指令的助记码，<operand>是操作数、<comment>是行尾的注释

		类的属性和定义：
			constant_pool_count：是从1开始的
			
			不同的常量类型，是用tag来区分的，它后面对应的 info 结构是不一样的

			“L” 表示对象，“[” 表示数组、“V” 表示void


		方法和方法调用：
			slot是虚拟机为局部变量分配内存所使用的最小单位

			args_size：参数个数，为1的话，因实例方法默认会传入this，locals也会预留一个slot来存放

			字符串加的操作如：String name = "cty";  "where name=" + name;
			 在字符串后面直接 “+” 的时候，性能不会很好，早期jdk会提醒不要用字符串加的操作，但新版的jdk中会自动帮你优化，虚拟机会new 一个 StringBuilder 或 StringBuffer 去进行 “append” 的操作，最后会 toString()


总结：重点是理解并能阅读字节码，以及用 “虚拟机汇编语言” 表示的Java类，————目的：1.了解虚拟机是如何运行我们的程序的，指令执行的顺序 2.了解虚拟机做了哪些优化 3.了解了Class文件格式之后，我们就可以采用直接操作字节码的方式来完成一些动态的功能（这些功能用普通的Java程序开发方式不太好实现）