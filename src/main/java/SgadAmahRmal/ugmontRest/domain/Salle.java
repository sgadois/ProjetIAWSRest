package SgadAmahRmal.ugmontRest.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "salle")
public class Salle {
	
	private String id;
	private String name;
	private String city;
	private String zipcode;
	private String region;
	
	public Salle(String id, String name, String city, String zipcode,
			String region) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.zipcode = zipcode;
		this.region = region;
	}

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
	
	
}
