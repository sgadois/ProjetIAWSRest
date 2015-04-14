package SgadAmahRmal.ugmontRest.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.domain.OmdbFilm;

/**
 * films resource
 */
@Path("associate")
public class AssociationRessource {

    @Inject
    private ITheaterDao dao;

    /**
     * @param filmId    film id
     * @param theaterId theater id
     * @return a xml marker success or fail
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("{filmId}/{theaterId}")
    public Response storeFilmTheater(@PathParam("filmId") String filmId,
                                   @PathParam("theaterId") String theaterId) {
        String output;
        if (!isValideFilmId(filmId) || !isValideSalleId(theaterId)) {
            output = "<error> filmId ou theaterId invalid </error>";
            return Response
                    .ok(output, MediaType.APPLICATION_XML)
                    .build();
        }
        if (!dao.saveFilmTheater(filmId, theaterId))
        	output = "<error>Storage error</error>";
        else
        	output = "<success>The storage was successfully </success>";
        return Response
                .ok(output, MediaType.APPLICATION_XML)
                .build();
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

    private boolean isValideSalleId(String theaterId) {
        if (dao.find(theaterId) == null) {
            return false;
        }
        return true;
    }
    
}
