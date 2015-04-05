package SgadAmahRmal.ugmontRest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import SgadAmahRmal.ugmontRest.domain.Film;
import SgadAmahRmal.ugmontRest.domain.Salle;

/**
 * This is an example !
 * getting a list of salles and return it as XML
 * Look at ExampleTest to see xml result.
 * 
 * @author Romain
 *
 */
@Path("movies")
public class Exemple {

	@GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Salle> getFilms() {
    	ArrayList<Film> films = new ArrayList<Film>();
    	Film first = new Film();
    	first.setImdbID("15kj");
    	Film second = new Film();
    	second.setImdbID("98op");
    	films.add(first);
    	films.add(second);
    	
    	ArrayList<Salle> salles = new ArrayList<Salle>();
    	Salle one = new Salle();
    	one.setCity("city1");
    	one.setFilms(films);
    	one.setId("01");
    	one.setName("name1");
    	one.setRegion("region1");
    	one.setZipcode("11 111");
    	salles.add(one);
    	
    	Salle two = new Salle();
    	two.setCity("city2");
    	two.setFilms(films);
    	two.setId("02");
    	two.setName("name2");
    	two.setRegion("region2");
    	two.setZipcode("22 222");
    	salles.add(two);
    	
    	return salles;
    }
}
