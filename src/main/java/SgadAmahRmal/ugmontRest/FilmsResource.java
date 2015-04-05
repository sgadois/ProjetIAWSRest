package SgadAmahRmal.ugmontRest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.domain.Film;
import SgadAmahRmal.ugmontRest.domain.OmdbFilm;

/**
 * films resource
 * 
 */
@Path("films/{title : [a-zA-Z0-9+]+}/{year : [0-9]*}")
public class FilmsResource {

	/**
	 * 
	 * Method call omdb api and return the result as application/xml
	 * 
	 * @param title
	 * @param year
	 * @return as application/xml a list of imdbID's film
	 */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public List<Film> getFilms(
    		@PathParam("title") String title, 
    		@DefaultValue("") @PathParam("year") String year) {
    	
    	List<Film> films = new ArrayList<Film>();
    	
    	Client client = ClientBuilder.newClient();
    	WebTarget target = client.target("http://www.omdbapi.com/")
    			.queryParam("s", title)
    			.queryParam("y", year)
    			.queryParam("type", "movie")
    			.queryParam("r", "xml");
    	
    	List<OmdbFilm> omdbFilms = null;
    	GenericType<List<OmdbFilm>> generic = new GenericType<List<OmdbFilm>>() {};
      
    	Response response = target.request(MediaType.APPLICATION_XML).get();
    	if (response.getStatus() == 200) {
    		omdbFilms = response.readEntity(generic);
    		for (OmdbFilm omdbFilm : omdbFilms) {
    			Film film = new Film();
				film.setImdbID(omdbFilm.getImdbID());
				films.add(film);
			}
    	}
    	return films;
    }
}
