package SgadAmahRmal.ugmontRest.resource;

import SgadAmahRmal.ugmontRest.Main;
import SgadAmahRmal.ugmontRest.dao.ITheaterDao;
import SgadAmahRmal.ugmontRest.domain.OmdbFilm;
import SgadAmahRmal.ugmontRest.domain.Theater;
import junit.framework.TestCase;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AssociationRessourceTest extends TestCase {
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testStoreFilmTheaterOk() {

        // Given
        String film_id_ok = "tt0082503";
        String theater_id_ok = "15";
        ITheaterDao dao = Mockito.mock(ITheaterDao.class);

        when(dao.saveFilmTheater(film_id_ok, theater_id_ok)).thenReturn(true);

        String xmlString = "<success>The storage was successfully </success>";


        // When
        Response reponse = target.path("associate").path(film_id_ok).path(theater_id_ok)
                .request(MediaType.APPLICATION_XML).put(Entity.xml(xmlString));
        final String rest = reponse.readEntity(String.class);

        // Then
        assertEquals(xmlString, rest);
    }

    @Test
    public void testStorageErrorFilmTheater() {
        // Given
        String film_id_ok = "tt0082503";
        String theater_id_ok = "1";
        ITheaterDao dao = Mockito.mock(ITheaterDao.class);

        when(dao.saveFilmTheater(film_id_ok, theater_id_ok)).thenReturn(false);

        String xmlString = "<error>Storage error</error>";

        // When
        Response reponse = target.path("associate").path(film_id_ok).path(theater_id_ok)
                .request(MediaType.APPLICATION_XML).put(Entity.xml(xmlString));

        final String rest = reponse.readEntity(String.class);
        // Then
        assertEquals(xmlString, rest);
    }

    @Test
    public void testStoreFilmTheaterNoOk() {
        // Given
        String film_id_ok = "56";
        String theater_id_ok = "1";

        String xmlString = "<error> filmId ou theaterId invalid </error>";

        // When
        Response reponse = target.path("associate").path(film_id_ok).path(theater_id_ok)
                .request(MediaType.APPLICATION_XML).put(Entity.xml(xmlString));

        final String rest = reponse.readEntity(String.class);
        // Then
        assertEquals(xmlString, rest);
    }
}