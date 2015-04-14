package SgadAmahRmal.ugmontRest.dao;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import SgadAmahRmal.ugmontRest.database.Param;
import SgadAmahRmal.ugmontRest.domain.Theater;

@Contract
public interface ITheaterDao {

    /**
     * Get a theater by it ID.
     *
     * @param id of the theater required
     * @return Theater or null if it does not exist
     */
    Theater find(String id);

    /**
     * Get a list of theater whose reference an imdbID film ID.
     *
     * @param imdbID film ID
     * @return List of Theater or null if no theater reference imbdID given
     */
    List<Theater> findByFilmId(String imdbID);

    /**
     * Get a list of theater whose respect a list of criteria .
     *
     * @param criteria list of criteria
     * @return list of theater or null if no theater for the criteria
     */
    public List<Theater> findTheatersByCriteria(List<Param> criteria);

    /**
     * Associate film and theater
     *
     * @param imdbID  title of film
     * @param theaterId  id of theater
     * @return success or fail message
     */
    public boolean saveFilmTheater(String imdbID, String theaterId);

}
