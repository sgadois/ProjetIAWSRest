package SgadAmahRmal.ugmontRest.resource;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import SgadAmahRmal.ugmontRest.Main;
import SgadAmahRmal.ugmontRest.database.Database;

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
    	response = target.path("films")
    			.queryParam("title", "there+will+be+blood")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testFindFilmsOkWithYear() {
    	response = target.path("films")
    			.queryParam("title", "war")
    			.queryParam("year", "1981")
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
    	response = target.path("films")
    			.queryParam("title", "bad")
    			.queryParam("year", "198")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testFindFilmsWithNoFilmsResult() {
    	response = target.path("films")
    			.queryParam("title", "elephant")
    			.queryParam("year", "1921")
    			.request(MediaType.APPLICATION_XML).get();
        assertEquals(204, response.getStatus());
    }
    
    @Test
    public void testPost() throws SQLException {
    	Database db = Database.getInstance();
    	int count = 39;
    	String querytoTest = "SELECT COUNT(*) FROM film_salle";
    	
    	List<String> ids = new ArrayList<String>(2);
    	ids.add("1");
    	ids.add("2");
    	Form form = new Form();
    	form.param("imdbID", "tt2294629"); // imdb Frozen
    	MultivaluedMap<String, String> map = form.asMap();
    	map.addAll("theaterList", ids);
    	
    	response = target
    			.path("films")
    			.path("theaters")
    			.request(MediaType.APPLICATION_FORM_URLENCODED)
    			.post(Entity.form(form));
    	ResultSet res = db.getQuery(querytoTest);
    	int countRes = 0;
    	if (res.next())
    		countRes = res.getInt(1);
    	
    	assertEquals(count + 2, countRes);
    }
}
