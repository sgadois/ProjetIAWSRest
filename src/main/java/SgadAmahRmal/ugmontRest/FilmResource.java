package SgadAmahRmal.ugmontRest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * films resource
 */
@Path("films/{title : [a-zA-Z0-9+]+}/{year : [0-9]*}")
public class FilmResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/xml" media type.
     *
     * @return String that will be returned as a text/xml response.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String findFilms(
    		@PathParam("title") String title, 
    		@DefaultValue("") @PathParam("year") String year) {
    	
    	String films = null;
    	
    	Client client = ClientBuilder.newClient();
    	WebTarget target = client.target("http://www.omdbapi.com/?s=" + title + "&type=movie&y=" + year + "&r=xml");
    	
    	Response response = target.request(MediaType.APPLICATION_XML).get();
    	if (response.getStatus() == 200) {
    		films = response.readEntity(String.class);
    	}
    	return films;
    }
    
    
}
