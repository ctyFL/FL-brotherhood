package test;

import utils.jvm.asm.CustomerDemo;

/**
 * 使用ASM开发功能（计算某个方法运行消耗的时间）测试类
 * @author ctyFL
 * @date 2020年7月19日
 * @version 1.0
 */
public class JvmAsmTest {

	public static void main(String[] args) {
		CustomerDemo cus = new CustomerDemo();
		cus.doSameThing();
	}
	
}
