package SgadAmahRmal.ugmontRest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilmResourceTest {

    private HttpServer server;
    private WebTarget target;

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
    public void testFindFilmsOkNoYear() {
    	Response responseMsg = 
    			target.path("films").path("there+will+be+blood/")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, responseMsg.getStatus());
    }
    
    @Test
    public void testFindFilmsOkWithYear() {
    	Response responseMsg = 
    			target.path("films").path("war/1981")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, responseMsg.getStatus());
    }
    
    @Test
    public void testFindFilmsWithoutParam() {
    	Response responseMsg = 
    			target.path("films")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(404, responseMsg.getStatus());
    }
    
    @Test
    public void testFindFilmsWithWrongParam() {
    	Response responseMsg = 
    			target.path("films").path("bad")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(404, responseMsg.getStatus());
    }
}
