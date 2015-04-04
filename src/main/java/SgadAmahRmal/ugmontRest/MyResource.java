package SgadAmahRmal.ugmontRest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource/{title : [a-zA-Z0-9]+}/{year : [0-9]*}")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public String findFilms(
    		@QueryParam("title") String title, 
    		@DefaultValue("") @QueryParam("year") String year) {
    	
    	String films = null;
    	title = title.replace(" ", "+");
    	
    	Client client = ClientBuilder.newClient();
    	WebTarget target = client.target("http://www.omdbapi.com/?s=" + title + "&type=movie&y=" + year + "&plot=short&r=xml");
    	
    	Response response = target.request(MediaType.TEXT_XML).get();
    	if (response.getStatus() == 200) {
    		films = response.readEntity(String.class);
    	}
    	return films;
    }
}
