package audio;
import DatabaseConnection.*;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.sql.DriverManager.*;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class sendAudio
 */
@WebServlet("/GetAudio")
public class GetAudio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAudio() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = null;
		Connection con = null;
		HttpSession session=request.getSession();
		String iid=(String)session.getAttribute("iid");
		int sno=0;
		if(iid==null){
			System.out.println("Unauthorised attempt from ip:"+request.getRemoteAddr());
			response.sendError(403,"Not Authorized..!");
			return;
		}
		if(request.getParameter("sno")==null||request.getParameter("course")==null){
			System.out.println("Malformed Syntax from "+iid+" using ip:"+request.getRemoteAddr());
			response.sendError(400,"Malformed Syntax");
		}
		else{
				Blob bl = null;
				InputStream inputStream = null;
				OutputStream outputStream = null;
				sno=Integer.parseInt(request.getParameter("sno"));
				String course=request.getParameter("course");
				try {
					dbconnection con_object=new dbconnection();
				    con=con_object.createDatabaseConnection();
					String sql = "select file,student from audio where sno=? ";
					System.out.println(sql+sno);
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1,sno);
					ResultSet rs = ps.executeQuery();
					String studentId=null;
					if (rs.next()) {
						/*
						 * http://www.codejava.net/java-ee/servlet/java-servlet-to-download
						 * -file-from-database
						 */
						bl = rs.getBlob(1);
						studentId=rs.getString(2);
						inputStream = bl.getBinaryStream();
						int fileLength = inputStream.available();
						String mimeType = "audio/wav";
						response.setContentType(mimeType);
						response.setContentLength(fileLength);
						String headerKey = "Content-Disposition";
						String headerValue = String
								.format("attachment; filename=\""+studentId+".wav\"");
						response.setHeader(headerKey, headerValue);
						outputStream = response.getOutputStream();
		
						byte[] buffer = new byte[BUFFER_SIZE];
						int bytesRead = -1;
		
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, bytesRead);
						}
						System.out.println("Professor for"+course+" downloaded"+studentId+".wav from ip:"+request.getRemoteAddr());
						inputStream.close();
						outputStream.close();
					}
					else {
						response.sendError(405,"Couldn't retrieve the file..!");
					}
	
					con.close();
				} catch (SQLException e) {
					response.getWriter().print("SQL or Class Error..!");
					e.printStackTrace();
				}
				finally{
					if(con!=null){
						try{con.close();}catch(SQLException e){e.printStackTrace(pw);}
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
