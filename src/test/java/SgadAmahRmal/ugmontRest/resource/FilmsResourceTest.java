package SgadAmahRmal.ugmontRest.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
import SgadAmahRmal.ugmontRest.database.Database;
import SgadAmahRmal.ugmontRest.domain.Theater;

public class FilmsResourceTest {

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

    /* 
     * Tests sur la recherche de films
     */
    @Test
    public void testFindFilmsOkNoYear() {
    	response = target.path("films")
    			.queryParam("title", "there+will+be+blood")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, response.getStatus());
        response.close();
    }
    
    @Test
    public void testFindFilmsOkWithYear() {
    	response = target.path("films")
    			.queryParam("title", "war")
    			.queryParam("year", "1981")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, response.getStatus());
        response.close();
    }
    
    @Test
    public void testFindFilmsWithoutParam() {
    	response = target.path("films")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(404, response.getStatus());
        response.close();
    }
    
    @Test
    public void testFindFilmsWithWrongParam() {
    	response = target.path("films")
    			.queryParam("title", "bad")
    			.queryParam("year", "198")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(400, response.getStatus());
        response.close();
    }

    @Test
    public void testFindFilmsWithNoFilmsResult() {
    	response = target.path("films")
    			.queryParam("title", "elephant")
    			.queryParam("year", "1921")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(204, response.getStatus());
        response.close();
    }
    
    /* 
     * Tests sur l'affectation d'un film à une ou plusieurs salles
     */
    @Test
    public void testPost() throws SQLException {
    	Database db = Database.getInstance();
    	int count = 39;
    	String querytoTest = "SELECT COUNT(*) FROM film_salle";
    	
    	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
    			+ "<theaters imdbID=\"tt2294629\">"
    			+ "<theater>1</theater>"
    			+ "<theater>2</theater>"
    			+ "</theaters>";
    	
    	response = target
    			.path("films")
    			.path("theaters")
    			.request(MediaType.APPLICATION_XML)
    			.post(Entity.xml(xml));
    	ResultSet res = db.getQuery(querytoTest);
    	int countRes = 0;
    	if (res.next())
    		countRes = res.getInt(1);
    	
    	assertEquals(201, response.getStatus());
    	assertEquals(count + 2, countRes);
    	response.close();
    }
    
    /*
     * Tests sur l'obtention de la liste de salles affectées à un film
     */
    @Test
	public void testGetTheatersByFilmIdIncorrectId() {
		// Given
		String wrongID = "tt123456";
		
		// When
		response = target.path("films").path(wrongID).path("theaters")
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(404, response.getStatus());
		response.close();
	}
	
	@Test
	public void testGetTheatersByFilmIdNoResult() {
		// Given
		String goodIDNotInDb = "tt0034567";
		
		// When
		response = target.path("films").path(goodIDNotInDb).path("theaters")
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
		response = target.path("films").path(goodIDNotInDb).path("theaters")
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
		List<Theater> list = buildListForTheaterByFilmId();
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
		String restXml = target.path("films").path(id).path("theaters")
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
}
