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


@WebServlet("/setCourse")
public class setCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String student_session=(String)request.getSession().getAttribute("studentID");
		if(student_session==null){
			response.sendRedirect("/AVCRIOA/student/index.jsp");
		}
		String courseID=request.getParameter("courseID");
		System.out.println("courseID"+courseID);
		if(courseID!=null){
			Connection conn=null;
			ResultSet rs=null;
			Statement st=null;
			dbconnection con_object=new dbconnection();
		    conn=con_object.createDatabaseConnection();
		    PrintWriter out=response.getWriter();
		    String query="select instructorID from instructorcourse where courseID='"+courseID+"'";
		    try{
		    	st=conn.createStatement();
		    	rs=st.executeQuery(query);
		    	if(rs.next()){
		    		System.out.println("insid"+rs.getString("instructorID"));
		    		out.println(rs.getString("instructorID"));
		    	}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    finally{
		    	try {
					st.close();
					rs.close();
			    	conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    	
		    }
			}
		else{
			response.sendRedirect("/AVCRIOA/index.jsp");
		}
	}

}
