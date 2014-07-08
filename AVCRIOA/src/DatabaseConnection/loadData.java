package DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import DatabaseConnection.dbconnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 * Servlet implementation class loadData
 */
@WebServlet("/loadData")
public class loadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public loadData() {
        super();
        
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String student_session=(String)request.getSession().getAttribute("studentID");
		if(student_session==null){
			response.sendRedirect("/AVCRIOA/student/index.jsp");
		}
		String sid=request.getParameter("id");
		String iid=request.getParameter("iid");
		String cid=request.getParameter("courseID");
		System.out.println("studentid"+sid);
		
		dbconnection con_object=new dbconnection();
		Connection conn=con_object.createDatabaseConnection();
		Statement s=null;
		ResultSet rs=null;
		String query="insert into requests(studentID,courseID) values ('"+sid+"','"+cid+"')";
		String query2="select requestStatus from requests where studentID='"+sid+"' and requestStatus=1";
		PrintWriter out=response.getWriter();
		try{
			s=conn.createStatement();
			rs=s.executeQuery(query2);
			if(!rs.next()){
			int k=s.executeUpdate(query);
			System.out.println("Rows effected:"+k);
			out.println("<b>Request forwarded</b>");
			}
			else{
				out.println("<b>Request Already Sent</b>");
			}
		/*rs2=s.executeQuery("select instructorID from instructorcourse where instructorcourse.courseID=(select courseID from student where studentID='"+sid+"')");
		if(rs2.next()){
		String iid=rs2.getString("instructorID");*/
		System.out.println("instructorid"+iid);
		out.println("#"+iid);
		
		
			}
		catch(SQLException e){
			System.out.println(e);
			}
		finally{
				try {
					s.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
	}

}
