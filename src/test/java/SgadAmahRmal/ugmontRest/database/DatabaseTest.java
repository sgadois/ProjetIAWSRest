package SgadAmahRmal.ugmontRest.database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {

	private Database bdd;
	
	@Before
	public void setUp() throws Exception {
		bdd = Database.getInstance();
	}

	@Test
	public void testStartDump() throws SQLException {
		String utopia = null;
		ResultSet res = bdd.getQuery("SELECT name FROM salle WHERE id=1");
		if (res.next()) {
			utopia = res.getString("name");
		}
		assertEquals("UTOPIA", utopia);
	}

}
