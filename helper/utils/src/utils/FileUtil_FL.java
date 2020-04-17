package utils;

import java.io.File;

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
	
}