package SgadAmahRmal.ugmontRest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import SgadAmahRmal.ugmontRest.database.Database;
import SgadAmahRmal.ugmontRest.domain.Theater;

@Service
public class TheaterDaoH2Db implements ITheaterDao {

	private Database db;

	@Inject
	public TheaterDaoH2Db(Database db) {
		this.db = db;
	}
	
	@Override
	public Theater find(String id) {
		ResultSet result = db.getQuery("SELECT * FROM salle WHERE id=" + id);
		try {
			if (result.next()) {
				Theater theater = new Theater();
				theater.setId(result.getString("id"));
				theater.setName(result.getString("name"));
				theater.setCity(result.getString("city"));
				theater.setZipcode(result.getString("zipcode"));
				theater.setRegion(result.getString("departement"));
				return theater;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Theater> findByFilmId(String imdbID) {
		ResultSet result = db.getQuery(
				"SELECT FS.salle_id, S.id, S.name, S.city, S.zipcode, S.departement "
				+ "FROM film_salle FS, salle S "
				+ "WHERE film_id = '" + imdbID + "' "
						+ "AND FS.salle_id = S.id "
						+ "ORDER BY S.name ASC");
		List<Theater> theaters = new ArrayList<Theater>();
		try {
			while(result.next()) {
				Theater theater = new Theater();
				theater.setId(result.getString("id"));
				theater.setName(result.getString("name"));
				theater.setCity(result.getString("city"));
				theater.setZipcode(result.getString("zipcode"));
				theater.setRegion(result.getString("departement"));
				theaters.add(theater);
			}
			if ( ! theaters.isEmpty())
				return theaters;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void store(Theater theater) {
		// TODO Auto-generated method stub
		
	}

}
