package tqs.Hw1.Services;

import tqs.Hw1.Cache.CapacityCache;
import tqs.Hw1.Models.City;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityServiceImpl implements CityService{
    private static final String API_KEY = "0393b6910e874309947113255230204";
    private static final String URL = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY;

    /*
     * CACHE:
     * I used two types of cache, one that uses a maximum number of items and other that uses TTl (time to leave), just uncomment the one to be used
     */

    private final CapacityCache<String, City> cityCache = new CapacityCache<>(5); // maximum number of Cities to save, if passed, the first city in cache is the first city to be removed (FIFO)
    //private CityTTLCache<String, City> cityCache = new CityTTLCache<>(60, 60);

    @Override
    public City getCityByName(String name) throws IOException, URISyntaxException {
        City city = cityCache.get(name.toLowerCase());

        if (city == null) {
            city = consumeFromAPI(URL + "&q=" + name + "&aqi=yes");

            if(city != null)
                cityCache.put(name.toLowerCase(), city);

        }
        System.out.println(city);
        return city;
    }

    @Override
    public City getCityByLatAndLon(Double lat, Double lon) throws IOException, URISyntaxException {
        return consumeFromAPI(URL + "&q=" + lat + "," + lon + "&aqi=yes");
    }

    @Override
    public Map<String, Object> getCacheDetails() {
        return cityCache.getCacheDetails();
    }

    public City consumeFromAPI(String url) throws IOException, URISyntaxException {
        // Create a Logger
        Logger logger
                = Logger.getLogger(
                CityServiceImpl.class.getName());

        HTTPClient httpClient = new HTTPClient();

        System.out.println(url);
        String response = httpClient.makeRequest(url);

        System.out.println(response);

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject locationObj = jsonObj.getJSONObject("location");


            String name = locationObj.getString("name");
            String country = locationObj.getString("country");
            Double lat = locationObj.getDouble("lat");
            Double lon = locationObj.getDouble("lon");
            String localTime = locationObj.getString("localtime");

            locationObj = jsonObj.getJSONObject("current");
            JSONObject locationObj_inside = locationObj.getJSONObject("air_quality");

            Double co = locationObj_inside.getDouble("co");
            Double no2 = locationObj_inside.getDouble("no2");
            Double o3 = locationObj_inside.getDouble("o3");
            Double so2 = locationObj_inside.getDouble("so2");
            Double pm2_5 = locationObj_inside.getDouble("pm2_5");
            Double pm10 = locationObj_inside.getDouble("pm10");

            System.out.println(co);
            System.out.println(no2);
            System.out.println(o3);
            System.out.println(so2);
            System.out.println(pm2_5);
            System.out.println(pm10);


            return new City(name, country, lat, lon, localTime);

        } catch (JSONException e) {
            logger.log(Level.SEVERE, "ERROR: ", e);
            return null;
        }
    }
}
