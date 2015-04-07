package SgadAmahRmal.ugmontRest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.database.Database;
import SgadAmahRmal.ugmontRest.database.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.domain.Theater;
import SgadAmahRmal.ugmontRest.domain.Tuple;

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
	 * 
	 * @param filmID
	 * @return
	 */
	@GET
	@Path("/{imdbID}")
	@Produces(MediaType.APPLICATION_XML)
	public List<Theater> getTheatersByFilmId(
			@PathParam("imdbID") String imdbID) {
		
		if ( ! imdbID.matches("tt[0-9]{7}")) {
    		throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
    	             .entity("Now I don't known what can I explain you").type(MediaType.TEXT_PLAIN).build());
    	}
		return dao.findByFilmId(imdbID);
	}

	
    public List<Theater> getTheatersByFilmAny(Tuple<String, String>[] listCriteres) {
    	String req = "select  * from salle where " + listCriteres[0].getName() + " = " +  listCriteres[0].getValue();

        for(int i = 1; i < listCriteres.length; i++) {
            req += " and " + listCriteres[i].getName() + " = " +  listCriteres[i].getValue();
        }

        ResultSet resultSet = Database.getInstance().getQuery(req);
        List<Theater> listSalles = new ArrayList<>();
        try {
            while(resultSet.next()) {
                Theater theater = new Theater();
                theater.setId(resultSet.getString("id"));
                theater.setCity(resultSet.getString("city"));
                theater.setName(resultSet.getString("name"));
                theater.setRegion(resultSet.getString("departement"));
                theater.setZipcode(resultSet.getString("zipcode"));

                listSalles.add(theater);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  listSalles;
    }
}
