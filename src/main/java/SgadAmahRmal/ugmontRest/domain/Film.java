package SgadAmahRmal.ugmontRest.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Movie")
public class Film {

	private String title;
	private String year;
	private String imdbID;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getImdbID() {
		return imdbID;
	}
	
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
}
