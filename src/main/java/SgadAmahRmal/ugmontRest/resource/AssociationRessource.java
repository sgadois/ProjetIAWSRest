package SgadAmahRmal.ugmontRest.resource;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.database.Tuple;
import SgadAmahRmal.ugmontRest.domain.Theater;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * films resource
 */
@Path("associate")
public class AssociationRessource {

    @Inject
    private ITheaterDao dao;
    private Response response = null;

    /**
     * @param filmId    film id
     * @param theaterId theater id
     * @return a xml balise succes or fail
     */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("{filmId}/{theaterId}")
    public Response storeFilmTheater(@PathParam("filmId") String filmId,
                                   @PathParam("theaterId") String theaterId) {
        if (!isValideFilmId(filmId) || !isValideSalleId(theaterId)) {
            System.out.println(response.getStatus());
            return response;
        }
        System.out.println(filmId + ", " + response.getStatus() + " , " + theaterId );
        dao.filmTheater(filmId, theaterId);

        return response;
    }

    private boolean isValideFilmId(String filmId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.omdbapi.com/")
                .queryParam("i", filmId)
                .queryParam("r", "xml");

        response = target.request(MediaType.APPLICATION_XML).get();

        return (response.getStatus() == 200);
    }

    private boolean isValideSalleId(String theaterId) {
        Tuple<String, String>[] listCriteres = new Tuple[1];
        listCriteres[0] = new Tuple<>("id", theaterId);
        List<Theater> theaterList = dao.findTheatersByFilmAny(listCriteres);
        if (theaterList == null || theaterList.isEmpty()) {
            return false;
        }
        return true;
    }
}
