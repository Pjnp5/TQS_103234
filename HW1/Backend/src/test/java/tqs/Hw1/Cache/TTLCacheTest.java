package tqs.Hw1.Cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.Hw1.Models.City;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TTLCacheTest {
    private static TTLCache cache;

    @BeforeEach
    void setUp() {
        cache = new TTLCache();
    }

    @Test
    void putTest() {
        City aveiro = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        cache.put("Oporto", aveiro);

        assertEquals(aveiro, cache.get("Oporto"));
    }

    @Test
    void getTest() {
        City porto = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        cache.put("Aveiro", porto);

        assertEquals(porto, cache.get("Aveiro"));
    }

    @Test
    void getTestAfterTimeout() {
// Set up mock city
        City city = mock(City.class);
        when(city.getLastAcess()).thenReturn(System.currentTimeMillis() / 1000 - 61);

        // Set up mock system time
        long now = System.currentTimeMillis() / 1000;
        SystemTime systemTime = mock(SystemTime.class);
        when(systemTime.getCurrentTimeSeconds()).thenReturn(now);

        // Set up cache with mocked system time
        cache.put("Lisbon", city);

        // Try to retrieve cached city after TTL has expired
        City cachedCity = cache.get("Lisbon");

        // Verify that cache returns null when TTL has expired
        assertNull(cachedCity);
    }

    /**
     * A mock class that returns the current system time in seconds.
     */
    private static class SystemTime {
        public long getCurrentTimeSeconds() {
            return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
    }

    @Test
    void deleteTest() {
        City porto = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        cache.put("Oporto", porto);

        cache.deleteNode(porto);

        assertNull(cache.get("Oporto"));
    }

    @Test
    void deleteTestWithNull() {
        assertDoesNotThrow(() -> cache.deleteNode(null));
    }

    @Test
    void getHitsTest() {
        City aveiro = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        cache.put("Oporto", aveiro);

        cache.get("Oporto");

        assertEquals(1, cache.getHitCount());
    }

    @Test
    void getMissesTest() {
        cache.get("Oporto");

        assertEquals(1, cache.getMissCount());
    }

    @Test
    void getRequestsTest() {
        City porto = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);

        cache.put("Oporto", porto);
        cache.get("Oporto");
        cache.get("Aveiro");

        assertEquals(2, cache.getRequestCount());
    }

    @Test
    void getCacheDetailsTest() {
        City porto = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        cache.put("Oporto", porto);
        cache.get("Oporto");
        cache.get("Aveiro");

        Map<String, Object> map = new HashMap<>();
        map.put("hits", 1);
        map.put("misses", 1);
        map.put("requests", 2);

        assertEquals(map, cache.getCacheDetails());
    }

}
