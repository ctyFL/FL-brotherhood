package utils.jvm.asm;

public class MyTimeLog {

	private static long startTime = 0;
	
	public static void Start() {
		startTime = System.currentTimeMillis();
	}
	
	public static void end() {
		long endTime = System.currentTimeMillis();
		long tookTime = endTime - startTime;
		System.out.println("================= 耗时  " + tookTime + " ms =================");
	}
	
}
