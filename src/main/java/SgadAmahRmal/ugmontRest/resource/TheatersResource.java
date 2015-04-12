package SgadAmahRmal.ugmontRest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.database.Param;
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

	/**
	 * Search theaters with criteria
	 * 
	 * @param departement: two digits required
	 * @param city: city name
	 * @param name: theater name
	 * @param zipcode: five digits
	 * @return a list of theater as application/xml
	 * or 204 no content status code if no result
	 */
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_XML)
    public List<Theater> searchTheaters(
    		@QueryParam("departement") String departement,
    		@DefaultValue("") @QueryParam("city") String city,
    		@DefaultValue("") @QueryParam("name") String name,
    		@DefaultValue("") @QueryParam("zipcode") String zipcode) {
        
    	if ("".equals(departement) || !departement.matches("[0-9]{2}")) {
    		throw new BadRequestException();
    	}
    	
    	List<Param> criteria = new ArrayList<Param>(4);
    	criteria.add(new Param("departement", departement, Param.TypeSearch.EQUAL));
    	if (!"".equals(city))
    		criteria.add(new Param("city", city, Param.TypeSearch.CONTAINS));
    	if (!"".equals(name))
    		criteria.add(new Param("name", name, Param.TypeSearch.CONTAINS));
    	if (!"".equals(zipcode))
    		criteria.add(new Param("zipcode", zipcode, Param.TypeSearch.EQUAL));
    	
    	return dao.findTheatersByCriteria(criteria);
    }

    @GET
    @Path("{imdbID}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Theater> getTheaterByFilmId(
            @PathParam("imdbID") String imdbID) {
        return  dao.findByFilmId(imdbID);
    }
}
