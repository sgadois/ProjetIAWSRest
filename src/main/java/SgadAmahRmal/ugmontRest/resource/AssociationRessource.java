package SgadAmahRmal.ugmontRest.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.domain.Theater;

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
     * @return a xml balise succes or fail
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("{filmId}/{theaterId}")
    public String storeFilmTheater(@PathParam("filmId") String filmId,
                                   @PathParam("theaterId") String theaterId) {
        if (!isValideFilmId(filmId) || !isValideSalleId(theaterId)) {
            return "<fail>" + filmId + " ou " + theaterId + " film introuvable </fail>";
        }

        dao.filmTheater(filmId, theaterId);

        return "<succes> </succes>";
    }

    private boolean isValideFilmId(String filmId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.omdbapi.com/")
                .queryParam("i", filmId)
                .queryParam("r", "xml");

        Response response = target.request(MediaType.APPLICATION_XML).get();

        return (response.getStatus() == 200);
    }

    private boolean isValideSalleId(String theaterId) {
        if (dao.find(theaterId) == null) {
            return false;
        }
        return true;
    }
    
}
