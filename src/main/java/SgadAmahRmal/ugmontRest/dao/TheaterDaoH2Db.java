package SgadAmahRmal.ugmontRest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import SgadAmahRmal.ugmontRest.database.Tuple;
import SgadAmahRmal.ugmontRest.domain.Film;
import SgadAmahRmal.ugmontRest.resource.FilmsResource;
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
            while (result.next()) {
                Theater theater = new Theater();
                theater.setId(result.getString("id"));
                theater.setName(result.getString("name"));
                theater.setCity(result.getString("city"));
                theater.setZipcode(result.getString("zipcode"));
                theater.setRegion(result.getString("departement"));
                theaters.add(theater);
            }
            if (!theaters.isEmpty())
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

    @Override
    public List<Theater> findTheatersByFilmAny(Tuple<String, String>[] listCriteres) {
        String req = "select  * from salle where " + listCriteres[0].getName() + " = " + listCriteres[0].getValue();

        for (int i = 1; i < listCriteres.length; i++) {
            req += " and " + listCriteres[i].getName() + " = " + listCriteres[i].getValue();
        }

        ResultSet resultSet = Database.getInstance().getQuery(req);
        List<Theater> listSalles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Theater theater = new Theater();
                theater.setId(resultSet.getString("id"));
                theater.setCity(resultSet.getString("city"));
                theater.setName(resultSet.getString("name"));
                theater.setRegion(resultSet.getString("departement"));
                theater.setZipcode(resultSet.getString("zipcode"));

                listSalles.add(theater);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listSalles;
    }

    public String filmTheater(String film_title, String theater_id) {
        FilmsResource filmsResource = new FilmsResource();
        List<Film> filmList = filmsResource.getFilmsByTitle(film_title);
        if (filmList == null || filmList.isEmpty()) {
            return "<fail>"+film_title + " film introuvable </fail>";
        }
        Tuple<String, String>[] listCriteres = new Tuple[1];
        listCriteres[0] = new Tuple<>("id", theater_id);
        List<Theater> theaterList = findTheatersByFilmAny(listCriteres);
        if (theaterList == null || theaterList.isEmpty()) {
            return "<fail>"+theater_id + " salle introuvable </fail>";
        }
        String req = "insert into film_salle(salle_id,film_id) values(" + theater_id + " , '" + filmList.get(0).getImdbID()+"')";
        Database.getInstance().executeSql(req);

        return "<succes> </succes>";
    }

}
