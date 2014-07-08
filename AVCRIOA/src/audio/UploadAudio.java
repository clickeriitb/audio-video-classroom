package audio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import DatabaseConnection.dbconnection;

/**
 * Servlet implementation class UploadingAudio
 */
@WebServlet("/UploadAudio")
public class UploadAudio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filePath;
    private boolean isMultipart;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadAudio() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(){
 	   filePath = getServletContext().getInitParameter("file-upload");

 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String student_session=(String)request.getSession().getAttribute("studentID");
		if(student_session==null){
			response.sendRedirect("/AVCRIOA/student/index.jsp");
		}
		File file ;
		   int maxFileSize = 50000 * 1024;
		   int maxMemSize = 50000 * 1024;
		   PrintWriter out=response.getWriter();
		   isMultipart=ServletFileUpload.isMultipartContent(request);
		   if (!isMultipart) {
			      out.println("<html>");
			        out.println("<head>");
			         out.println("<title>Servlet upload</title>");  
			         out.println("</head>");
			         out.println("<body>");
			         out.println("<p>No file uploaded</p>"); 
			         out.println("</body>");
			         out.println("</html>");
			         return;
			   
		   }

		      DiskFileItemFactory factory = new DiskFileItemFactory();
		      // maximum size that will be stored in memory
		      factory.setSizeThreshold(maxMemSize);
		      // Location to save data that is larger than maxMemSize.
		 
		      factory.setRepository(new File("/tmp"));

			  
		      // Create a new file upload handler
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      // maximum file size to be uploaded.
		      upload.setSizeMax( maxFileSize );
		      InputStream finput=null;
		     
		      Connection con=null;
		      String msg=null;
		      List fileItems;
		  
		      try {
		      fileItems = upload.parseRequest(request);		      
		      HttpSession session=request.getSession(); 
		   	  String course=request.getParameter("course");
		  	//  String username=session.getAttribute("userid").toString();
		   	  String username="Varun";
		   	  Float duration=Float.parseFloat(request.getParameter("dur"));
		   	  
		      // Process the uploaded file items
		      Iterator i = fileItems.iterator();
		      FileItem fi = (FileItem)i.next();
		      finput=fi.getInputStream();
		      dbconnection con_object=new dbconnection();
			  con=con_object.createDatabaseConnection();
		      String sql="insert into audio(student,file,duration,course) values(?,?,?,?)";
		      PreparedStatement stmt=con.prepareStatement(sql);
		       
		      stmt.setString(1,fi.getName());
		         
		         if(finput!=null){
		            stmt.setBlob(2,finput);
		         }  
		         stmt.setFloat(3,duration);
		         stmt.setString(4,course);
		         int rowseffected=stmt.executeUpdate();
		         response.setContentType("text/html");
		        
		         if(rowseffected>0){
		        	out.println("File Name : "+username+".wav<br/>");
		        	out.println("Duration  :  "+duration+"<br/>");
		        	out.println("Course    : "+course+"<br/>");
		            out.println("File Uploaded and saved in database");
		         }
		      }
		      catch(SQLException ex) {
		          System.out.println(" Error in SQL Excepiton:  " +ex.getMessage());
		          ex.printStackTrace(out);
		      }
		      catch(FileUploadException e){
		    	  e.printStackTrace(out);
		      }
		      finally{
		         if(con!=null){
		            try{
		               con.close();
		            }
		            catch(SQLException ex){ex.printStackTrace(out);}
		         }
		      }
		   
		    

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}