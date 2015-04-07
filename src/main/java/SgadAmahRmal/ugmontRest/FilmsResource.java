package SgadAmahRmal.ugmontRest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.domain.Film;
import SgadAmahRmal.ugmontRest.domain.OmdbFilm;

/**
 * films resource
 * 
 */
@Path("films")
public class FilmsResource {

	/**
	 * 
	 * Method call omdb API.
	 * 
	 * @param title: space are not allowed, use the "+" character
	 * @return list of imdbID's film as application/xml 
	 * or 204 no content status code if no result 
	 */
    @GET
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/{title : [a-zA-Z0-9+]+}")
    public List<Film> getFilmsByTitle(
    		@PathParam("title") String title) {
    	
    	WebTarget target = getTarget(title, "");
    	return getFilms(target);
    }
    
    /**
     * 
     * Method call omdb API
     * 
     * @param title: space are not allowed, use the "+" character
     * @param year: YYYY form
     * @return list of imdbID's film as application/xml 
	 * or 204 no content status code if no result
     */
    @GET
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/{title : [a-zA-Z0-9+]+}/{year : [0-9]{4}}")
    public List<Film> getFilmsByTitleAndYear(
    		@PathParam("title") String title, 
    		@PathParam("year") String year) {
    	
    	WebTarget target = getTarget(title, year);
    	return getFilms(target);
    }

    private WebTarget getTarget(String title, String year) {
    	Client client = ClientBuilder.newClient();
    	return client.target("http://www.omdbapi.com/")
    			.queryParam("s", title)
    			.queryParam("y", year)
    			.queryParam("type", "movie")
    			.queryParam("r", "xml");
    }

    private List<Film> getFilms(WebTarget target) {
    	List<Film> films = null;
    	OmdbFilm omdbFilms = null;
    	Response response = target.request(MediaType.TEXT_XML).get();
    	if (response.getStatus() == 200) {
    		omdbFilms = response.readEntity(OmdbFilm.class);
			if ("True".equals(omdbFilms.getResponse())) {
	    		films = new ArrayList<Film>();
				for (Film omdbFilm : omdbFilms.getMovies()) {
					Film film = new Film();
					film.setImdbID(omdbFilm.getImdbID());
					films.add(film);
				}
			}
    	}
    	return films;
    }
}