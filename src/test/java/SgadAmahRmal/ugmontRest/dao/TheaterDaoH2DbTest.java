package SgadAmahRmal.ugmontRest.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import SgadAmahRmal.ugmontRest.database.Database;
import SgadAmahRmal.ugmontRest.domain.Theater;

/*
 * Enhancement: mocking database
 */
public class TheaterDaoH2DbTest {

	private Database bdd;	
	private TheaterDaoH2Db dao;
	
	@Before
	public void setUp() throws Exception {
		bdd = Database.getInstance();
		dao = new TheaterDaoH2Db(bdd);
	}

	@Test
	public void testFindIdExist() {
		Theater test = dao.find("12");
		assertEquals("12", test.getId());
		assertEquals("Cinéma Ecran 7", test.getName());
		assertEquals("Cugnaux", test.getCity());
		assertEquals("31270", test.getZipcode());
		assertEquals("31", test.getRegion());
	}

}
