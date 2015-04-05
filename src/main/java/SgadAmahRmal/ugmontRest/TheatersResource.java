package SgadAmahRmal.ugmontRest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import SgadAmahRmal.ugmontRest.domain.Theater;

/**
 * theaters resource
 *
 */
@Path("theaters")
public class TheatersResource {

	/*
	 * Note that the name of this method is used as a String in the 
	 * Film domain class. Must be renamed too in case of change
	 */
	/**
	 * 
	 * @param filmID
	 * @return
	 */
	@GET
	@Path("/{imdbID}")
	@Produces(MediaType.APPLICATION_XML)
	public List<Theater> getTheatersByFilmId(@PathParam("imdbID") String filmID) {
		// TODO implement it
		return null;
	}
}
