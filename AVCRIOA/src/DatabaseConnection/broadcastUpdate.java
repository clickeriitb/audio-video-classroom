package DatabaseConnection;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;


/**
 * Servlet implementation class broadcastUpdate
 */
@WebServlet("/broadcastUpdate")
public class broadcastUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public broadcastUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String session_name=request.getParameter("session_id");
		dbconnection con_obj = new dbconnection();
		Connection conn = con_obj.createDatabaseConnection();
		Statement s=null;
		PrintWriter out = response.getWriter();
		if(session_name!=null)
		{
			String query = "update instructorBroadcast set status=0 where id='"+session_name+"';";
			try
			{
				s = conn.createStatement();
				int k = s.executeUpdate(query);
				if(k!=0)
				{
					out.println("Disconnected!");
				}
			}
			catch(Exception e)
			{
				out.print("Exception: "+e);
			}
			finally
			{
				try
				{
					s.close();
					conn.close();
				}
				catch(Exception e)
				{
					out.print("Exception: "+e);
				}
			}
		}
	}

}
