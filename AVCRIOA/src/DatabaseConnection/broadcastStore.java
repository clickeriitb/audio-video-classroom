package DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.*;

/**
 * Servlet implementation class broadcastStore
 */
@WebServlet("/broadcastStore")
public class broadcastStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public broadcastStore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String session_id=request.getParameter("s_id");
		int stat = Integer.parseInt(request.getParameter("status"));
		String instructor_session=(String)request.getSession().getAttribute("iid");
		if(instructor_session!=null){
		dbconnection con_object=new dbconnection();
		Connection conn=con_object.createDatabaseConnection();
		Statement s=null;
		if(session_id!=null)
		{
			String query="insert into instructorBroadcast(id, status) values ('"+session_id+"', "+stat+");";
			PrintWriter out = response.getWriter();
			try
			{
				s = conn.createStatement();
				int k = s.executeUpdate(query);
				if(k>0)
				{
					out.println("BroadCast Started!!");
				}
				else
				{
					out.println("Broadcast Not Started!!");
				}
			}
			catch(Exception e)
			{
				out.println("Exception"+e);
			}
			finally
			{
				try
				{
					s.close();
					//rs.close();
					conn.close();
				}
				catch(Exception e)
				{
					out.println("Error: "+e);
				}
					
			}
		}
		}else{
			response.sendRedirect("/AVCRIOA/index.jsp");
		}
		
	}

}
