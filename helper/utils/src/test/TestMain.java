package test;

import utils.DateUtil;

public class TestMain {

	public static void main(String[] args) {
		DateUtil dateUtil = new DateUtil();
		String date1 = "2019.8";
		String date2 = "2020.1";
		String date3 = "2015.11";
		
		String str1 = dateUtil.getTimeIntervalByTwoDates(date1, date2, DateUtil.ONLY_MONTH);
		String str2 = dateUtil.getTimeIntervalByTwoDates(date2, date3, DateUtil.YEAR_AND_MONTH);
		
		System.out.println(str1);
		System.out.println(str2);
	}

}
