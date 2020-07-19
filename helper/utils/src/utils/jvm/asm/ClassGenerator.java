package utils.jvm.asm;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * 字节码生成器:
 * 1.使用 ClassReader 解析 class 字节码
 * 2.使用我们自己实现的 ClassAdapter 对字节码改造（增加统计时间）
 * 3.使用 ClassWriter 将改造后的 class 输出
 * @author ctyFL
 * @date 2020年7月19日
 * @version 1.0
 */
public class ClassGenerator {
	
	public static void main(String[] args) {
		try {
			ClassReader reader = new ClassReader("utils/jvm/asm/CustomerDemo");//读取的目标类全名
			
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			
			ClassVisitor visitor = new MyClassAdapter(writer);
			
			reader.accept(visitor, ClassReader.SKIP_DEBUG);//开始读，跳过调试
			
			byte[] data = writer.toByteArray();//获得返回的数组
			
			//输出成文件到CustomerDemo.class文件所在位置
			File file = new File("bin/utils/jvm/asm/CustomerDemo.class");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
			
			System.out.println("ClassGenerator CustomerDemo class success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
