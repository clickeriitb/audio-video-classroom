package DatabaseConnection;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
public class dbconnection{
	public Connection createDatabaseConnection(){
	DataSource dataSource;
	Connection conn=null;
	Context initContext=null;
	Context envContext=null;
	try{
		initContext=new InitialContext();
		envContext=(Context)initContext.lookup("java:/comp/env");
		dataSource=(DataSource)envContext.lookup("jdbc/avcrioa");
		conn=(dataSource).getConnection();
	}catch(Exception e){
		e.printStackTrace();
	}
	return conn;
	}
}