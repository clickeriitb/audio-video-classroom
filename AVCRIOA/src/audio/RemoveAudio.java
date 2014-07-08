package audio;

import DatabaseConnection.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class RemoveAudio
 */
@WebServlet("/RemoveAudio")
public class RemoveAudio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveAudio() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
				sno=Integer.parseInt(request.getParameter("sno"));
				String course=request.getParameter("course");
				try {
					dbconnection con_object=new dbconnection();
				    con=con_object.createDatabaseConnection();
					String sql = "delete from audio where sno=? ";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1,sno);
					int i= ps.executeUpdate();
					if(i!=0){
						System.out.println("Audio file with sno ="+sno+" deleted from course "+course+" by Instructor with Id:"+iid);
					}
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
