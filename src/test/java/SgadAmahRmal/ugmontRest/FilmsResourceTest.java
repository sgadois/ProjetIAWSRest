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
        response.close();
    	server.shutdownNow();
    }

    @Test
    public void testFindFilmsOkNoYear() {
    	response = target.path("films").path("there+will+be+blood")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testFindFilmsOkWithYear() {
    	response = target.path("films").path("war").path("1981")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testFindFilmsWithoutParam() {
    	response = target.path("films")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(404, response.getStatus());
    }
    
    @Test
    public void testFindFilmsWithWrongParam() {
    	response = target.path("films").path("bad").path("198")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testFindFilmsWithNoFilmsResult() {
    	response = target.path("films").path("elephant").path("1921")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(204, response.getStatus());
    }
}
