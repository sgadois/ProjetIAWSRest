package SgadAmahRmal.ugmontRest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    @Produces(MediaType.APPLICATION_XML)
    public List<Theater> searchTheaters(
    		@QueryParam("dep") String departement,
    		@DefaultValue("") @QueryParam("city") String city,
    		@DefaultValue("") @QueryParam("name") String name,
    		@DefaultValue("") @QueryParam("cp") String zipcode) {
        
    	if ("".equals(departement) || departement == null)
    		throw new NotFoundException();
    	else if (!departement.matches("[0-9]{2}"))
    		throw new BadRequestException();
    	
    	List<Param> criteria = new ArrayList<Param>(4);
    	criteria.add(new Param("departement", departement, Param.TypeSearch.EQUAL));
    	if (!"".equals(city))
    		criteria.add(new Param("city", city.toUpperCase(), Param.TypeSearch.CONTAINS));
    	if (!"".equals(name))
    		criteria.add(new Param("name", name.toUpperCase(), Param.TypeSearch.CONTAINS));
    	if (!"".equals(zipcode))
    		criteria.add(new Param("zipcode", zipcode, Param.TypeSearch.EQUAL));
    	
    	return dao.findTheatersByCriteria(criteria);
    }
}
