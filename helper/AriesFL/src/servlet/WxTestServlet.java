package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信测试servlet
 * @author ctyFL
 * @date 2020年5月17日
 * @version 1.0
 */
public class WxTestServlet extends HttpServlet {
	
	 public void init() throws ServletException {
	    	super.init();
	 }

	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.getWriter().append("Served at: ").append(request.getContextPath() + ": ");
	 }
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html;charset=UTF-8");
		 String access_token = request.getParameter("access_token");
		 String openId = request.getParameter("touser");
		 String msgtype = request.getParameter("msgtype");
		 String content = request.getParameter("text");
		
		 response.getWriter().append("access_token:" + access_token + "\n");
		 response.getWriter().append("openId:" + openId + "\n");
		 response.getWriter().append("msgtype:" + msgtype + "\n");
		 response.getWriter().append("content:" + content);
		
	 }
			
	 
}
