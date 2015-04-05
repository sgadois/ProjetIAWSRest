package SgadAmahRmal.ugmontRest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExempleTest {

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
	public void test() {
		String responseMsg = target.path("movies").request(MediaType.APPLICATION_XML).get(String.class);
        String xml = ""
        		+ "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        			+ "<salles>"
        				+ "<salle id=\"01\">"
        					+ "<city>city1</city>"
        					+ "<films>"
        						+ "<film imdbID=\"15kj\"/>"
        						+ "<film imdbID=\"98op\"/>"
        					+ "</films>"
        					+ "<name>name1</name>"
        					+ "<region>region1</region>"
        					+ "<zipcode>11 111</zipcode>"
        				+ "</salle>"
        				+ "<salle id=\"02\">"
        					+ "<city>city2</city>"
        					+ "<films>"
        						+ "<film imdbID=\"15kj\"/>"
        						+ "<film imdbID=\"98op\"/>"
        					+ "</films>"
        					+ "<name>name2</name>"
        					+ "<region>region2</region>"
        					+ "<zipcode>22 222</zipcode>"
        				+ "</salle>"
        			+ "</salles>";
        assertEquals(xml, responseMsg);
	}

}
