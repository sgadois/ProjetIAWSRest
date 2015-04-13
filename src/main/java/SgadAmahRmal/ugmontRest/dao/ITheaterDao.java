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
     * Save a new theater.
     *
     * @param theater to save
     */
    void store(Theater theater);

    /**
     * Get a list of theater whose respect a list of criteria .
     *
     * @param listCriteres list of criteria
     * @return list of theater or null if no theater for the criteria
     */
    public List<Theater> findTheatersByCriteria(List<Param> criteria);

    /**
     * Associate film and theater
     *
     * @param film_title  title of film
     * @param theater_id  id of theater
     * @return success or fail message
     */
    public void filmTheater(String film_title, String theater_id);

}
