package SgadAmahRmal.ugmontRest.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class OmdbFilm {

	private String response;
	private String error;
	private List<Film> movies = null;
		
	public String getResponse() {
		return response;
	}

	@XmlAttribute
	public void setResponse(String response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	@XmlElement(name = "error", nillable = true)
	public void setError(String error) {
		this.error = error;
	}

	public List<Film> getMovies() {
		return movies;
	}

	@XmlElement(name = "Movie", nillable = true)
	public void setMovies(List<Film> movies) {
		this.movies = movies;
	}
	
}
