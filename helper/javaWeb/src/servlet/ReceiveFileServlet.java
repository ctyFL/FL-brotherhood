package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * 
 * 接收文件servlet
 * 需要jar包（commons-fileupload.jar）
 * @author ctyFL
 * @date 2020年4月16日
 * @version 1.0
 * 
 */
public class ReceiveFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Map<String, String> params = new HashMap<String, String>();
	private String savepath = "";
	private FileItem fileItem = null; 
 	
    public ReceiveFileServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	this.savepath = getInitParameter("savepath") + File.separator;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		createSavePath();
		parseFileParam(request);
		if(fileItem == null) {
			response.getWriter().println("paramError");
			return;
		}
		try {
			saveFile(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				deleteTempFile();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存文件
	 * @param request
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveFile(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		File file = new File("E:/ppppp");
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		InputStream in = fileItem.getInputStream();
		String newFileName = "112.txt";
		File newFile = new File(savepath + newFileName);
		FileOutputStream out = new FileOutputStream(newFile);
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len = in.read(bytes)) != -1) {
			out.write(bytes, 0, len);
		}
		out.close();
		in.close();
		response.getWriter().println(savepath + "/" + newFileName);
	}
	
	/**
	 * 解析接收文件参数
	 * @param request
	 */
	private void parseFileParam(HttpServletRequest request) {
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list) {
				if(item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					params.put(name, value);
				}else {
					fileItem = item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除处理文件上传时生成的临时文件
	 * @throws IOException
	 */
	private void deleteTempFile() throws IOException {
		if(fileItem != null) {
			fileItem.delete();
			System.gc();
		}
	}
	
	/**
	 * 创建文件保存父级路径
	 */
	private void createSavePath() {
		File file = new File(savepath);
		if(!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}
	
}
