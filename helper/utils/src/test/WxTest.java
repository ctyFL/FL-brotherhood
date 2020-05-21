package test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import utils.DateUtil_FL;
import utils.HttpUtil_FL;
import utils.wxUtils.AccessToken_FL;
import utils.wxUtils.WxMiniAppMsgTemplate;

/**
 * 微信测试
 * @author ctyFL
 * @date 2020年5月19日
 * @version 1.0
 */
public class WxTest {

	public static void main(String[] args) {
		
		AccessToken_FL accessToken_FL = AccessToken_FL.getInstance();
		String access_token = accessToken_FL.getMiniAppAccessToken();
		/**发送订阅消息接口*/
		String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token;
		/**示例：FLbrotherhood模板消息id*/
		String templateid = "nJ8c2uLnhxmoX3B6GLyG6L3SU6FwNM7-SnEOkEy9u20";
		WxMiniAppMsgTemplate template = new WxMiniAppMsgTemplate();
		template.setTouser("oS2PU5KGHN_q8XX1Po5OJupi1qEM");//需填入FLbrotherhood中的openid  这里暂为由由
		template.setTemplate_id(templateid);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("date3", DateUtil_FL.formatDateToStr(DateUtil_FL.YYYY_MM_DDHHmmss, new Date()));
		paramsMap.put("phrase1", "已派工");
		template.setData(paramsMap);
		template.printParamJsonStr();
		String jsonStr = template.toJsonStr();
		/**post请求调用接口*/
		String result = HttpUtil_FL.HttpPost(jsonStr, url);
		
		if(result.indexOf("\"errcode\":0") !=-1) {
			System.out.println("发送成功");
		}else if(result.indexOf("\"errcode\":43101") !=-1) {
			System.out.println("用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系");
		}else if(result.indexOf("\"errcode\":40037") !=-1) {
			System.out.println("订阅模板id为空不正确");
		}else if(result.indexOf("\"errcode\":47003") !=-1) {
			System.out.println("模板参数不准确，可能为空或者不满足规则");
		}else if(result.indexOf("\"errcode\":47003") !=-1) {
			System.out.println("page路径不正确，需要保证在现网版本小程序中存在，与app.json保持一致");
		}
	}
	
}
