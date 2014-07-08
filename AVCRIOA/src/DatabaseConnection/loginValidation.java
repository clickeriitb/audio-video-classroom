package DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginValidation
 */
@WebServlet("/loginValidation")
public class loginValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginValidation() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("uname");
		String password=request.getParameter("passwd");
		dbconnection con_object=new dbconnection();
		Connection conn=con_object.createDatabaseConnection();
		ResultSet rs=null;
		Statement s=null;
		PrintWriter out=response.getWriter();
		String query="select password from instructor where instructorID='"+username+"'";
		try{
			s=conn.createStatement();
			rs=s.executeQuery(query);
			if(rs.next()){
				String dbpassword=rs.getString("password");
				if(dbpassword.equals(password)){
					request.getSession().setAttribute("iid",username);
					response.sendRedirect("/AVCRIOA/instructor/");
				}
				else{
					request.getSession().setAttribute("errmessage","Wrong Username or password");
					response.sendRedirect("/AVCRIOA/index.jsp");
					}
				}
			else{
				request.getSession().setAttribute("errmessage","Wrong Username or password");
				response.sendRedirect("/AVCRIOA/index.jsp");
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					s.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
	}


