package SgadAmahRmal.ugmontRest;

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

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
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
		response = target.path("theaters").path(wrongID)
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
		response = target.path("theaters").path(goodIDNotInDb)
				.request(MediaType.APPLICATION_XML).get();
		
		// Then
		assertEquals(204, response.getStatus());
		response.close();
	}
	
	@Test
	public void testGetTheatersByFilmIdResult() throws IOException {
		// Given
		String id = "tt0164354";
		ITheaterDao dao = Mockito.mock(ITheaterDao.class);
		
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
		when(dao.findByFilmId(id)).thenReturn(list);
		
		String xml = "";
		String line;
		BufferedReader file = new BufferedReader(new FileReader("src/main/resources/testGetTheatersByFilmIdResult.xml"));
		while((line = file.readLine()) != null) {
			xml += line;
		}
		file.close();
		
		// When
		String restXml = target.path("theaters").path(id)
				.request(MediaType.APPLICATION_XML).get(String.class);
		
		// Then
		assertEquals(xml, restXml);
	}
	
	
}
