package tqs.Hw1.Services;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Define the HTTPClient class
@Service
public class HTTPClient {

    // Define constants for the API URLs and keys
    private static final String URL = "https://api.weatherapi.com/v1/current.json?key=";
    private static final String URL2 = "https://api.api-ninjas.com/v1/airquality?city=";
    private static final String URL3 = "https://api.api-ninjas.com/v1/airquality?lat=";
    private static final String KEY = "0393b6910e874309947113255230204";
    private static final String KEY2 = "00vZsomznr8mpVUx3MFCAA==G1pLW1v9O1gtxjHY";
    private static final String KEY3 = "bdc_edfd91d021ec4f6d95889d362be12277";

    private static final String HEADER = "X-Api-Key";

    private static final String ERROR = "ERROR: ";

    // Create an HTTP client object
    private CloseableHttpClient client;
    public HTTPClient() {
        this.client = HttpClients.createDefault();
    }

    // Define a method to make a request by name
    public String makeRequestByName(String name) throws IOException {

        // Create a GET request with the URL and API key
        HttpGet httpGet = new HttpGet(URL + KEY + "&q=" + name + "&aqi=yes");

        // Create a Logger object
        Logger logger = Logger.getLogger(HTTPClient.class.getName());

        // Execute the request and store the response
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        try {
            // Get the response entity
            HttpEntity httpEntity = httpResponse.getEntity();
            // If the response status code is not 200, try another API
            if (httpResponse.getStatusLine().getStatusCode() != 200){
                httpGet = new HttpGet(URL2 + name);
                httpGet.setHeader(HEADER, KEY2);
                httpResponse = client.execute(httpGet);

                // If the new API also returns an error, throw an exception
                if(httpResponse.getStatusLine().getStatusCode() != 200){
                    throw new IllegalArgumentException();
                }
                httpEntity = httpResponse.getEntity();
            }
            // Return the response entity as a string
            return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
        } catch (Exception e) {
            // Log any errors that occur
            logger.log(Level.SEVERE, ERROR, e);
        }
        // Return null if an error occurs
        return null;
    }

    // Define a method to make a request by name with an additional API, to obtain geolocation info.
    public String makeRequestByName_extra(String name) {
        // Create a Logger object
        Logger logger = Logger.getLogger(HTTPClient.class.getName());

        try {
            // Create a GET request with the geocoding API URL and API key
            HttpGet httpGet = new HttpGet("https://api.api-ninjas.com/v1/geocoding?city=" + name);
            httpGet.setHeader(HEADER, KEY2);
            // Execute the request and store the response
            CloseableHttpResponse httpResponse = client.execute(httpGet);

            // If the response status code is not 200, throw an exception
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new IllegalArgumentException();
            }
            // Obtain and the return the response entity as a string, treating the string so it can be used as JSON later
            HttpEntity httpEntity = httpResponse.getEntity();
            return httpEntity != null ? EntityUtils.toString(httpEntity).replace("[", "").replace("]", "") : null;

        } catch (Exception e) {
            // Log any errors that occur
            logger.log(Level.SEVERE, ERROR, e);
        }
        // Return null if an error occurs

        return null;
    }

    public String makeRequestByLAtAndLon(String lat, String lon) throws IOException {
        // Construct the URL for the weather API request
        HttpGet httpGet = new HttpGet(URL + KEY + "&q=" + lat + "," + lon + "&aqi=yes");

        // Create a Logger
        Logger logger = Logger.getLogger(HTTPClient.class.getName());

        // Send the HTTP request and get the response
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        try {
            // Check if the HTTP response code is 200 OK
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                // If not, try another API endpoint with a different URL and API key
                httpGet = new HttpGet(URL3 + lat + "&lon=" + lon);
                httpGet.setHeader(HEADER, KEY2);
                httpResponse = client.execute(httpGet);

                // If the second API endpoint also returns an error, throw an IllegalArgumentException
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    throw new IllegalArgumentException();
                }

                // Otherwise, get the HTTP response entity
                httpEntity = httpResponse.getEntity();
            }

            // Return the HTTP response entity as a string
            return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
        } catch (Exception e) {
            // If an error occurs, log the error with the logger and return null
            logger.log(Level.SEVERE, ERROR, e);
        }

        return null;
    }

    public String makeRequestByLAtAndLon_extra(String lat, String lon) {
        // Create a Logger
        Logger logger = Logger.getLogger(HTTPClient.class.getName());

        try {
            // Construct the URL for the reverse geocoding API request
            HttpGet httpGet = new HttpGet("https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + lat + "&longitude=" + lon +"&localityLanguage=en&key=" + KEY3);

            // Send the HTTP request and get the response
            CloseableHttpResponse httpResponse = client.execute(httpGet);

            // Check if the HTTP response code is 200 OK
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                // If not, throw an IllegalArgumentException
                throw new IllegalArgumentException();
            }

            // Get the HTTP response entity
            HttpEntity httpEntity = httpResponse.getEntity();

            // Return the HTTP response entity as a string
            return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
        } catch (Exception e) {
            // If an error occurs, log the error with the logger and return null
            logger.log(Level.SEVERE, ERROR, e);
        }

        return null;
    }
}
