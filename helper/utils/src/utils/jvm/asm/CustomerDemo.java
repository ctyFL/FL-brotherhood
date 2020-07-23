package utils.jvm.asm;

/**
 * 需要被改造的类，添加功能（计算某个方法的运行时间——使用ASM改造该类的字节码文件class）
 * @author ctyFL
 * @date 2020年7月19日
 * @version 1.0
 */
public class CustomerDemo {
	
	/**
	 * 假设要监控doSameThing()这个方法的运行时间
	 */
	public void doSameThing() {
		
		/**
		 * 1.在使用ASM之前：我们用 “方法结束后的当前时间毫秒数” - “方法开始时的当前时间毫秒数” = “该方法运行所消耗的时间”
		 *   然后我们用 “ASMifier工具” 生成一份ASM以visitor的方式调用虚拟机操作码的代码文件
		 * 2.我们把埋点（即方法头尾使用System.currentTimeMillis()算出的运行时间的代码）注释或删掉，再使用“ASMifier工具” 生成一份代码文件
		 * 3.比较两方代码，不同的地方（减少了的代码块）就是ASM以visitor的方式调用操作码计算方法运行时间（埋点部分）的代码
		 *   这部分代码就是我们要增加的功能（往class字节码文件增加的部分）
		 * 4.使用 “ClassWriter” 修改后（使用ClassGenerator类修改class文件），我就可以把埋点去掉，此时，这个类的每个方法的头和尾都有了计算运行时间的功能
		 * 5.测试报错“java.lang.VerifyError: Bad local variable type”，因为我们添加的功能里有局部变量，而ASM修改class文件会对局部变量的储存位置产生影响，
		 *   若原方法中也存储了局部变量，就容易产生冲突
		 * 6.所以我们采用另一种方法，将添加的功能都封装起来，封装到类、方法中去
		 *   MyTimeLog类中，把当前系统时间用静态资源储存，计算时间的功能封装到静态方法中
		 *   (当前系统时间用静态属性，所有类的实例都共享这一属性，全局唯一，一处变处处变，所以线程不安全，但是这里先不去考虑并发)
		 * 7.此时我们就可以使用MyTimeLog.Start()、MyTimeLog.end()作为埋点（这时候埋点中就没有局部变量了）
		 * 8.然后再次使用“ASMifier工具”生成两份代码获得ASM以visitor的方式调用操作码计算方法运行时间（埋点部分）的代码
		 * 9.新建MyClassAdapterV2类使用新的代码去修改class
		 */
		
		try {
//			long startTime = System.currentTimeMillis();
			MyTimeLog.Start();
			
			System.out.println("doSameThing() method start ————>");
			System.out.println("doing someThing ...");
			Thread.sleep(700);
			System.out.println("doSameThing() method end <————");
			
			MyTimeLog.end();
//			long endTime = System.currentTimeMillis();
//			long tookTime = endTime - startTime;
//			System.out.println("================= 耗时  " + tookTime + " ms =================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
