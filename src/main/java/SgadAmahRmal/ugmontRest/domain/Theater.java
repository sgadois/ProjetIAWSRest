package SgadAmahRmal.ugmontRest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Theater {
	
	private String id;
	private String name;
	private String city;
	private String zipcode;
	private String region;
	private List<Film> films;
	
	public Theater() {
		films = new ArrayList<Film>();
	}

	@XmlAttribute
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}

	@XmlElementWrapper
	@XmlElement(name = "film", nillable = true)
	public List<Film> getFilms() {
		if (films.isEmpty())
			return null;
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}
	
	
}
