package SgadAmahRmal.ugmontRest;

import SgadAmahRmal.ugmontRest.domain.Tuple;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in SgadAmahRmal.ugmontRest package
        final ResourceConfig rc = new ResourceConfig()
        		.packages("SgadAmahRmal.ugmontRest")
        		.register(DeclarativeLinkingFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        TheatersResource theatersResource = new TheatersResource();
        Tuple<String, String>[] listCriteres =  new Tuple[2];
        listCriteres[0] = new Tuple<String,String>("id", "9");
        listCriteres[1] = new Tuple<String,String>("city", "'La Rochelle'");
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}

