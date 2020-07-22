package utils.jvm.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
/**
 * ClassAdapter 是 ClassVisitor 的实现类，负责把 ClassReader 读进来的类进行改造，实现新的功能
 * @author ctyFL
 * @date 2020年7月19日
 * @version 1.0
 */
public class MyClassAdapter extends ClassVisitor {

	public MyClassAdapter(ClassVisitor classVisitor) {
		super(Opcodes.ASM7, classVisitor);//Opcodes.ASM7版本
	}
	
	/**
	 * 可以重载的方法有：
	 * visit——扫描类时第一个调用的方法，主要用于类声明使用visit( 类版本 , 修饰符 , 类名 , 泛型信息 , 继承的父类 , 实现的接口)
	 * visitAnnotation——访问注解，扫描器扫描到类注解声明时进行调用
	 * visitAtrribute——访问属性
	 * visitField——访问字段，扫描器扫描到类中字段时进行调用
	 * visitMethod——访问方法，扫描到类的方法时进行调用
	 * visitEnd——访问结束，扫描器完成类扫描时才会调用
	 * 等...
	 */
	
	/**
	 * 该方法是当扫描类时第一个调用的方法，主要用于类声明使用。
	 * 下面是对方法中各个参数的示意：visit( 类版本 , 修饰符 , 类名 , 泛型信息 , 继承的父类 , 实现的接口)
	 */
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// TODO Auto-generated method stub
		cv.visit(version, access, name, signature, superName, interfaces);
	}

	/**
	 * 该方法是当扫描器扫描到类的方法时进行调用。
	 * 下面是对方法中各个参数的示意：visitMethod(修饰符 , 方法名 , 方法签名 , 泛型信息 , 抛出的异常)
	 */
	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
			String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);

		if(!"<init>".equals(name) && mv != null) {//除去init方法
			//符合这个条件下的方法，增加统计方法运行时间的功能
			mv = new MyMethodVisitor(mv);//替换为我们自己实现的 “MethodVisitor”（实现功能统计运行时间）
		}
		
		return mv;
	}

	//重写一个MethodVisitor
	class MyMethodVisitor extends MethodVisitor {

		public MyMethodVisitor(MethodVisitor methodVisitor) {
			super(Opcodes.ASM7, methodVisitor);//Opcodes.ASM7 版本
		}

		/**
		 * 可以重载的方法有：
		 * visitAnnotationDefualt——访问注解接口方法的默认值；
		 * visitAnnotaion——访问方法的一个注解；
		 * visitTypeAnnotation——访问方法签名上的一个类型的注解；
		 * visitAnnotableParameterCount——访问注解参数数量，就是访问方法参数有注解参数个数；
		 * visitAttribute——访问方法属性；
		 * visitCode——开始访问方法代码，此处可以添加方法运行前拦截器；
		 * visitIntInsn——访问数值类型指令；
		 * visitVarInsn——访问本地变量类型指令；
		 * visitTypeInsn——访问类型指令，类型指令会把类的内部名称当成参数Type；
		 * visitFieldInsn——域操作指令，用来加载或者存储对象的Field；
		 * visitMethodInsn——访问方法操作指令；
		 * 等...
		 */
		
		//visitCode:开始访问方法代码，此处可以添加方法运行前拦截器；
		//重写visitCode()方法开始访问方法代码
		@Override
		public void visitCode() {
			mv.visitCode();
			//将之前复制出来的代码块复制过来
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false);
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.AALOAD);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getClassName", "()Ljava/lang/String;", false);
			mv.visitVarInsn(Opcodes.ASTORE, 1);
			Label label3 = new Label();
			mv.visitLabel(label3);
			mv.visitLineNumber(27, label3);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false);
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.AALOAD);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getMethodName", "()Ljava/lang/String;", false);
			mv.visitVarInsn(Opcodes.ASTORE, 2);
			Label label4 = new Label();
			mv.visitLabel(label4);
			mv.visitLineNumber(28, label4);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
			mv.visitVarInsn(Opcodes.LSTORE, 3);
			Label label5 = new Label();
			mv.visitLabel(label5);
			mv.visitLineNumber(30, label5);
		}
		
		@Override
		public void visitInsn(int opcode) {
			
			//将之前复制出来的代码块复制过来
			if((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) 
					|| opcode == Opcodes.ATHROW) {//到代码结尾return之前，还有一种方法结束情况是抛异常之前（操作码必须大于等于 ireturn （还没有到return））
				Label label9 = new Label();
				mv.visitLabel(label9);
				mv.visitLineNumber(35, label9);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
				mv.visitVarInsn(Opcodes.LSTORE, 5);
				Label label10 = new Label();
				mv.visitLabel(label10);
				mv.visitLineNumber(36, label10);
				mv.visitVarInsn(Opcodes.LLOAD, 5);
				mv.visitVarInsn(Opcodes.LLOAD, 3);
				mv.visitInsn(Opcodes.LSUB);
				mv.visitVarInsn(Opcodes.LSTORE, 7);
				Label label11 = new Label();
				mv.visitLabel(label11);
				mv.visitLineNumber(37, label11);
				mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
				mv.visitInsn(Opcodes.DUP);
				mv.visitLdcInsn("\u7c7b\u540d\uff1a");
				mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
				mv.visitVarInsn(Opcodes.ALOAD, 1);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitLdcInsn(" \u65b9\u6cd5\uff1a");
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitVarInsn(Opcodes.ALOAD, 2);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitLdcInsn("() \u8017\u65f6  ");
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitVarInsn(Opcodes.LLOAD, 7);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
				mv.visitLdcInsn(" ms");
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
				//以下可以删除
//				mv.visitLabel(label1);
//				mv.visitLineNumber(38, label1);
			}
			
			//到这一行时，代码真正到结尾结束了，所以要在这之前把功能加上
			mv.visitInsn(opcode);
		}
		
	}
	
}
