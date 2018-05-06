package dk.dtu.compute.se.pisd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * This class provides connection to the database
 * @author Jacob Jørgensen */
public class Connector {
	private final String HOST     = "Localhost";
	private final int    PORT     = 3306;
	private final String DATABASE = "MatadorDemo";
	private final String USERNAME = "root"; 
	private final String PASSWORD = "";
	private Connection connection;

	public Connector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
			connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
			//System.out.println("Connected");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Connection getConnection(){
		return connection;
	}
	
	/**
	 * do a query in the database
	 * @param query the query you want to perform.
	 * @return ResultSet
	 */
	public ResultSet doQuery(String query) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery(query);
		return res;
		
	}
	
	/**
	 * do a query/update in the database
	 * @param query the query you want to perform.
	 * 
	 */
	public void doUpdate(String query) throws SQLException{
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(query);
	}
	
	/**
	 * do a procedure in the database
	 * @param functionName the name of the function you want to use.
	 * @param args all the arguments you need in the procedure.
	 * 
	 */
	public void runProcedure(String functionName, Object... args)throws SQLException{
		Statement stmt = connection.createStatement();
		String query = String.format("call %s", functionName);
		query += "(";
		
		for (Object o : args) {
			if(o.getClass() == String.class) 
				query += "'";
			query += o.toString();
			
			if(o.getClass() == String.class) 
				query += "'";
			query += ",";
		}
		query = query.substring(0, query.length()-1);
		query += ");";
		stmt.executeUpdate(query);
	}
}


