package SgadAmahRmal.ugmontRest.domain;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.linking.InjectLink;

import SgadAmahRmal.ugmontRest.resource.TheatersResource;

@XmlRootElement()
public class Film {

	@InjectLink(
			resource = TheatersResource.class,
			method = "getTheatersByFilmId",
			style = InjectLink.Style.ABSOLUTE)
	@XmlAttribute
	URI href;

	private String imdbID;

	@XmlAttribute
	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

}
