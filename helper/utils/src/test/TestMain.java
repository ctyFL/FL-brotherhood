package test;

import utils.DateUtil;
import utils.FileUtil;
import utils.StringUtil_FL;

public class TestMain {

	public static void main(String[] args) {
		String date1 = "2019.8";
		String date2 = "2020.1";
		String date3 = "2015.11";
		
		String str1 = DateUtil.getTimeIntervalByTwoDates(date1, date2, DateUtil.ONLY_MONTH);
		String str2 = DateUtil.getTimeIntervalByTwoDates(date2, date3, DateUtil.YEAR_AND_MONTH);
		
		System.out.println(str1);
		System.out.println(str2);
		
		String ss = "sdfjlg?#$%^&sdfsd()&%^~!@#$,./.=-0";
		System.out.println(StringUtil_FL.filterSpecialCharacterFromString(ss));
		
		String string = "dfsag《，。？！’‘“”《》￥";
		System.out.println(StringUtil_FL.convertPunctuationToEnglishStatus(string));
		
		String servletName = "http://192.168.10.153:9100/AriesFL/ReceiveFileServlet";
		FileUtil.uploadToServer("D:/31.jpg", servletName);

	}

}
