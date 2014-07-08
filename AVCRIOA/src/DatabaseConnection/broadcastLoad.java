package DatabaseConnection;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.*;

/**
 * Servlet implementation class broadcastLoad
 */
@WebServlet("/broadcastLoad")
public class broadcastLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public broadcastLoad() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String st=(String)request.getSession().getAttribute("studentID");
		if(st!=null){
		dbconnection con_obj = new dbconnection();
		Connection conn = con_obj.createDatabaseConnection();
		PrintWriter out = response.getWriter();
		Statement s=null;
		ResultSet rs = null;
		String query = "Select id from instructorBroadcast where status=1";
		try
		{
			s = conn.createStatement();
			rs = s.executeQuery(query);
			while(rs.next())
			{
				String val=rs.getString("id");
				out.println("<button onclick='return call(this)' style=\"height:28px;font-size:20px;width:200px;\">"+val+"</button><br><br>");
			}
		}
		catch(Exception e)
		{
			out.println("Error: "+e);
		}
		finally
		{
			try
			{
				rs.close();
				s.close();
				conn.close();
			}
			catch(Exception e)
			{
				out.println("Error: "+e);
			}
		}
	}
		else{
			response.sendRedirect("/AVCRIOA/student/index.jsp");
		}
	}

}
