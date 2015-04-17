package SgadAmahRmal.ugmontRest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "filmtheaters")
public class FilmTheater {

	private String imdbID;
	private List<String> theaterIds;
	
	public FilmTheater() {
		theaterIds = new ArrayList<String>();
	}

	@XmlAttribute
	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	@XmlElement(name = "theater")
	public List<String> getTheaterIds() {
		return theaterIds;
	}

	public void setTheaterIds(List<String> theaterIds) {
		this.theaterIds = theaterIds;
	}
	
}
