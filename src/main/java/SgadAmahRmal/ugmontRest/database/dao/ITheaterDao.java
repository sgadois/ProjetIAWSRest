package SgadAmahRmal.ugmontRest.database.dao;

import java.util.List;

import SgadAmahRmal.ugmontRest.domain.Theater;

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
	
	// May be some others methods...
}
