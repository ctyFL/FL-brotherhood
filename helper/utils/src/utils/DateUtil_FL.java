package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 日期工具类
 * @author ctyFL
 * @date 2020年4月9日
 * @version 1.0
 * 
 */
public class DateUtil_FL {
	
	public static final int YEAR_AND_MONTH = 0;
	public static final int ONLY_MONTH = 1;
	public static final int YEAR_SPLIT = 2;
	public static final int MONTH_SPLIT = 3;
	
	public static final Map<Integer, String> dateFormatTypeMap = new HashMap<Integer, String>(); 
	public static final int YYYY_POINT_MM = 0;
 	public static final int YYYYMMDD = 1;
	
	static {
		dateFormatTypeMap.put(YYYY_POINT_MM, "yyyy.MM");
		dateFormatTypeMap.put(YYYYMMDD, "yyyyMMdd");
	}

	/**
	 * 返回两个日期之间的时间间隔，单位：年/月、月
	 * @param startDate
	 * @param endDate
	 * @param type 1.YEAR_AND_MONTH 2.ONLY_MONTH 3.YEAR_SPLIT 4.MONTH_SPLIT
	 * @return
	 */
	public static String getTimeIntervalByTwoDates(String startDateStr, String endDateStr, int type) {
		String cutDateStr = "";
		try {
			Date startDate = formatDateFromStr(YYYY_POINT_MM, startDateStr);
			Date endDate = formatDateFromStr(YYYY_POINT_MM, endDateStr);
			if(!startDate.before(endDate)) {
				startDate = formatDateFromStr(YYYY_POINT_MM, endDateStr);
				endDate = formatDateFromStr(YYYY_POINT_MM, startDateStr);
			}
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(startDate);
			end.setTime(endDate);
			int month = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
			int year = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
			if(month < 0) {
				month += 12;
				year --;
			}
			if(type == YEAR_AND_MONTH) {
				if(year > 0 && month > 0) {
					cutDateStr = year + "年" + month + "月";
				}else if(year > 0) {
					cutDateStr = year + "年";
				}else {
					cutDateStr = month + "月";
				}
			}else if(type == ONLY_MONTH) {
				if(year > 0 && month > 0) {
					month = year * 12 + month;
				}else if(year > 0) {
					month = year * 12;
				}
				cutDateStr = month + "月";
			}else if(type == YEAR_SPLIT) {
				cutDateStr = year + "年";
			}else if(type == MONTH_SPLIT) {
				cutDateStr = month + "月";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cutDateStr;
	} 
	
	/**
	 * 返回按指定格式解析的日期对象
	 * @param type
	 * @param dateStr
	 * @return
	 */
	public static Date formatDateFromStr(int type, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatTypeMap.get(type));
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回指定的格式化日期字符串
	 * @param type
	 * @param date
	 * @return
	 */
	public static String formatDateToStr(int type, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatTypeMap.get(type));
		return sdf.format(date);
	}
	
}
