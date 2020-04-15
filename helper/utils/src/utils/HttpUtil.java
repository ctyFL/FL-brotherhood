package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 
 * Http工具类
 * @author ctyFL
 * @date 2020年4月15日
 * @version 1.0
 * 
 */
public class HttpUtil {
	
	private static final String nextLine = "\r\n";
	private static final String boundaryPrefix = "--";
	private static final String BOUNDARY = "=========7d4a6d158c9";// 数据分割线
	
	/**
	 * 创建一个post请求线程
	 * @param paramsMap
	 * @param url
	 */
	public static void postToServerThread(Map<String, String> paramsMap, String url) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
			
			}
		};
		thread.start();
	}
	
	/**
	 * 创建一个通过post上传文件的线程
	 * @param filePath
	 * @param url
	 */
	public static void postUploadFileThread(String filePath, String url) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
				uploadPost(filePath, url);
			}
		};
		thread.start();
	}
	
	/**
	 * 通过post上传文件
	 * @param filePath
	 * @param url
	 */
	public static void uploadPost(String filePath, String url) {
		DataOutputStream out = null;
		DataInputStream in = null;
		String result = "";
		try {
			URL postUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection httpConn = (HttpURLConnection) postUrl.openConnection();
			// 发送POST请求必须设置如下两行
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			// 设置为POST请求
			httpConn.setRequestMethod("POST");
			// 设置请求头参数
			httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			httpConn.setRequestProperty("Charsert", "UTF-8");
			httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			httpConn.connect();
			
			out = new DataOutputStream(httpConn.getOutputStream());
			// 添加参数file
			File file = new File(filePath);
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(nextLine);
			sb.append("Content-Disposition: form-data; name=\"file\";filename=\"" + file.getName() + "\"");
			sb.append(nextLine);
			sb.append("Content-Type:application/octet-stream");
			sb.append(nextLine);
			sb.append(nextLine);
			out.write(sb.toString().getBytes());
			
			in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write(nextLine.getBytes());
			// 定义最后数据分割线
			byte[] end_data = (nextLine + boundaryPrefix + BOUNDARY + boundaryPrefix + nextLine).getBytes();
			out.write(end_data);
			
			// flush输出流的缓冲
			out.flush();
            // 定义BufferedReader输入流来读取URL的响应  
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));  
			String line = null;  
			while((line = reader.readLine()) != null) {  
              result += line;  
			}  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) {
					out.close();
				}
				if(in != null) {
					in.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("result：" + result);
	}

//	public static void httpPost(Map<String, String> paramsMap, String url) {
//		PrintWriter out = null;
//		BufferedReader in = null;
//		String response = "";
//		String status = "";
//		try {
//			URL postUrl = new URL(url);
//			// 打开和URL之间的连接
//			HttpURLConnection httpConn = (HttpURLConnection) postUrl.openConnection();
//			// 设置请求属性
//			httpConn.setRequestProperty("Content-Type", "application/json");
//            // 发送POST请求必须设置如下两行
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			// 获取URLConnection对象对应的输出流
//			out = new PrintWriter(httpConn.getOutputStream());
//			// 发送请求参数
//			//out.write(content);
//			// flush输出流的缓冲
//            out.flush();
//            httpConn.connect();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
//            String line = "";
//            while((line = in.readLine()) != null) {
//            	response += line;
//            }
//            status = new Integer(httpConn.getResponseCode()).toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(out != null) {
//				out.close();
//			}
//			if(in != null) {
//				in.close();
//			}
//		}
//		System.out.println("status：" + status + "||response：" + response);
//	}
	
}
