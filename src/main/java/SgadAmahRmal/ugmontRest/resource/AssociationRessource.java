package SgadAmahRmal.ugmontRest.resource;

import SgadAmahRmal.ugmontRest.dao.ITheaterDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * films resource
 *
 */
@Path("associate")
public class AssociationRessource {

    @Inject
    private ITheaterDao dao;
    /**
     *
     * Method call omdb API.
     *
     * @param title: partial or complete film title
     * @return list of imdbID's film as application/xml
     * or 204 no content status code if no result
     */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("{title}&{id}")
    public  String associate(@PathParam("title") String title,
                             @PathParam("id") String id) {
        System.out.println(title+" "+ id);
        return dao.filmTheater(title,id);
    }
}
