package test;

import java.util.HashMap;
import java.util.Map;

import utils.HttpUtil_FL;
import utils.wxUtils.AccessToken_FL;

public class WxTest {

	public static void main(String[] args) {
		
		AccessToken_FL accessToken_FL = AccessToken_FL.getInstance();
		String access_token = accessToken_FL.getMiniAppAccessToken();
		System.out.println();
		
		String josnStr = "content=hello";
		String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token;
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("touser", "oS2PU5KGHN_q8XX1Po5OJupi1qEM");
		paramsMap.put("mp_template_msg", "06XNv0echLQKf-9WDeXsznmUivk5c0TQrZFBSg9hihY");
		paramsMap.put("data", "");
		String result = HttpUtil_FL.HttpPost(paramsMap, url);
	}
	
}
