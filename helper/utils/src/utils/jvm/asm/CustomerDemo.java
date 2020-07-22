package utils.jvm.asm;

/**
 * 需要被改造的类，添加功能（计算某个方法的运行时间，并连同当前类名和方法名打印到控制台——使用ASM改造该类的字节码文件class）
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
		 * 2.我们把埋点（即方法头尾获得类名和方法名和用System.currentTimeMillis()算出的运行时间的代码）注释或删掉，再使用“ASMifier工具” 生成一份代码文件
		 * 3.比较两方代码，不同的地方（减少了的代码块）就是ASM以visitor的方式调用操作码获得类名、方法名和计算方法运行时间（埋点部分）的代码
		 *   这部分代码就是我们要增加的功能（往class字节码文件增加的部分）
		 * 4.使用 “ClassWriter” 修改后（使用ClassGenerator类修改class文件），我就可以把埋点去掉，此时，这个类的每个方法的头和尾都有了获得当前类名和方法名和计算运行时间的功能
		 */
		
		try {
//			String className = Thread.currentThread() .getStackTrace()[1].getClassName();
//			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//			long startTime = System.currentTimeMillis();
			
			System.out.println("doSameThing() method start ————>");
			System.out.println("doing someThing ...");
			Thread.sleep(700);
			System.out.println("doSameThing() method end <————");
			
//			long endTime = System.currentTimeMillis();
//			long tookTime = endTime - startTime;
//			System.out.println("类名：" + className + " 方法：" + methodName + "() 耗时  " + tookTime + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
