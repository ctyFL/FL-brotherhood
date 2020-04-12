package utils;
/**
 * 
 * 字符串工具类
 * @author ctyFL
 * @date 2020年4月11日
 * @version 1.0
 * 
 */
public class StringUtil {

	public String filterSpecialCharacterFromString(String str) {
		String returnStr = str;
		if(returnStr != null && !"".equals(returnStr)) {
			String[] specials = { "\\","$","(",")","*","+",".","[", "]","?","^","{","}","|","'",
					"%","!","@","#","￥",",","。","，","&","-","_","~","=","<",">","/","`"};   
			for(int i=0; i<specials.length; i++) {
				if(returnStr.contains(specials[i])) {
					returnStr = returnStr.replace(specials[i], ""); 
				}
			}
		}else {
			return "";
		}
		return returnStr;
	}
	
}
