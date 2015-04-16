package SgadAmahRmal.ugmontRest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.domain.Film;
import SgadAmahRmal.ugmontRest.domain.OmdbFilm;
import SgadAmahRmal.ugmontRest.domain.Theater;

/**
 * films resource
 */
@Path("films")
public class FilmsResource {
    
	@Inject
    private ITheaterDao dao;

    /**
     * Search a film by title and optionally by year.
     *
     * @param title: partial or complete film title
     * @param year:  YYYY form
     * @return list of imdbID's film as application/xml
     * or 204 no content status code if no result
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Film> searchFilms(
            @QueryParam("title") String title,
            @DefaultValue("") @QueryParam("year") String year) {

    	if ("".equals(title) || title == null)
    		throw new NotFoundException();
    	if (!"".equals(year) && !year.matches("[0-9]{4}")) {
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Year must be composed of 4 digits").type(MediaType.TEXT_PLAIN).build());
        }
    	
    	List<Film> films = null;
        OmdbFilm omdbFilms = null;
    	Client client = ClientBuilder.newClient();
    	Response response = client.target("http://www.omdbapi.com/")
                .queryParam("s", title)
                .queryParam("y", year)
                .queryParam("type", "movie")
                .queryParam("r", "xml")
                .request(MediaType.APPLICATION_XML).get();
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

    /**
     * @param film: an imdbID
     * @param theaters: a theater id
     * @return a response:
     * 201 creation success
     * 200 association already exist
     * 404 invalid filmId or theaterId
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("theaters")
    public Response storeFilmTheater(
            @FormParam("imdbID") String filmId,
            @FormParam("theaterList") List<String> theatersIds,
            @Context UriInfo uri) {
    	
        Response responseNotFound = null;
        Response responseOk = null;
        Response responseCreated = null;
        if (isValideFilmId(filmId)) {
            for (String theater : theatersIds) {
                if (dao.find(theater) == null && responseNotFound == null) {
                    responseNotFound = Response.status(Response.Status.NOT_FOUND).build();
                }
                else if (!dao.saveFilmTheater(filmId, theater) && responseOk == null)
                    responseOk = Response.status(Response.Status.OK).build();
                else {
                	// TODO find a solution less dependente on the context
                	UriBuilder uriBuilder = uri.getAbsolutePathBuilder().replacePath("myapp/films");
                	URI locationHeader = uriBuilder.path(filmId).path("theaters").build();
                    responseCreated = Response.created(locationHeader).build();
                }
            }
        }
        if (responseCreated != null)
            return responseCreated;
        else if (responseOk != null)
            return responseOk;
        return responseNotFound;
    }

    private boolean isValideFilmId(String filmId) {
        OmdbFilm omdbFilms = null;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.omdbapi.com/")
                .queryParam("i", filmId)
                .queryParam("r", "xml");
        Response response1 = target.request(MediaType.APPLICATION_XML).get();
        if (response1.getStatus() == 200) {
            omdbFilms = response1.readEntity(OmdbFilm.class);
            if ("True".equals(omdbFilms.getResponse()))
                return true;
        }
        return false;
    }
    
    /*
	 * Note that the name of this method is used as a String in the 
	 * Film domain class. Must be renamed too in case of change
	 */
	/**
	 * Get a list of theaters associated to an imdb film ID
	 * 
	 * @param filmID
	 * @return a list of theater as application/xml
	 * or 204 no content status code if no result
	 */
	@GET
	@Path("{imdbID : tt[0-9]{7}}/theaters")
	@Produces(MediaType.APPLICATION_XML)
	public List<Theater> getTheatersByFilmId(
			@PathParam("imdbID") String imdbID) {

		return dao.findByFilmId(imdbID);
	}
}
