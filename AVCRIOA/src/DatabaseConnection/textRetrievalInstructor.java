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
 * Servlet implementation class textRetrievalInstructor
 */
@WebServlet("/textRetrievalInstructor")
public class textRetrievalInstructor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public textRetrievalInstructor() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String instructor_session=(String)request.getSession().getAttribute("iid");
		if(instructor_session==null){
			response.sendRedirect("/AVCRIOA/index.jsp");
		}
		String iid=request.getParameter("iid");
		Connection conn=null;
		ResultSet rs=null;
		Statement st=null;
		dbconnection con_object=new dbconnection();
	    conn=con_object.createDatabaseConnection();
	    PrintWriter out=response.getWriter();
	    String query="select * from questions where instructorID='"+iid+"' and status=1 order by sno asc";
	    if(iid!=null){
	    	try{
	    		st=conn.createStatement();
	    		rs=st.executeQuery(query);
	    		int y=1;
	    		out.println("<table id=\"audiorequestpanel\">");
	    		out.println("<tr><th>S.No</th><th>Question</th><th>Student ID</th><th>TimeStamp</th><th>Action</th></tr>");
	    		while(rs.next()){
	    			out.println("<tr><td>"+y+"</td><td>"+rs.getString("question")+"</td><td>"+rs.getString("studentID")+"</td><td>"+rs.getString("timeStamp")+"</td>");
	    			out.println("<td><button onclick='done("+rs.getString("sno")+")'> Done</button></td></tr>");
	    			y++;
	    			}	
	    		if(y==1){
	    			out.println("<tr><td colspan=6 style=\"text-align:center;\">There are no text questions..!</td></tr>");
	    		}
	    		out.println("</table>");
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try {
					st.close();
					rs.close();
		    		conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    }
	}

}
