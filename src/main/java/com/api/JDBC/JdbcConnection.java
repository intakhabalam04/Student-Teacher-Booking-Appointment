package com.api.JDBC;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {
	public static Connection getConnection(PrintWriter printWriter){
        String url ="jdbc:mysql://localhost:3306/stab";
        String user="root";
        String pass="Intakhab";

        Connection connection=null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,pass);
        }catch (Exception e){
            printWriter.println("Error to getting the connection "+e.getMessage());
        }
        return connection;
    }
	
	
}
