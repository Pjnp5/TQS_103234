package tqs.Hw1.Services;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPClient {
    private static final String KEY = "0393b6910e874309947113255230204";

    private CloseableHttpClient client;

    public HTTPClient() {
        this.client = HttpClients.createDefault();
    }

    public String makeRequest(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url + "&key=" + KEY);

        // Create a Logger
        Logger logger
                = Logger.getLogger(
                HTTPClient.class.getName());

        CloseableHttpResponse httpResponse = client.execute(httpGet);
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERROR: ", e);
        }

        return null;
    }
}
