package utils;

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
	
	public static void postToServerThread(Map<String, String> paramsMap, String url) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
			
			}
		};
		thread.start();
	}

}
