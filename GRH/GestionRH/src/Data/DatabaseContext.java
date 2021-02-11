package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

// Singleton

class DatabaseContext {
	
	
	private static final String user = "postgres";
	private static final String pass = "root";
	private static final String url = "jdbc:postgresql://localhost:5432/GRH";
	
	public DatabaseContext() {
		loadDatabaseDriver();
	}
	
	private void loadDatabaseDriver() {
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Impossible de charger le driver de la BD.");
			System.exit(-1);
		}
	}
	
	public static Connection connectToDatabase() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
		}catch(SQLException exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
			System.exit(-1);
		}
		
		return con;
	}
	
	public static Object executeSqlQuery(String req, boolean update) {
		
		Connection con = null;
		ResultSet res = null;
		int res_update = 0;
		try {
			con = connectToDatabase();
			Statement s = con.createStatement();
			if(update == false) {
				res = s.executeQuery(req);
				return res;
			}
			else {
				res_update = 0;
				res_update = s.executeUpdate(req);
				return res_update;
			}
		}catch(SQLException exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
		
		if(update == false) {
			return res;
		}
		else {
			return res_update;
		}
	}
	

	
	
	
	
	
	

}
