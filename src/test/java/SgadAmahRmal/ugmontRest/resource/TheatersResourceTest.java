package SgadAmahRmal.ugmontRest.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import SgadAmahRmal.ugmontRest.Main;
import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.database.Param;
import SgadAmahRmal.ugmontRest.domain.Theater;

public class TheatersResourceTest {

	private HttpServer server;
    private WebTarget target;
    private Response response;
    
	@Before
	public void setUp() throws Exception {
		server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
	}
	
	@After
    public void tearDown() throws Exception {
    	server.shutdownNow();
    }
	
	@Test
	public void testTheatersResourceNoQuery() {
		// Given: nothing...
		// When
		response = target.path("theaters")
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(404, response.getStatus());
		response.close();
	}

	@Test
	public void testGetTheatersByFilmIdBadRequest() {
		// Given
		String wrongID = "tt123456";
		
		// When
		response = target.path("theaters").path("films").path(wrongID)
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(400, response.getStatus());
		response.close();
	}
	
	@Test
	public void testGetTheatersByFilmIdNoResult() {
		// Given
		String goodIDNotInDb = "tt0034567";
		
		// When
		response = target.path("theaters").path("films").path(goodIDNotInDb)
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(204, response.getStatus());
		response.close();
	}
	
	@Test
	public void testGetTheatersByFilmIdNotAcceptable() {
		// Given
		String goodIDNotInDb = "tt0034567";
		
		// When
		response = target.path("theaters").path("films").path(goodIDNotInDb)
				.request(MediaType.TEXT_PLAIN).get();
		
		// Then
		assertEquals(406, response.getStatus());
		response.close();
	}
	
	@Test
	public void testGetTheatersByFilmIdResult() throws IOException {
		// Given
		String id = "tt0164354";
		ITheaterDao dao = Mockito.mock(ITheaterDao.class);
		List<Theater> list = buildListForTheaterByFilmId(); // End of the class
		when(dao.findByFilmId(id)).thenReturn(list);
		
		String xml = "";
		String line;
		BufferedReader file = new BufferedReader(
				new FileReader("src/main/resources/testGetTheatersByFilmIdResult.xml"));
		while((line = file.readLine()) != null) {
			xml += line;
		}
		file.close();
		
		// When
		String restXml = target.path("theaters").path("films").path(id)
				.request(MediaType.APPLICATION_XML).get(String.class);
		
		// Then
		assertEquals(xml, restXml);
	}
	
	@Test
	public void testSearchTheatersEmptyQuery() {
		// Given nothing...
		// When
		response = target.path("theaters")
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(404, response.getStatus());
		response.close();
	}
	
	@Test
	public void testSearchTheatersQueryNotExist() {
		// Given 
		String keyInvalid = "truc";
		
		// When
		response = target.path("theaters")
				.queryParam(keyInvalid, "machin")
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(404, response.getStatus());
		response.close();
	}
	
	@Test
	public void testSearchTheatersParamInvalid() {
		// Given
		String valueInvalid = "invalid";
		
		// When
		response = target.path("theaters")
				.queryParam("dep", valueInvalid)
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(400, response.getStatus());
		response.close();
	}
	

	@Test
	public void testSearchTheatersNotAcceptable() {
		// Given
		String valueValid = "31";
		
		// When
		response = target.path("theaters")
				.queryParam("dep", valueValid)
				.request(MediaType.TEXT_PLAIN).get();
		
		// Then
		assertEquals(406, response.getStatus());
		response.close();
	}
	
	@Test
	public void testSearchTheatersParamValid() {
		// Given
		String valueValid = "31";
		
		// When
		response = target.path("theaters")
				.queryParam("dep", valueValid)
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(200, response.getStatus());
		response.close();
	}
	
	@Test
	public void testSearchTheatersParamValidNoContent() {
		// Given
		String valueValid = "45";
		
		// When
		response = target.path("theaters")
				.queryParam("dep", valueValid)
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(204, response.getStatus());
		response.close();
	}
	
	@Test
	public void testSearchTheatersParams() throws IOException {
		// Given
		String dep = "31";
		String city = "toul";
		List<Param> criteria = new ArrayList<Param>(2);
		criteria.add(new Param("departement", dep, Param.TypeSearch.EQUAL));
		criteria.add(new Param("city", city.toUpperCase(), Param.TypeSearch.CONTAINS));
		ITheaterDao dao = Mockito.mock(ITheaterDao.class);
		List<Theater> list = buildListForSearchTheater(); // End of the class
		when(dao.findTheatersByCriteria(criteria)).thenReturn(list);
		
		String xml = "";
		String line;
		BufferedReader file = new BufferedReader(
				new FileReader("src/main/resources/testSearchTheatersParams.xml"));
		while((line = file.readLine()) != null) {
			xml += line;
		}
		file.close();
		
		// When
		String restXml = target.path("theaters")
				.queryParam("dep", dep)
				.queryParam("city", city)
				.request(MediaType.APPLICATION_XML).get(String.class);
		
		// Then
		assertEquals(xml, restXml);
	}

	private List<Theater> buildListForTheaterByFilmId() {
		List<Theater> list = new ArrayList<Theater>(2);
		Theater theater12 = new Theater();
		theater12.setId("12");
		theater12.setName("Cinéma Ecran 7");
		theater12.setCity("Cugnaux");
		theater12.setZipcode("31270");
		theater12.setRegion("31");
		Theater theater8 = new Theater();
		theater8.setId("8");
		theater8.setName("Entrepot");
		theater8.setCity("Paris");
		theater8.setZipcode("75015");
		theater8.setRegion("75");
		list.add(theater12);
		list.add(theater8);
		return list;
	}
	
	private List<Theater> buildListForSearchTheater() {
		List<Theater> list = new ArrayList<Theater>(4);
		Theater theater1 = new Theater();
		theater1.setId("1");
		theater1.setName("Utopia");
		theater1.setCity("Toulouse");
		theater1.setZipcode("31000");
		theater1.setRegion("31");
		Theater theater4 = new Theater();
		theater4.setId("4");
		theater4.setName("UGC Gaumont");
		theater4.setCity("Toulouse");
		theater4.setZipcode("31000");
		theater4.setRegion("31");
		Theater theater5 = new Theater();
		theater5.setId("5");
		theater5.setName("Abc");
		theater5.setCity("Toulouse");
		theater5.setZipcode("31000");
		theater5.setRegion("31");
		Theater theater6 = new Theater();
		theater6.setId("6");
		theater6.setName("Le cratere");
		theater6.setCity("Toulouse");
		theater6.setZipcode("31000");
		theater6.setRegion("31");
		list.add(theater1);
		list.add(theater4);
		list.add(theater5);
		list.add(theater6);
		return list;
	}
}
