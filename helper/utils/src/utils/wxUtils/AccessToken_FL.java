package utils.wxUtils;

import utils.HttpUtil_FL;

/**
 * 
 * 微信全局唯一后台接口调用凭据
 * @author ctyFL
 * @date 2020年5月14日
 * @version 1.0
 * 
 */
public class AccessToken_FL {

	private String miniAppId = "wx57b6c3f6da1c9a24";
	private String miniAppSecret = "4038b724832658201e06e759e8e09afa";
	private String access_token = "";
	private Long expires_in = 0l;
	private Long lastGetCurrentTime = 0l;
	private String miniAppGetAcessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=" + miniAppId + "&secret=" + miniAppSecret;
	
	private AccessToken_FL() {
		
	}

	public static AccessToken_FL getInstance() {
		return Inner.instance;
	}
	
	private static class Inner {
		private static final AccessToken_FL instance = new AccessToken_FL();
	}

	public String getMiniAppId() {
		return miniAppId;
	}

	public void setMiniAppId(String miniAppId) {
		this.miniAppId = miniAppId;
	}

	public String getMiniAppSecret() {
		return miniAppSecret;
	}

	public void setMiniAppSecret(String miniAppSecret) {
		this.miniAppSecret = miniAppSecret;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setLastGetCurrentTime(Long lastGetCurrentTime) {
		this.lastGetCurrentTime = lastGetCurrentTime;
	}

	public String getMiniAppGetAcessTokenUrl() {
		return miniAppGetAcessTokenUrl;
	}

	public void setMiniAppGetAcessTokenUrl(String miniAppGetAcessTokenUrl) {
		this.miniAppGetAcessTokenUrl = miniAppGetAcessTokenUrl;
	}

	/**
	 * 获取微信小程序的access_token
	 * @return
	 */
	public String getMiniAppAccessToken() {
		if(!"".equals(access_token) && isInAvailableTime()) {
			return access_token;
		}else {
			String result = HttpUtil_FL.HttpGet(null, miniAppGetAcessTokenUrl);
			//{"access_token":"33_n2sWB3oxoKsuEZdACs4naqdI0uMi7jF_CDVKkblsu7u4HCQ3sJ8C_iSPn3lIgbbzZDzaJs2XZ7gL6Ljrm8HBumSyqfE6XNSdjA6P8nQbDq-aHAqaFvCWZQ9rpNkiDLAodX9X-B7n2PDJsou3VCBdAFABIH","expires_in":7200}
			if(isGetSuccess(result)) {
				access_token = splitJsonStrToGet("access_token", result);
				expires_in = Long.valueOf(splitJsonStrToGet("expires_in", result));
				lastGetCurrentTime = System.currentTimeMillis();
			}else {
				access_token = "";
			}
			return access_token;
		}
	}
	
	public boolean isInAvailableTime() {
		return System.currentTimeMillis() - lastGetCurrentTime < expires_in;
	}
	
	private boolean isGetSuccess(String resultJsonStr) {
		return resultJsonStr.indexOf("errcode") < 0 && resultJsonStr.indexOf("access_token") != -1;
	}
	
	private String splitJsonStrToGet(String type, String resultJsonStr) {
		String result = "";
		resultJsonStr = resultJsonStr.replaceAll("{", "");
		resultJsonStr = resultJsonStr.replaceAll("}", "");
		resultJsonStr = resultJsonStr.replaceAll("\\\"", "");
		String[] strs = resultJsonStr.split(":");
		if("access_token".equals(type)) {
			result = strs[1];
		}else if("expires_in".equals(type)) {
			result = strs[3];
		}
		return result;
	}
	
}
