package test;

import java.util.HashMap;
import java.util.Map;

import utils.DateUtil_FL;
import utils.ExcelUtil_FL;
import utils.FileUtil_FL;
import utils.HttpUtil_FL;
import utils.StringUtil_FL;
import utils.wxUtils.AccessToken_FL;

public class TestMain {

	public static void main(String[] args) {
//		String date1 = "2019.8";
//		String date2 = "2020.1";
//		String date3 = "2015.11";
//		
//		String str1 = DateUtil_FL.getTimeIntervalByTwoDates(date1, date2, DateUtil_FL.ONLY_MONTH);
//		String str2 = DateUtil_FL.getTimeIntervalByTwoDates(date2, date3, DateUtil_FL.YEAR_AND_MONTH);
//		
//		System.out.println(str1);
//		System.out.println(str2);
//		
//		String ss = "sdfjlg?#$%^&sdfsd()&%^~!@#$,./.=-0";
//		System.out.println(StringUtil_FL.filterSpecialCharacterFromString(ss));
//		
//		String string = "dfsag《，。？！’‘“”《》￥";
//		System.out.println(StringUtil_FL.convertPunctuationToEnglishStatus(string));
//		
//		System.out.println(StringUtil_FL.getNowDateCurrentTimeStr());
//		
//		String servletName = "http://192.168.10.153:9100/AriesFL/ReceiveFileServlet";
//		FileUtil_FL.uploadToServer("D:/31.jpg", servletName);
//		
//		HttpUtil_FL.HttpGetThread(null, servletName);
//		HttpUtil_FL.HttpPostThread(null, servletName);
//		
//		ExcelUtil_FL.readXlsxExcelByPOI("D:/22.xlsx");
		
		AccessToken_FL accessToken_FL = AccessToken_FL.getInstance();
		String access_token = accessToken_FL.getMiniAppAccessToken();
		System.out.println();
		
		String josnStr = "{'content':'hello'}";
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("touser", "oS2PU5LK8MoZ09gvhc8iTSoEnm_U");
		paramsMap.put("msgtype", "text");
		paramsMap.put("text", josnStr);
		String result = HttpUtil_FL.HttpPost(paramsMap, url);
		System.out.println(result);
	}

}
