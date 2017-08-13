package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.xml.internal.ws.api.addressing.AddressingVersion.EPR;

/**
 * Servlet implementation class FileImageUpload
 */
public class FileImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private  ServletFileUpload upload;
	private final long MAXSize=4194304*6L;
	private String fileDir;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileImageUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		FileItemFactory factory=new DiskFileItemFactory();
		this.upload=new ServletFileUpload(factory);
		
//		int start=request.getSession().getServletContext().getRealPath("").indexOf(".");
//		String file_path=request.getSession().getServletContext().getRealPath("");
//		file_path=file_path.substring(0, start);
//		String file_content_path=request.getContextPath().substring(1, request.getContextPath().length());
//		System.out.println(file_path+file_content_path);
		
		
		this.upload.setSizeMax(this.MAXSize);
		fileDir="C:\\wamp\\www\\images";
		File file_dir=new File(fileDir);
		if (!file_dir.exists()) {
			file_dir.mkdir();
		}
		
		
		PrintWriter out=response.getWriter();
		try{
			List<FileItem>items=this.upload.parseRequest(request);
			if (items!=null&!items.isEmpty()) {
				for(FileItem fileItem:items){
					System.out.println(fileItem);
					String filename=fileItem.getName();
					String filepath=fileDir+File.separator+filename;
					
					System.out.println("文件保存路径为："+filepath);
					File file=new File(filepath);
					InputStream inputStream=fileItem.getInputStream();
					BufferedInputStream fInputStream=new BufferedInputStream(inputStream);
					FileOutputStream fos=new FileOutputStream(file);
					int f;
					while((f=fInputStream.read())!=-1){
						fos.write(f);
					}
					fos.flush();
					fos.close();
					fInputStream.close();
					inputStream.close();
					System.out.println("文件"+filename+"上传成功");
				}
			}
			System.out.println("文件上传成功");
			out.write("上传文件成功");
		}catch(FileUploadException e){
			e.printStackTrace();
			out.write("上传文件失败"+e.getMessage());
		}
	}

}
