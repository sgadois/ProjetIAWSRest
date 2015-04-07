package SgadAmahRmal.ugmontRest.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import SgadAmahRmal.ugmontRest.database.Database;
import SgadAmahRmal.ugmontRest.domain.Theater;

public class TheaterDaoH2Db implements ITheaterDao {

	private Database db;
	
	public TheaterDaoH2Db(Database db) {
		this.db = db;
	}
	
	@Override
	public Theater find(String id) {
		ResultSet result = db.getQuery("select * from salle where id=" + id);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Theater theater) {
		// TODO Auto-generated method stub
		
	}

}
