package DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class listRequests
 */
@WebServlet("/listRequests")
public class listRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String instructorID=request.getParameter("iid");
		String instructor_session=(String)request.getSession().getAttribute("iid");
	if(instructor_session!=null){
		String courseID=null;
		if(instructorID!=null){
			Connection conn=null;
			ResultSet rs=null,rs2=null;
			java.sql.Statement st=null;
			dbconnection con_object=new dbconnection();
		    conn=con_object.createDatabaseConnection();
		    PrintWriter out=response.getWriter();
		    
		    String query=null;
		    String query2="select courseID from instructorcourse where instructorID='"+instructorID+"'";
		    try{
		    	st=conn.createStatement();
		    	rs2=st.executeQuery(query2);
		    	if(rs2.next()){
		    		courseID=rs2.getString("courseID");
		    		 query="select studentID from requests where courseID='"+courseID+"' and requestStatus=1";
		    	   
		    	}
		    	rs=st.executeQuery(query);
		    	while(rs.next()){
		    			String studentID=rs.getString("studentID");
		    			out.println("<button onclick='return call(this)' style=\"height:25px;font-size:18px;width:180px;\">"+studentID+"</button><br><br>");
		    	}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    finally{
		    	try {
					st.close();
					rs.close();
						rs2.close();
			    	conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    	
		    }
		}
	}else{
		response.sendRedirect("/AVCRIOA/index.jsp");
	}
	}

}
