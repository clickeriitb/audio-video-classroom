package DatabaseConnection;

import java.io.IOException;

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
 * Servlet implementation class updateRequests
 */
@WebServlet("/updateRequests")
public class updateRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public updateRequests() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String instructor_session=(String)request.getSession().getAttribute("iid");
		if(instructor_session==null){
			response.sendRedirect("/AVCRIOA/index.jsp");
		}
		String sid=request.getParameter("id");
		int val=Integer.parseInt(request.getParameter("val"));
		String iid=request.getParameter("iid");
		System.out.println(val+sid+iid);
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		dbconnection con_object=new dbconnection();
	    conn=con_object.createDatabaseConnection();
	    String query2="select courseID from instructorcourse where instructorID='"+iid+"'";
	    String query=null;
	    try{
	    	st=conn.createStatement();
	    	rs=st.executeQuery(query2);
	    	if(rs.next()){
	    	String courseID=rs.getString("courseID");
	    	query="update requests set requestStatus='"+val+"' where studentID='"+sid+"' and courseID='"+courseID+"'";
	    	int k=st.executeUpdate(query);
	    	System.out.println("Rows effected: "+k);
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally{
	    	
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
