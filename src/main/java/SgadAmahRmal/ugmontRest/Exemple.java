package SgadAmahRmal.ugmontRest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import SgadAmahRmal.ugmontRest.domain.Film;
import SgadAmahRmal.ugmontRest.domain.Theater;

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
    public List<Theater> getFilms() {
    	ArrayList<Film> films = new ArrayList<Film>();
    	Film first = new Film();
    	first.setImdbID("15kj");
    	Film second = new Film();
    	second.setImdbID("98op");
    	films.add(first);
    	films.add(second);
    	
    	ArrayList<Theater> salles = new ArrayList<Theater>();
    	Theater one = new Theater();
    	one.setCity("city1");
    	one.setId("01");
    	one.setName("name1");
    	one.setRegion("region1");
    	one.setZipcode("11 111");
    	one.setFilms(films);
    	salles.add(one);
    	
    	Theater two = new Theater();
    	two.setCity("city2");
    	two.setId("02");
    	two.setName("name2");
    	two.setRegion("region2");
    	two.setZipcode("22 222");
    	salles.add(two);
    	
    	return salles;
    }
}
