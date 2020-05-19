package utils.wxUtils;

import java.util.Map;

/**
 * 
 * 微信小程序订阅消息-接口入参数据封装
 * @author ctyFL
 * @date 2020年5月19日
 * @version 1.0
 * 
 */
public class WxMiniAppMsgTemplate {
	
	private String touser; //接收用户openid 必填
	private String template_id; //消息模板id 必填
	private String page; //跳转小程序页面 选填
	/**
	 * 模板参数 必填  如：模板中设定两个参数为{{date3.DATA}}、{{phrase1.DATA}}
	 * 则map.put("date3", "2020-05-19 16:17:49") map.put("phrase1", "已派工") 
	 */
	private Map<String, String> data; 
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String toJsonStr() {
		String josnStr = "{";
		josnStr += "\"touser\":\"" + getTouser() + "\",";
		josnStr += "\"template_id\":\"" + getTemplate_id() + "\",";
		if(getPage() != null && !"".equals(getPage())) {
			josnStr += "\"page\":\"" + getPage() + "\",";
		}
		josnStr += "\"data\":";
		josnStr += "{";
		for(String key : getData().keySet()) {
			String value = getData().get(key);
			josnStr += "\"" + key + "\":{\"value\":\"" + value + "\"},";
		}
		josnStr = josnStr.substring(0,josnStr.length() -1);
		josnStr += "}";
		josnStr += "}";
		return josnStr;
	}
	
	public void printParamJsonStr() {
		String josnStr = toJsonStr();
		System.out.println("小程序订阅消息参数json：" + josnStr);
	}
	
}
