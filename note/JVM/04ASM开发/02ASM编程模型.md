ASM编程模型：

		Core API：提供了基于事件形式的编程模型
			该模型不需要一次性将整个类的结构读取到内存中，因此这种方式更快，需要更少的内存，但这种编程方式难度较大（类似SAX解析xml（读一个节点，操作一个节点））

		Tree API：提供了基于树形的编程模型
			该模型需要一次性将一个类的完整结构全部读取到内存中，所以这种方法需要更多的内存，这种编程方式较简单（类似DOM解析）


		Core API：
			1.该API中操作字节码的功能基于 ClassVisitor 整个接口（开发时需要我们编写一个它的实现类）。这个接口中的每一个方法对应了 Class 文件中的每一项（class文件中有什么，这个接口中就有一个对应的方法去增加或改变它的功能等）

			2.ASM提供了三个基于 ClassVisitor 接口的类来实现 Class 文件的生成和转换
				（1）ClassReader：ClassReader 会解析一个类的 class 字节码
				（2）ClassAdapter：ClassAdapter 是 ClassVisitor 的实现类，负责把 ClassReader 读进来的类进行改造，实现新的功能
				（3）ClassWriter：ClassWriter 也是 ClassVisitor 的实现类，用来输出变化后的字节码