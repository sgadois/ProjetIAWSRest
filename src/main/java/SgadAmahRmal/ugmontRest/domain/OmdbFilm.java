package SgadAmahRmal.ugmontRest.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Movie")
public class OmdbFilm {

	private String title;
	private String year;
	private String imdbID;
	private String type;
	
	public String getTitle() {
		return title;
	}
	
	@XmlAttribute(name = "Title")
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return year;
	}
	
	@XmlAttribute(name = "Year")
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getImdbID() {
		return imdbID;
	}
	
	@XmlAttribute(name = "imdbID")
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
	public String getType() {
		return type;
	}
	
	@XmlAttribute(name = "Type")
	public void setType(String type) {
		this.type = type;
	}
}
