package utils;

import java.util.Date;
import java.util.UUID;

/**
 * 
 * 字符串工具类
 * @author ctyFL
 * @date 2020年4月11日
 * @version 1.1
 * 
 */
public class StringUtil_FL {
	
	private static final String[] SPECIAL_CHARACTER = { "\\","$","(",")","*","+",".","[", "]","?","^","{","}","|","'","%","!","@","#","￥",",","。","，","&","-","_","~","=","<",">","/","`"};   
	private static final String[] PUNCTUATION_CN = {"！","：","？","，","。","；","（","）","￥","《","》","‘","’","“","”"};
	private static final String[] PUNCTUATION_EN = {"!",":","?",",",".",";","(",")","$","<",">","'","'","\"","\""}; 
	
	/**
	 * 过滤所有特殊字符
	 * @param str
	 * @return
	 */
	public static String filterSpecialCharacterFromString(String str) {
		String returnStr = "";
		if(str != null && !"".equals(str)) {
			returnStr = str;
			for(int i=0; i<SPECIAL_CHARACTER.length; i++) {
				if(returnStr.contains(SPECIAL_CHARACTER[i])) {
					returnStr = returnStr.replace(SPECIAL_CHARACTER[i], ""); 
				}
			}
		}
		return returnStr;
	}
	
	/**
	 * 将所有中文标点符号转换为英文状态下
	 * @param str
	 * @return
	 */
	public static String convertPunctuationToEnglishStatus(String str) {
		String returnStr = "";
		if(str != null && !"".equals(str)) {
			returnStr = str;
			for(int i=0; i<PUNCTUATION_CN.length; i++) {
				if(returnStr.contains(PUNCTUATION_CN[i])) {
					returnStr = returnStr.replace(PUNCTUATION_CN[i], PUNCTUATION_EN[i]);
				}
			}
		}
		return returnStr;
	}
	
	/**
	 * 获取随机UUID字符串
	 * @return
	 */
	public static String getRandomUUIDStr() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 返回当前日期+当前时间毫秒数字符串
	 * @return
	 */
	public static String getNowDateCurrentTimeStr() {
		Date date = new Date();
		return DateUtil_FL.formatDateToStr(DateUtil_FL.YYYYMMDD, date) + System.currentTimeMillis();
	}
}
