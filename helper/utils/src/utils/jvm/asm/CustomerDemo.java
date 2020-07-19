package utils.jvm.asm;

/**
 * 需要被改造的类，添加功能（计算某个方法的运行时间——使用ASM改造该类的字节码文件class）
 * @author ctyFL
 * @date 2020年7月19日
 * @version 1.0
 */
public class CustomerDemo {
	
	/**
	 * 1.假设要监控doSameThing()这个方法的运行时间
	 */
	public void doSameThing() {
		try {
			//在使用ASM之前我们用 方法结束后的当前时间毫秒数 -方法开始时的当前时间毫秒数 来算出该方法运行所消耗的时间
			long startTime = System.currentTimeMillis();
			
			System.out.println("doSameThing() method start ————>");
			System.out.println("doing someThing ...");
			Thread.sleep(700);
			System.out.println("doSameThing() method end <————");
			
			long endTime = System.currentTimeMillis();
			long tookTime = endTime - startTime;
			System.out.println("method: doSameThing() took " + tookTime + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.现在我们使用ASM开发，直接修改该类的class文件，给 doWithASM 方法加上计算运行时间的方法
	 */
	public void doWithASM() {
		try {
			System.out.println("doWithASM() method start ————>");
			System.out.println("doing someThing ...");
			Thread.sleep(700);
			System.out.println("doWithASM() method end <————");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
