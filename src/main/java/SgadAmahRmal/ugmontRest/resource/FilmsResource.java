package SgadAmahRmal.ugmontRest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
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
	 * @param title: partial or complete film title
	 * @return list of imdbID's film as application/xml 
	 * or 204 no content status code if no result 
	 */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("{title}")
    public List<Film> getFilmsByTitle(
    		@PathParam("title") String title) {
    	
    	WebTarget target = getTarget(title, "");
    	return getFilms(target);
    }
    
    /**
     * 
     * Method call omdb API
     * 
     * @param title: partial or complete film title
     * @param year: YYYY form
     * @return list of imdbID's film as application/xml 
	 * or 204 no content status code if no result
     */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("{title}/{year}")
    public List<Film> getFilmsByTitleAndYear(
    		@PathParam("title") String title, 
    		@PathParam("year") String year) {

    	if ( ! year.matches("[0-9]{4}")) {
    		throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
    	             .entity("Year must be composed of 4 digits").type(MediaType.TEXT_PLAIN).build());
    	}
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
    	Response response = target.request(MediaType.APPLICATION_XML).get();
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
