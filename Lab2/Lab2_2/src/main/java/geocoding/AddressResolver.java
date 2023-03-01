/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoding;

import connection.ISimpleHttpClient;
import org.apache.http.ParseException;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;


/**
 * calls external api to perform the reverse geocoding
 *
 * @author ico
 */
public class AddressResolver {
    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String MAPQUESTAPI_GEOCODING = "https://www.mapquestapi.com/geocoding/v1/reverse";
    private static final int API_SUCCESS = 0;
    private ISimpleHttpClient httpClient;

    public AddressResolver(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public Optional<Address> findAddressForLocation(double latitude, double longitude) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {

        String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        URIBuilder uriBuilder = new URIBuilder(MAPQUESTAPI_GEOCODING);
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.6f,%.6f", latitude, longitude).toString());

        System.err.println(" url: " + uriBuilder.build().toString());

        String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());

        log.debug("Api Response: " + apiResponse);

/*        if (apiResponse == null){ // ISimpleHttpClient returns null always, its an interface
            return Optional.of(new Address("Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null));
        }*/


        if(apiResponse.equals("null")){
            throw new NullPointerException();
        }

        log.debug( "remote response: ",  apiResponse);

        // get root object from JSON
        JSONObject obj = (JSONObject) new JSONParser().parse(apiResponse);

        if((Long)((JSONObject)obj.get("info") ).get("statuscode")  !=  API_SUCCESS) {
            //log.debug(String.valueOf(((JSONObject)obj.get("info") ).get("messages")));
            if (String.valueOf(((JSONObject)obj.get("info") ).get("messages")).equals("[\"Illegal argument from request: Invalid LatLng specified.\"]")) {
                throw new IndexOutOfBoundsException();
            }
            return Optional.empty();
        }else {
            // get the first element of the results array
            log.debug(obj.toString());
            obj = (JSONObject) ((JSONArray) obj.get("results")).get(0);

            if (((JSONArray) obj.get("locations")).isEmpty()){
                return Optional.empty();
            } else {
                JSONObject address = (JSONObject) ((JSONArray) obj.get("locations")).get(0);

                String road = (String) address.get("street");
                String city = (String) address.get("adminArea5");
                String state = (String) address.get("adminArea3");
                String zip = (String) address.get("postalCode");
                return Optional.of(new Address(road, city, state, zip, null));
            }
        }
    }
}
