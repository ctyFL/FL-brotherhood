ASM开发实战————实现统计方法的运行时间（性能调优的时候想要知道一个方法的运行时间是多少，然而提前埋点是不可能的，不可能在每个方法的开头结尾都打个桩，实现方式类似AOP）

		ASM给我们提供了 ASMifier 工具来帮助开发，可使用这个工具生成ASM结构来对比（将原先的文件和修改后的文件对比）

		使用 ASMifier 需要的jar包：
					asm-x.x.x.jar
					asm-util-x.x.x.jar


	开发示例
	步骤：
		1.创建一个需要被修改的类，统计这个类中的某个方法的运行时间：
			FL-brotherhood下 helper/utils/src/utils/jvm/asm/CustomerDemo.java
			CustomerDemo中创建一个方法，doSameThing()————这个方法中先使用埋点的方法实现这个功能：
			在方法的头和尾使用System.currentTimeMillis()获取当前时间毫秒数，最后的差值就是该方法运行消耗的时间

		2.使用 “ASMifier工具” 生成一份这个类使用埋点的方法实现了这个功能的代码：
			首先在该类的编译后的class文件的所在的位置下cmd，也就是在（E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\bin\utils\jvm\asm）：
				打开cmd————先cd到E盘（输入 e: 然后回车）————然后cd到class文件所在位置（输入 cd gitHub/gitHubProject/FL-brotherhood/helper/utils/bin/utils/jvm/asm）

			使用 “java -cp” 命令引入所依赖的jar包，输入：java -cp E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-7.0.jar;E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-util-7.1.jar

			然后后面跟上 “ASMifier工具” 的全面， 和要被处理的class文件：		
			java -cp E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-7.0.jar;E:\gitHub\gitHubProject\FL-brotherhood\helper\utils\jar\asm\asm-util-7.1.jar org.objectweb.asm.util.ASMifier CustomerDemo.class

			回车后把生成的代码先复制出来：addFunctionAfter.txt

		3.在doSameThing()方法中，去掉埋点，然后再使用 “ASMifier工具” 生成一次，把新生成的代码复制出来：addFunctionBefore.txt

		4.比较两份代码的不同之处，也就是减少了的部分，把新增的代码块复制出来：dif.txt
			这部分减少的代码块就是我们所要新增的功能，这几行代码就是对应“System.currentTimeMillis()”的那几行代码

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

			注意：visitInsn方法执行时表示真正到了方法的结尾，所以我们要把新增的功能的结尾部分的代码块加在这个visitInsn方法之前，同时也要在方法reuturn和出错抛异常之前（方法结束之前，return和抛异常都会结束当前方法）

		7.创建class生成器类（将改造后的class输出）
			创建ClassGenerator（FL-brotherhood下 helper/utils/src/utils/jvm/asm/ClassGenerator.java）
			
			里面实现功能：1.使用 ClassReader 解析 class 字节码
						 2.使用我们自己实现的 ClassAdapter 对字节码改造（增加统计时间）
						 3.使用 ClassWriter 将改造后的 class 输出

		8.创建测试类JvmAsmTest进行测试（FL-brotherhood下 helper/utils/src/test/JvmAsmTest.java）
			调用CustomerDemo的doSameThing()方法，此时会报错 “java.lang.VerifyError: Bad local variable type”，因为ASM修改class文件，会对方法中的本地变量造成影响，因为代码中原先标志着“位置1”可能存储着一个方法中原本的变量，修改后，若我们增加的功能中也去储存了局部变量，“位置1”变成了存了其他类型的变量，就冲突了，如示例中被我们强行修改成了储存获得当前时间毫秒数的long型，所以出错了，此时若将doSameThing()方法中的局部变量去掉（示例中，去掉埋点后为它原本的业务代码中，局部变量就是try/catch块中Exception e的“e”这个局部变量）此时测试就不会报错，然而这不合理，因为你不能去修改它原有的业务代码，只能是我们追加功能的时候去迁就原来的业务功能代码

		9.解决————由于我们增加的功能中需要存储局部变量，会有和原有的业务代码产生冲突的可能（若原来的业务代码中也有局部变量），所以我们将我们增加的功能封装起来，封装到类、方法中去
			创建MyTimeLog类":
			将我们增加的功能都封装到这个类中（FL-brotherhood下 helper/utils/src/utils/jvm/asm/MyTimeLog.java）
			MyTimeLog类中，我们将获得的当前系统时间用static静态资源的方式储存，将计算时间的功能封装到静态方法中
			（当前系统时间的变量用静态属性，所有类的实例都共享这一属性，全局唯一，一处变，处处变，所以线程不安全，但是这里先不去考虑并发）

		10.此时我们就可以使用MyTimeLog.Start()、MyTimeLog.end()作为埋点（这时候埋点中就没有局部变量了）
		然后再次使用“ASMifier工具”生成两份代码（addFunctionBeforeV2.txt addFunctionAfterV2.txt difV2.txt）获得ASM以visitor的方式调用操作码计算方法运行时间（埋点部分）的代码
		新建MyClassAdapterV2类（FL-brotherhood下 helper/utils/src/utils/jvm/asm/MyClassAdapterV2.java）使用新的代码去修改class，最后测试