package tqs.Hw1.Cache;

import org.springframework.stereotype.Service;
import tqs.Hw1.Models.City;

import java.util.HashMap;
import java.util.Map;
@Service
public class TTLCache {
    private final Map<String, City> cacheMap; // Map with city name (K) and the city object
    private static final int TTL = 60; // seconds the Cities will remain saved
    private int hitCount = 0; // number of cache hits
    private int missCount = 0; // number of cache misses
    private int requestCount = 0; // total number of cache requests

    // Cache Creation
    public TTLCache() {
        cacheMap = new HashMap<>(TTL);
    }

    // Adding a new item to the cache
    public void put(String key, City city) {
        city.setLastAcess(); // set last access time for new item
        cacheMap.put(key, city); // add item to cache
    }

    // Get information of a city in cache
    public City get(String key) {
        this.requestCount++; // increment the total number of cache requests

        if (!cacheMap.containsKey(key)) { // check if item exists in cache
            incrementMissCount();
            return null; // return null if item doesn't exist
        } else {
            City city;
            // check if item has timed out
            if (System.currentTimeMillis() / 1000 >= cacheMap.get(key).getLastAcess() + TTL) {
                deleteNode(cacheMap.get(key)); // delete timed out item
                return null; // return null if item has timed out
            } else {
                // item is still within TTL, so return it and increment the hit count
                city = cacheMap.get(key);
                incrementHitCount();
                city.incrementHitCounter();
            }
            return city; // return the city object
        }
    }

    // Delete an item from cache
    public void deleteNode(City city) {
        if (city == null) return; // check if item is null
        cacheMap.remove(city.getName()); // remove item from cache
    }

    public int getHitCount() {
        return this.hitCount; // return the number of cache hits
    }

    public void incrementHitCount() {
        this.hitCount++; // increment the number of cache hits
    }

    public int getMissCount() {
        return this.missCount; // return the number of cache misses
    }

    public void incrementMissCount() {
        this.missCount++; // increment the number of cache misses
    }

    public int getRequestCount() {
        return this.requestCount; // return the total number of cache requests
    }

    // Get cache details in a Map format
    public Map<String, Object> getCacheDetails() {
        Map<String, Object> detailsMap = new HashMap<>(); // create new Map object

        // add hit count, miss count, and request count to Map object
        detailsMap.put("hits", getHitCount());
        detailsMap.put("misses", getMissCount());
        detailsMap.put("requests", getRequestCount());

        return detailsMap; // return Map object with cache details
    }
}
