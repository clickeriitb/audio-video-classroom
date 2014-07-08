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
 * Servlet implementation class textDoubts
 */
@WebServlet("/textDoubts")
public class textDoubts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public textDoubts() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String student_session=(String)request.getSession().getAttribute("studentID");
		if(student_session==null){
			response.sendRedirect("/AVCRIOA/student/index.jsp");
		}
			String studentID=request.getParameter("studentID");
			String question=request.getParameter("question");
			String instructorID="";
			instructorID=request.getParameter("iid");
			PrintWriter out=response.getWriter();
			if(question!=null && studentID!=null && instructorID!=""){
						Connection conn=null;
						Statement st=null;
						dbconnection con_object=new dbconnection();
					    conn=con_object.createDatabaseConnection();
					    String query="insert into questions(studentID,instructorID,question) values('"+studentID+"','"+instructorID+"','"+question+"')";
					    try{
					    	st=conn.createStatement();
					    	int k=st.executeUpdate(query);
					    	System.out.println("Rows effected: "+k);
					    }catch(Exception e){
					    	e.printStackTrace();
					    }
					    finally{
					    	try {
								st.close();
						    	conn.close();
					    		} catch (SQLException e) {
								e.printStackTrace();
								}
					    	
					    	}
				}
			else{
				out.println("error");
			}
	}

}
