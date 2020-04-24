package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * 
 * Http工具类
 * @author ctyFL
 * @date 2020年4月15日
 * @version 1.0
 * 
 */
public class HttpUtil_FL {
	
	private static final String nextLine = "\r\n";
	private static final String boundaryPrefix = "--";
	private static final String BOUNDARY = "=========7d4a6d158c9";// 数据分割线
	private static final String GET = "GET";
	private static final String POST = "POST";
	
	/**
	 * 创建一个GET请求线程
	 * @param paramsMap
	 * @param url
	 */
	public static void HttpGetThread(Map<String, String> paramsMap, String url) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
				HttpGet(paramsMap, url);
			}
		};
		thread.start();
	}
	
	/**
	 * 创建一个POST请求线程
	 * @param paramsMap
	 * @param url
	 */
	public static void HttpPostThread(Map<String, String> paramsMap, String url) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
				HttpPost(paramsMap, url);
			}
		};
		thread.start();
	}
	
	/**
	 * 创建一个通过POST上传文件的线程
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
	 * 通过POST上传文件
	 * @param filePath
	 * @param url
	 */
	public static String uploadPost(String filePath, String url) {
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
			httpConn.setRequestMethod(POST);
			// 设置请求头参数
			httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			httpConn.setRequestProperty("Charsert", "UTF-8");
			httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			httpConn.connect();
			//返回状态码（调试时使用，提前调用getResponseCode，表实该请求已经结束了）
			//System.out.println("ResponseCode：" + httpConn.getResponseCode());
			
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
		return result;
	}
	
	/**
	 * HttpGet请求
	 * @param paramsMap
	 * @param url
	 */
	public static String HttpGet(Map<String, String> paramsMap, String url) {
		url = url + "?" + getParamString(paramsMap);
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL getUrl = new URL(url); 
			conn = (HttpURLConnection) getUrl.openConnection();
			setHttpUrlConnection(conn, GET);
			conn.connect();
			result = readResposeContent(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		System.out.println("result：" + result);
		return result;
	}
	
	/**
	 * HttpPost请求
	 * @param paramsMap
	 * @param url
	 */
	public static String HttpPost(Map<String, String> paramsMap, String url) {
		HttpURLConnection conn = null;
		PrintWriter writer = null;
		String result = "";
		try {
			URL postUrl = new URL(url);
			conn = (HttpURLConnection) postUrl.openConnection();
			String params = getParamString(paramsMap);
			setHttpUrlConnection(conn, POST);
	        conn.connect();
	        writer = new PrintWriter(conn.getOutputStream());
	        writer.print(params);
	        writer.flush();
	        result = readResposeContent(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		System.out.println("result：" + result);
		return result;
	}
	
	/**
	 * 设置Http连接属性
	 * @param conn
	 * @param requestMethod
	 */
	private static void setHttpUrlConnection(HttpURLConnection conn, String requestMethod) throws ProtocolException {
		if(conn != null) {
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("Accept-Language", "zh-CN");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
	        conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
	        
	        if(isPost(requestMethod)) {
	        	// 发送POST请求必须设置如下两行
	        	conn.setDoInput(true);
	        	conn.setDoOutput(true);
	        }
		}
	}
	
	/**
	 * 返回响应字节流转为的字符串
	 * @param in
	 */
	private static String readResposeContent(InputStream in) throws IOException {
		Reader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new InputStreamReader(in);
			char[] buffer = new char[1024];
			int head = 0;
			while((head = reader.read(buffer)) > 0) {
				builder.append(new String(buffer, 0, head));
			}
			return builder.toString();
		} finally {
			if(in != null) {
				in.close();
			}
			if(reader != null) {
				reader.close();
			}
		}
	}
	
	/**
	 * 解析参数map
	 * @param paramsMap
	 * @return
	 */
	private static String getParamString(Map<String, String> paramsMap) {
		if(paramsMap != null && !paramsMap.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for(String key : paramsMap.keySet()) {
				builder.append("&")
				.append(key)
				.append("=")
				.append(paramsMap.get(key));
			}
			return builder.deleteCharAt(0).toString();
		}
		return "";
	}
	
	/**
	 * 是否是POST请求
	 * @param requestMethod
	 * @return
	 */
	private static boolean isPost(String requestMethod) {
		return requestMethod != null && !"".equals(requestMethod) && POST.equals(requestMethod);
	}
	
}
