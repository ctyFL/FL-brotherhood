ASM开发实战————实现统计方法的运行时间（性能调优的时候想要知道一个方法的运行时间是多少，然而提前埋点是不可能的，实现方式类似AOP）

		ASM给我们提供了 ASMifier 工具来帮助开发，可使用这个工具生成ASM结构来对比（将原先的文件和修改后的文件对比）

		使用 ASMifier 需要的jar包：
					asm-x.x.x.jar
					asm-util-x.x.x.jar


	开发示例
	步骤：
		1.创建一个需要被修改的类，统计这个类中的某个方法的运行时间：
			FL-brotherhood下 helper/utils/src/utils/jvm/asm/CustomerDemo.java
			CustomerDemo中有两个方法，doSameThing()————这个方法中使用埋点的方法，在方法的头和尾使用System.currentTimeMillis()获取当前时间毫秒数，最后的差值就是该方法运行消耗的时间

			doWithASM()————这个方法中就只有原先的业务代码

		2.使用 “ASMifier工具” 生成一份这个类的代码：
			首先在该类的编译后的class文件的所在的位置下cmd，也就是在（E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\bin\utils\jvm\asm）：
				打开cmd————先cd到E盘（输入 e: 然后回车）————然后cd到class文件所在位置（输入 cd gitHub/gitHubProject/FL-brotherhood/helper/utils/bin/utils/jvm/asm）

			使用 “java -cp” 命令引入所依赖的jar包，输入：java -cp E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-7.0.jar;E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-util-7.1.jar

			然后后面跟上 “ASMifier工具” 的全面， 和要被处理的class文件：		
			java -cp E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-7.0.jar;E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-util-7.1.jar org.objectweb.asm.util.ASMifier CustomerDemo.class

			回车后把生成的代码先复制出来：useASMbefore.txt

		3.在doWithASM()方法中，像doSameThing()中一样，加上埋点，然后再使用 “ASMifier工具” 生成一次，把新生成的代码复制出来：useASMafter.txt

		4.比较两份代码的不同之处，也就是新增的地方，把新增的代码块复制出来：dif.txt
			这部分代码就是我们所要新增的功能，这几行代码就是对应“System.currentTimeMillis()”的那几行代码

		原理：“ASMifier工具” 生成的代码，就是ASM以visitor的方式去操作字节码的指令，我们就是利用它来帮助我们生成代码，就不需要我们自己去编写了，我们先写好想要改造后的代码，给ASMifier生成一遍，再和改造前比较，就能获得我们想要的代码

		
		5.实现我们自己的ClassVisitor（也就是 “ClassAdapter” 负责把 ClassReader 读进来的类进行改造，实现新的功能）（FL-brotherhood下 helper/utils/src/utils/jvm/asm/MyClassAdapter.java）
			我们需要实现 ClassVisitor 接口来实现 Class 文件的生成和转换
			（MyClassAdapter extends ClassVisitor）

			右击——source——Override/implement Methods查看覆盖的方法：
				里面有很多如：
							visit——扫描类时第一个调用的方法，主要用于类声明使用visit( 类版本 , 修饰符 , 类名 , 泛型信息 , 继承的父类 , 实现的接口)
							visitAnnotation——访问注解，扫描器扫描到类注解声明时进行调用
							visitAtrribute——访问属性
							visitField——访问字段，扫描器扫描到类中字段时进行调用
							visitMethod——访问方法，扫描到类的方法时进行调用
							visitEnd——访问结束，扫描器完成类扫描时才会调用
							...

			这里我是对方法添加功能，所以只要重写 visit、visitMethod 这两个方法


		6.重写一个MethodVisitor类，来实现自己的扫描到要修改的方法时的逻辑（增加统计时间功能）：
			创建内部类 MyMethodVisitor 继承 MethodVisitor
			可以重载的方法有：
		 				visitAnnotationDefualt——访问注解接口方法的默认值；
			 			visitAnnotaion——访问方法的一个注解；
		 				visitTypeAnnotation——访问方法签名上的一个类型的注解；
		 				visitAnnotableParameterCount——访问注解参数数量，就是访问方法参数有注解参数个数；
		 				visitAttribute——访问方法属性；
			 			visitCode——开始访问方法代码，此处可以添加方法运行前拦截器；
		 				visitIntInsn——访问数值类型指令；
		 				visitVarInsn——访问本地变量类型指令；
		 				visitTypeInsn——访问类型指令，类型指令会把类的内部名称当成参数Type；
		 				visitFieldInsn——域操作指令，用来加载或者存储对象的Field；
		 				visitMethodInsn——访问方法操作指令；
		 				等...

			这里我们的统计时间的功能，是在方法开始前记录一次，方法结束时记录一次，所以重写 visitCode、visitInsn 这两个方法，然后将之前复制出来的代码分别复制到方法里

		7.创建class生成器类（将改造后的class输出）
			创建ClassGenerator（FL-brotherhood下 helper/utils/src/utils/jvm/asm/ClassGenerator.java）
			
			里面实现功能：1.使用 ClassReader 解析 class 字节码
						 2.使用我们自己实现的 ClassAdapter 对字节码改造（增加统计时间）
						 3.使用 ClassWriter 将改造后的 class 输出