package SgadAmahRmal.ugmontRest.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.database.Tuple;
import SgadAmahRmal.ugmontRest.domain.Theater;

/**
 * theaters resource
 *
 */
@Path("theaters")
public class TheatersResource {

	@Inject
	private ITheaterDao dao;
	
	/*
	 * Note that the name of this method is used as a String in the 
	 * Film domain class. Must be renamed too in case of change
	 */
	/**
	 * Get a list of theaters associated to a film
	 * 
	 * @param filmID
	 * @return a list of theater as application/xml
	 * or 204 no content status code if no result
	 */
	@GET
	@Path("films/{imdbID}")
	@Produces(MediaType.APPLICATION_XML)
	public List<Theater> getTheatersByFilmId(
			@PathParam("imdbID") String imdbID) {

		if ( ! imdbID.matches("tt[0-9]{7}")) {
    		throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query param does not match imdb film id form : tt + 7 digits").type(MediaType.TEXT_PLAIN).build());
    	}
		return dao.findByFilmId(imdbID);
	}

    @GET
    @Path("search/{listOfCriteria}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Theater> getTheatersByFilmAny(
            @PathParam("listOfCriteria") String listOfCriteria) {
        String A = "[a-zA-Z ]+=[a-zA-Z ]+";
        if ( ! listOfCriteria.matches(A+"(&"+A+")*")) {
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query param does not match imdb film id form : tt + 7 digits").type(MediaType.TEXT_PLAIN).build());
        }


        String [] criteria = listOfCriteria.split("&");
        Tuple<String, String>[] listCriteres = new Tuple[criteria.length];
        for (int i = 0; i < criteria.length ; i++) {
            String [] criterium = criteria[i].split("=");
            listCriteres[i] = new Tuple<>(criterium[0], "'" + criterium[1] + "'");
            //System.out.println(criterium[0]+" "+ criterium[1]);
        }
        return dao.findTheatersByFilmAny(listCriteres);
    }

    @GET
    @Path("{imdbID}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Theater> getTheaterByFilmId(
            @PathParam("imdbID") String imdbID) {
        return  dao.findByFilmId(imdbID);
    }
}
