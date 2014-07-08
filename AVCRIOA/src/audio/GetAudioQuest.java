package audio;

import java.io.IOException;
import java.sql.DriverManager.*;
import java.sql.*;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DatabaseConnection.dbconnection;

/**
 * Servlet implementation class GetAudioQuest
 */
@WebServlet("/GetAudioQuest")
public class GetAudioQuest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAudioQuest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		HttpSession session=request.getSession();
		String instructorId=(String)session.getAttribute("iid");
	//	String instructorId=request.getParameter("iid");
		System.out.println("IID: "+instructorId);
		if(instructorId==null){
			response.sendError(403,"Authentication Required..!");
			return;
		}
		
		String courseId=null;
		PrintWriter out=response.getWriter();
		int i=1;
		try{
		dbconnection con_object=new dbconnection();
		con=con_object.createDatabaseConnection();
		System.out.println(con.toString());
		String sql=null;
		PreparedStatement ps=null;
		sql= "select  courseId from instructorcourse where instructorID=?";
		ps = con.prepareStatement(sql);
		ps.setString(1,instructorId);
		ResultSet rs1=ps.executeQuery();
		if(!rs1.next()){
			out.println("Invalid Request..!");
			return;
		}
		else{
			courseId=rs1.getString(1);
			System.out.println("Course Id: "+courseId);
		}
		sql = "select student,sno,duration,time from audio where course=? order by time desc";
		ps = con.prepareStatement(sql);
		ps.setString(1,courseId);
		out.println("<table id=\"audiorequestpanel\"><tr><th>S.No</th><th>Student</th><th>Audio </th><th>&nbsp;Length&nbsp;</th><th>Status&nbsp;&nbsp;</th><th>&nbsp;&nbsp;&nbsp;&nbsp;Time Stamp&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Action</th></tr>");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			out.println("<tr><td>"+i+"</td><td>"+rs.getString(1)+"</td>");
			out.print("<td><audio width=70px controls id=aud"+i+" src=\"/AVCRIOA/GetAudio?course="+courseId+"&sno="+rs.getInt(2)+"\">");
		//	out.print("  <source src=\"GetAudio?sno="+rs.getInt(2)+"\" type=\"audio/wav\">");
			out.println("</audio>\n</td>");
			out.println("<td>&nbsp;&nbsp;&nbsp;"+String.format("%.2f",rs.getFloat(3))+" sec</td>");
			out.println("<td id=audstat"+i+"></td>");	
			out.println("<td>"+rs.getTimestamp(4)+"</td>");
			out.println("<td><button onclick=\"deleteAudio('"+courseId+"','"+rs.getInt(2)+"')\">Remove</button></td></tr>");
			i++;
			}
			if(i==1){
				out.println("<td colspan=7 style=\"text-align:center;\">There are no audio questions..!</td>");
			}
			else{
				out.println("<script>addListeners("+i+");</script>");
			}
			out.println("</table>");
		}
	
		catch(SQLException e){
			out.println("SQL Exception");
			e.printStackTrace(out);
		}
		finally{
			if(con!=null){
				try{con.close();}
				catch(SQLException e){e.printStackTrace();}
			}
		}
	    		  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
