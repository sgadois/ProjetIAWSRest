package SgadAmahRmal.ugmontRest.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import SgadAmahRmal.ugmontRest.database.Database;
import SgadAmahRmal.ugmontRest.database.Param;
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
		// Given
		String id = "12";
		
		// When
		Theater test = dao.find(id);
		
		// Then
		assertEquals("12", test.getId());
		assertEquals("31270", test.getZipcode());
	}
	
	@Test
	public void testFindByFilmIdWithResult() {
		// Given
		String id = "tt0164354";
		
		// When
		List<Theater> listTheaters = dao.findByFilmId(id);
		
		// Then
		assertEquals(2, listTheaters.size());
		assertEquals("12", listTheaters.get(0).getId());
		assertEquals("8", listTheaters.get(1).getId());
	}
	
	@Test
	public void testFindByFilmIdNoResult() {
		// Given
		String id = "tt0000000";
		
		// When
		List<Theater> listTheaters = dao.findByFilmId(id);
		
		// Then
		assertNull(listTheaters);
	}
	
	@Test
	public void testFindTheatersByCriteriaWithResult() {
		// Given
		List<Param> criteria = new ArrayList<Param>(2);
		criteria.add(new Param("departement", "31", Param.TypeSearch.EQUAL));
		criteria.add(new Param("city", "toul".toUpperCase(), Param.TypeSearch.CONTAINS));
		
		// When
		List<Theater> listTheaters = dao.findTheatersByCriteria(criteria);
		
		// Then
		assertEquals(4, listTheaters.size());
		assertEquals("1", listTheaters.get(0).getId());
		assertEquals("4", listTheaters.get(1).getId());
		assertEquals("5", listTheaters.get(2).getId());
		assertEquals("6", listTheaters.get(3).getId());
	}
	
	@Test
	public void testFindTheatersByCriteriaNoResult() {
		// Given
		List<Param> criteria = new ArrayList<Param>(2);
		criteria.add(new Param("departement", "45", Param.TypeSearch.EQUAL));
		
		// When
		List<Theater> listTheaters = dao.findTheatersByCriteria(criteria);
		
		// Then
		assertNull(listTheaters);
	}

}
