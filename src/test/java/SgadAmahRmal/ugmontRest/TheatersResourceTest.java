package SgadAmahRmal.ugmontRest;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void testGetTheatersByFilmIdBadRequest() {
		response = target.path("theaters").path("tt123456")
				.request(MediaType.APPLICATION_XML).get();
		assertEquals(400, response.getStatus());
		response.close();
	}
	
	
}
