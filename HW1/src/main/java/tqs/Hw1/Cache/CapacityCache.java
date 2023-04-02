package tqs.Hw1.Cache;

import tqs.Hw1.Models.City;

import java.util.HashMap;
import java.util.Map;

public class CapacityCache<K, V> {
    private final Map<K, City> cacheMap;
    private City first;
    private City last;
    private int size;
    private final int capacity; // maximum number of Cities to save
    private int hitCount = 0;
    private int missCount = 0;
    private int requestCount = 0;

    public CapacityCache(int capacity) {
        this.capacity = capacity;
        cacheMap = new HashMap<>(capacity);
    }

    public void put(K key, City city) {

        if (!cacheMap.containsKey(key)) {
            if (size() >= capacity) deleteNode(first);

            addNodeToLast(city);
        }

        cacheMap.put(key, city);
    }

    public City get(K key) {
        this.requestCount++;

        if (!cacheMap.containsKey(key)) {
            this.missCount++;
            return null;
        } else {

            City city = cacheMap.get(key);

            this.hitCount++;
            city.incrementHitCounter();

            return city;
        }
    }

    public void delete(K key) {
        deleteNode(cacheMap.get(key));
    }

    private void deleteNode(City city) {
        if (city == null) return;

        cacheMap.remove(city.getName());
        size--;
    }

    private void addNodeToLast(City city) {
        last = city;

        if(first == null) first = city;

        size++;
    }

    public int size() {
        return this.size;
    }
    public int getHitCount() {
        return this.hitCount;
    }

    public int getMissCount() {
        return this.missCount;
    }

    public int getRequestCount() {
        return this.requestCount;
    }

    public Map<String, Object> getCacheDetails() {
        Map<String, Object> detailsMap = new HashMap<>();

        detailsMap.put("hits", getHitCount());
        detailsMap.put("misses", getMissCount());
        detailsMap.put("requests", getRequestCount());
        detailsMap.put("Size", size());

        return detailsMap;
    }
}
