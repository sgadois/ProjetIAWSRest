package SgadAmahRmal.ugmontRest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public String findFilms(String title, String year) {
    	String films = null;
    	
    	HttpClient client = new HttpClient();
    	GetMethod get = new GetMethod("http://www.omdbapi.com/");

    	if (title == null)
    		title = "";
    	if (year == null)
    		year = "";
    	String query = "?t=" + title + "&y=" + year + "&plot=short&r=xml";
    	get.setQueryString(query);
    	try {
			if (client.executeMethod(get) != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + get.getStatusLine());
			}
			else {
				films = get.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	get.releaseConnection();
    	return films;
    }
}
