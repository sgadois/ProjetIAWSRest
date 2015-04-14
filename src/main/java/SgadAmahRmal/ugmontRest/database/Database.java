package SgadAmahRmal.ugmontRest.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton to access on database.
 * This is loaded when the application start
 *
 */
public class Database {

	private Connection connection = null;
	private Statement stat = null;
	private static final Database INSTANCE = new Database();
	
	private Database() {
		try {
			this.connection = DriverManager.getConnection("jdbc:h2:~/test");
			this.stat = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			executeStartDump();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Database getInstance() {
		return INSTANCE;
	}
	
	public boolean executeSql(String intructionSql) {
		try {
			return stat.execute(intructionSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet getQuery(String query) {
		ResultSet res = null;
		try {
			res = stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void executeStartDump() throws IOException {
		try {
			BufferedReader read = new BufferedReader(new FileReader("src/main/resources/dump.sql"));
			String dump = "";
			String line;
			while ((line = read.readLine()) != null) {
				dump += line;
			}
			executeSql(dump);
			read.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
}
