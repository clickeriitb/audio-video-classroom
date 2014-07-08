package DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class StudentLoginVerification
 */
@WebServlet("/StudentLoginVerification")
public class StudentLoginVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String responseVal = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentLoginVerification() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response){    	
    	String enrollmentID = request.getParameter("EnrollmentID");  
    	System.out.println(enrollmentID);
    	String macAddress = request.getParameter("MacAddress");
    	System.out.println(macAddress);
    	int flag =  Integer.parseInt(request.getParameter("Flag"));
    	System.out.println(enrollmentID + " --- " + macAddress +" --- " + flag);
    	final String MACADDRESS = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    	Pattern pattern = Pattern.compile(MACADDRESS);
    	Matcher matcher = pattern.matcher(macAddress);
    	request.getSession().setAttribute("studentID",enrollmentID);
    	if(!matcher.matches()){
    		try {
    			MyEncryption crypto = new MyEncryption();
    			System.out.println("Inside");
    			enrollmentID = crypto.decrypt(enrollmentID);
    			macAddress = crypto.decrypt(macAddress);
    			System.out.println(enrollmentID + " --- " + macAddress +" --- " + flag);    			
    		} catch (Exception e1) {
    			e1.printStackTrace();
    			return;
    		}
    	}
    	try{
    	PrintWriter out = response.getWriter();
		if (flag == 1) // check decision value if it is 1.
		{
			System.out.println("Flag : 1");
			responseVal = updateRecord(enrollmentID, macAddress);// call insertRecord() method.			
			out.print(responseVal);			
		} else if (flag == 3) {
			System.out.println("Flag : 3");
			responseVal = checkRecord(macAddress);
			out.print(responseVal);
		}
    	}catch (IOException e) {
			e.getStackTrace();
		}
    }

	private String checkRecord(String macAddress) {
		dbconnection dbConn = new dbconnection();
		Connection conn = dbConn.createDatabaseConnection();
		String result = "";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM student WHERE macAddress ='" +macAddress+ "'");
			if(rs.next()){
				System.out.println("Connect");
				result = "Connect";
			}else{
				System.out.println("Registration");
				result = "Registration";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)rs.close();			
				if(st!=null)st.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}


	private String updateRecord(String enrollmentID, String macAddress) {
		dbconnection dbConn = new dbconnection();
		Connection conn = dbConn.createDatabaseConnection();
		String result = "No";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM student WHERE  studentID = '"+enrollmentID+"' && macAddress = '" + macAddress + "'");
			if(rs.next()){
				System.out.println("QuizPage");
				result =  "QuizPage";
			}else{
				Statement st1 = conn.createStatement();
				ResultSet rs1 = st1.executeQuery("SELECT * FROM student WHERE  macAddress = '"+macAddress+"'");
				if(rs1.next()){
					if(!rs1.getString("studentID").equals(enrollmentID)){
						System.out.println("no");
						result = "No";
					}					
				}else{
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery("SELECT * FROM student WHERE  studentID = '" +enrollmentID+ "'");
					/*if(rs2.next()){
						result = "Duplicate";
					}else{
						Statement st3 = conn.createStatement();
						st3.executeUpdate("INSERT INTO student (StudentID,StudentRollNo,StudentName,YearofJoining,Privileges,DeptID,Password,status,EmailID,MacAddress) VALUES ('"+enrollmentID+"','"+enrollmentID+"','student"+enrollmentID+"','2012',null,'dept001','abc"+enrollmentID+"','0','"+enrollmentID+"@iitb.ac.in','"+macAddress+"')");
						result = "QuizPage";
						st3.close();
					}*/
					//
					if(rs2.next()){
						if(rs2.getString("macAddress").length()>1){
							System.out.println("duplicate");
							result = "Duplicate";
						}	
						else{
							Statement st3 = conn.createStatement();
							st3.executeUpdate("Update student set macAddress = '"+macAddress+"' where studentID = '"+enrollmentID+"'");
							System.out.println("QP");
							result = "QuizPage";
							if(st3!=null)st3.close();
						}
					}else{
						System.out.println("No");
						result = "No";
					}
					//
					if(rs2!=null)rs2.close();			
					if(st2!=null)st2.close();
				}
				if(rs1!=null)rs1.close();			
				if(st1!=null)st1.close();
			}					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null)rs.close();			
				if(st!=null)st.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
