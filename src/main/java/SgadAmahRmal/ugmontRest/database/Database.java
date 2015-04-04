package SgadAmahRmal.ugmontRest.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton to access on database.
 * This is loaded when the application start
 * 
 * @author Romain
 *
 */
public class Database {

	private Connection connection = null;
	private Statement stat = null;
	private static final Database INSTANCE = new Database();
	
	private Database() {
		try {
			this.connection = DriverManager.getConnection("jdbc:h2:~/test");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.stat = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		executeStartDump();
	}
	
	public static Database getInstance() {
		return INSTANCE;
	}
	
	public void executeSql(String intructionSql) {
		try {
			stat.execute(intructionSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void executeStartDump() {
		try {
			BufferedReader read = new BufferedReader(new FileReader("dump.sql"));
			String dump = "";
			String line;
			try {
				while ((line = read.readLine()) != null) {
					dump += line;
				}
				executeSql(dump);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
}
