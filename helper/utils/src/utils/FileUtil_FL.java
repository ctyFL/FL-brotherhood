package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * 文件工具类
 * @author ctyFL
 * @date 2020年4月15日
 * @version 1.0
 * 
 */
public class FileUtil_FL {

	/**
	 * 上传本地文件至服务器
	 * @param filePath
	 * @param servletName
	 */
	public static void uploadToServer(String filePath, String servletName) {
		if(isExists(filePath)) {
			HttpUtil_FL.postUploadFileThread(filePath, servletName);
		}
	}

	/**
	 * 判断文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean isExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	/**
	 * 目标如果是一个文件，则删除
	 * @param filePath
	 * @return
	 */
	public static boolean deteleFile(String filePath) {
		File file = new File(filePath);
		if(file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}
	
	/**
	 * 在目标路径创建一个新的txt文件，并写入内容
	 * @param filePath
	 * @param textContent
	 */
	public static void createNewTxtFile(String rootPath, String fileName, String textContent) {
		String format = "txt";
		FileOutputStream fos = null;
		PrintWriter pw = null;
		if(createNewFile(rootPath, fileName, format)) {
			File file = new File(rootPath + fileName + "." + format);
			try {
				fos = new FileOutputStream(file);
				StringBuffer buf = new StringBuffer();
				buf.append(textContent);
				pw = new PrintWriter(fos);
				pw.write(buf.toString().toCharArray());
				pw.flush();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(pw != null) {
					pw.close();
				}
				if(fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 在目标路径创建一个指定格式的文件
	 * @param rootPath
	 * @param fileName
	 * @param format
	 */
	public static boolean createNewFile(String rootPath, String fileName, String format) {
		boolean flag = false;
		String filePath = rootPath + fileName + "." + format;
		File file = new File(filePath);
		try {
			if(!file.exists()) {
				file.createNewFile();
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
