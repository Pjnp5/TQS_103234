package tqs.Hw1.Services;

import org.springframework.beans.factory.annotation.Autowired;
import tqs.Hw1.Cache.TTLCache;
import tqs.Hw1.Models.City;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityServiceImpl implements CityService{

    // create an instance of a TTLCache with a TTL of 60 seconds
    @Autowired
    private TTLCache cityCache = new TTLCache();

    @Autowired
    private HTTPClient httpClient = new HTTPClient();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    // method to retrieve city by name
    @Override
    public City getCityByName(String name) throws IOException {
        City city = cityCache.get(name.toLowerCase());

        if (city == null) {
            // if the city is not present in the cache, consume from API and add to cache
            city = consumeFromAPIbyName( name );
            if(city != null){
                // if data is available from API, add data to cache
                cityCache.put(name.toLowerCase(), city);}
        }
        return city;
    }

    // method to retrieve city by latitude and longitude, it has no connection with cache
    @Override
    public City getCityByLatAndLon(Double lat, Double lon) throws IOException {
        String response = httpClient.makeRequestByLAtAndLon_extra(lat.toString(), lon.toString());
        JSONObject jsonObj = new JSONObject(response);


        String name = jsonObj.getString("city");
        Double lati = jsonObj.getDouble("latitude");
        Double longi = jsonObj.getDouble("longitude");
        String country = jsonObj.getString("countryName");

        City city = cityCache.get(name.toLowerCase());
        if (city == null) {
            // if the city is not present in the cache, consume from API and add to cache
            city = consumeFromAPIbyLatAndLon(lat.toString(), lon.toString(), name,  lati, longi, country );
            if(city != null){
                // if data is available from API, add data to cache
                cityCache.put(name.toLowerCase(), city);}
        }
        return city;
    }

    // method to retrieve cache details
    @Override
    public Map<String, Object> getCacheDetails() {
        return cityCache.getCacheDetails();
    }

    // method to consume API and retrieve city data by name
    public City consumeFromAPIbyName(String url) throws IOException {
        // Create a Logger
        Logger logger
                = Logger.getLogger(
                CityServiceImpl.class.getName());

        String response = httpClient.makeRequestByName(url);

        JSONObject jsonObj = new JSONObject(response);

        // try to get info like it from the first API
        try {
            return getInfo(jsonObj);
        }catch (JSONException e){
            // try to get info like it from the second API, since the first did not work out
            try{
                response = httpClient.makeRequestByName_extra(url);
                JSONObject jsonObj2 = new JSONObject(response);

                String name = jsonObj2.getString("name");
                Double lat = jsonObj2.getDouble("latitude");
                Double lon = jsonObj2.getDouble("longitude");
                String country = jsonObj2.getString("country");

                return getInfo2(jsonObj, name, lat, lon, country);
            }
            catch (JSONException ex) {
                // in case none works out, raise the error and return null
                logger.log(Level.SEVERE, "ERROR: ", ex);
                return null;
            }
        }
    }

    // method to consume API and retrieve city data by latitude and longityude
    public City consumeFromAPIbyLatAndLon(String lat, String lon, String name, Double lati, Double longi, String country) throws IOException {
        // Create a Logger
        Logger logger
                = Logger.getLogger(
                CityServiceImpl.class.getName());

        String response = httpClient.makeRequestByLAtAndLon(lat, lon);

        JSONObject jsonObj = new JSONObject(response);

        // trying to get info from the first API
        try {
            return getInfo(jsonObj);
        }catch (JSONException e){
            // trying to get info from the second API, since the first did not work out
            try{
                return getInfo2(jsonObj, name, lati, longi, country);
            }
            catch (JSONException ex) {
                // in case none works out, raise the error
                logger.log(Level.SEVERE, "ERROR: ", ex);
                return null;
            }
        }
    }

    // method to extract city data from the JSON response of the API
    private City getInfo(JSONObject jsonObj) {
        JSONObject locationObj;
        locationObj = jsonObj.getJSONObject("location");
        String name = locationObj.getString("name");
        String country = locationObj.getString("country");
        Double lat = locationObj.getDouble("lat");
        Double lon = locationObj.getDouble("lon");

        locationObj = jsonObj.getJSONObject("current");
        JSONObject locationObj_inside = locationObj.getJSONObject("air_quality");

        Double co = locationObj_inside.getDouble("co");
        Double no2 = locationObj_inside.getDouble("no2");
        Double o3 = locationObj_inside.getDouble("o3");
        Double so2 = locationObj_inside.getDouble("so2");
        Double pm2_5 = locationObj_inside
                .getDouble("pm2_5");
        Double pm10 = locationObj_inside.getDouble("pm10");


        return new City(name, country, lat, lon, Double.valueOf(df.format(co)) , Double.valueOf(df.format(no2)) ,Double.valueOf(df.format(o3)), Double.valueOf(df.format(so2)), Double.valueOf(df.format(pm2_5)), Double.valueOf(df.format(pm10)));
    }
    // method to extract city data from the JSON response of the other API

    private City getInfo2(JSONObject jsonObj, String name, Double lat, Double lon, String country) {
        JSONObject locationObj;
        String concentration = "concentration";
        locationObj = jsonObj.getJSONObject("CO");
        Double co = locationObj.getDouble(concentration);
        locationObj = jsonObj.getJSONObject("NO2");
        Double no2 = locationObj.getDouble(concentration);
        locationObj = jsonObj.getJSONObject("O3");
        Double o3 = locationObj.getDouble(concentration);
        locationObj = jsonObj.getJSONObject("SO2");
        Double so2 = locationObj.getDouble(concentration);
        locationObj = jsonObj.getJSONObject("PM2.5");
        Double pm2_5 = locationObj.getDouble(concentration);
        locationObj = jsonObj.getJSONObject("PM10");
        Double pm10 = locationObj.getDouble(concentration);

        return new City(name, country, lat, lon, Double.valueOf(df.format(co)) , Double.valueOf(df.format(no2)) ,Double.valueOf(df.format(o3)), Double.valueOf(df.format(so2)), Double.valueOf(df.format(pm2_5)), Double.valueOf(df.format(pm10)));
    }
}
