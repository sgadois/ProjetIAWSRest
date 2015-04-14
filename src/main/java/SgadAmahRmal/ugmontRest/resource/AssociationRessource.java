package SgadAmahRmal.ugmontRest.resource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
     * @param filmId: an imdbID
     * @param theaterId: a theater id
     * @return a response:
     * 		201 creation success
     * 		200 association already exist
     * 		404 invalid filmId or theaterId
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{filmId}/{theaterId}")
    public Response storeFilmTheater(@PathParam("filmId") String filmId,
                                   @PathParam("theaterId") String theaterId) {
        
        if (!isValideFilmId(filmId) || dao.find(theaterId) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (!dao.saveFilmTheater(filmId, theaterId))
        	return Response.status(Response.Status.OK).build();
        else
        	return Response.status(Response.Status.CREATED).build();
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
    
}
