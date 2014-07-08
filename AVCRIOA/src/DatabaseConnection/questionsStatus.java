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
 * Servlet implementation class questionsStatus
 */
@WebServlet("/questionsStatus")
public class questionsStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public questionsStatus() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String instructor_session=(String)request.getSession().getAttribute("iid");
		if(instructor_session==null){
			response.sendRedirect("/AVCRIOA/index.jsp");
		}
		String number=request.getParameter("number");
		System.out.println(number);
		if(number!=null){
			Connection conn=null;
			
			Statement st=null;
			dbconnection con_object=new dbconnection();
		    conn=con_object.createDatabaseConnection();
		    String query="update questions set status='0' where sno="+number;
		    try{
		    	st=conn.createStatement();
		    	int k=st.executeUpdate(query);
		    	System.out.println("Rows updated:"+k);
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	try {
					st.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    }
		}
	}

}
