package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jerseyrestdemo")
public class JerseyRESTfulDemo {

	@GET//接受GET类型请求
	@Path("/hi")
	@Produces(MediaType.TEXT_PLAIN)//响应MIME类型为text/plain
	public String sayHi() {
		return "hello world";
	}
	
}
