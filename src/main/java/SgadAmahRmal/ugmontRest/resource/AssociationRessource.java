package SgadAmahRmal.ugmontRest.resource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.domain.Theater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    @POST
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
        dao.filmTheater(filmId, theaterId);
        output = "<succes>The storage was successfully </succes>";
        return Response
                .ok(output, MediaType.APPLICATION_XML)
                .build();
    }

    private boolean isValideFilmId(String filmId) {
        boolean resultat = false;
        Response response1 = null;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.omdbapi.com/")
                .queryParam("i", filmId)
                .queryParam("r", "xml");

        response = target.request(MediaType.APPLICATION_XML).get();
        response1 = target.request(MediaType.APPLICATION_XML).get();
        BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)response1.getEntity()));
        String line = null;
        try {

            br.mark(response1.getLength() + 1);
            while(((line = br.readLine()) != null) && !resultat) {
                resultat = line.contains("response=\"False\"");
            }
            br.reset();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return !resultat;
    }

    private boolean isValideSalleId(String theaterId) {
        Theater theaterList = dao.find(theaterId);
        if (theaterList == null) {
            return false;
        }
        return true;
    }
    
}
