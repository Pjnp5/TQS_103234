package tqs.Hw1.Services;

import tqs.Hw1.Models.City;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface CityService {

    // Method to get a City object by its name
    City getCityByName(String name) throws IOException, URISyntaxException;

    // Method to get a City object by its latitude and longitude
    City getCityByLatAndLon(Double lat, Double lon) throws IOException, URISyntaxException;

    // Method to get a map of cache statistics such as the number of entries, hit rate, and miss rate
    Map<String, Object> getCacheDetails();
}
