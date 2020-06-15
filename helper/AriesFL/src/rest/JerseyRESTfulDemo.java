package rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/jerseyrestdemo")
public class JerseyRESTfulDemo {

	@POST//接受POST类型请求
	@Path("/hi")
	@Produces(MediaType.TEXT_PLAIN)//响应MIME类型为text/plain
	public String sayHi(@FormParam("str") String data, @Context HttpServletRequest request) {
		String jsonData = data;
		String contextPath = request.getContextPath();
		System.out.println("data：" + jsonData);
		System.out.println("contextPath：" + contextPath);
		return "hello world";
	}
	
}
