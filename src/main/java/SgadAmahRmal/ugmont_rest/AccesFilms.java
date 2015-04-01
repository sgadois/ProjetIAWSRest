package SgadAmahRmal.ugmont_rest;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.NodeList;


import javax.xml.xpath.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by mahamat on 27/03/15.
 */
public class AccesFilms {
    private GestionBase gb;

    public AccesFilms(GestionBase gb) {
        this.gb = gb;
    }

    public void searchById(String id) {
        test(id, null, null, "short", 3);
    }

    public void searchByTitle(String title) {
        test(null, title, "", "short", 3);
    }

    public void searchByTitle(String title, String annee) {
        test(null, title, annee, "short", 4);
    }

    private void test(String id, String titre, String annee, String plot, int nbElem) {

        HttpClient client = new HttpClient();

        //GetMethod get  = new GetMethod("http://www.omdbapi.com/?t=frozen&y=&plot=short&r=xml");
        GetMethod get  = new GetMethod("http://www.omdbapi.com/");
        /*NameValuePair[] data = {
                new NameValuePair("t", "frozen"),
                new NameValuePair("plot", "short"),
                new NameValuePair("r", "xml")
        };*/

        NameValuePair[] data = new NameValuePair[nbElem];
        switch (nbElem) {
            case 4:{
                data[0] = new NameValuePair("t", titre);
                data[1] = new NameValuePair("y", annee);
                data[2] = new NameValuePair("plot", plot);
                data[3] = new NameValuePair("r", "xml");
                break;
            }
            case 3: {
                data[0] = new NameValuePair("i", id);
                data[1] = new NameValuePair("plot", plot);
                data[2] = new NameValuePair("r", "xml");
                break;
            }
        }
                get.setQueryString(data);
        // execute method and handle any error responses.
        try {
            int statusCode = client.executeMethod(get);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + get.getStatusLine());
            }

            // Read the response body.
            File xmlFile = new File("http://www.omdbapi.com/?t=frozen&y=&plot=short&r=xml");
            System.out.println("fileName = "+xmlFile.getName());
           // URL url = new URL("http://www.omdbapi.com/?t=frozen&y=&plot=short&r=xml");
            InputStream in = get.getResponseBodyAsStream();


            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            //chargement_xml(rd);
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
                System.out.println(line);
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            // Release the connection.
            get.releaseConnection();
        }
        // handle response.
    }

    private void chargement_xml(BufferedReader input) {
        try {
            SAXBuilder sxb = new SAXBuilder();
            sxb.setValidation(true);
            Document document = sxb.build(input);
            Element racine = document.getRootElement();

            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            List<NameValuePair> list = getAttributs(document, xpath, "");
            String nomAttr = "" , nomValues = "";
            if (!list.isEmpty()) {
                nomAttr = list.get(0).getName();
                nomValues = list.get(0).getValue();
                list.remove(0);
            }

            for(NameValuePair nvp : list) {
               nomAttr += ", " + nvp.getName();
                nomValues += ", " + nvp.getValue();
            }

            String insert = "insert into film (" + nomAttr + ") values(" + nomValues + ")";
            gb.executeSql(insert);

        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<NameValuePair> getAttributs(Document doc, XPath xpath, String expression) {
        List<NameValuePair> list = new ArrayList<>();
        try {
            //create XPathExpression object
            XPathExpression expr = xpath.compile(expression);
            //evaluate expression result on XML document
            NodeList nl = (NodeList) xpath.evaluate("//Element1/@*", doc, XPathConstants.NODESET);
            int length = nl.getLength();
            for( int i=0; i<length; i++) {
                Attr attr = (Attr) nl.item(i);
                String name = attr.getName();
                String value = attr.getValue();
                list.add(new NameValuePair(name, value));
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }

}
