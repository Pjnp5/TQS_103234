package tqs.Hw1.Service;

import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.Hw1.Cache.TTLCache;
import tqs.Hw1.Models.City;
import tqs.Hw1.Services.CityServiceImpl;
import tqs.Hw1.Services.HTTPClient;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @InjectMocks
    private CityServiceImpl cityService;

    @Mock
    private TTLCache cityCache;

    @Mock
    private HTTPClient httpClient;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetCityByName_WhenCityInCache_ShouldReturnCityFromCache() throws IOException {
        // Arrange
        String cityName = "London";
        City expectedCity = new City("London", "United Kingdom", 51.5074, -0.1278, 0.5, 0.1, 0.2, 0.3, 10.5, 20.5);
        when(cityCache.get(cityName.toLowerCase())).thenReturn(expectedCity);

        // Act
        City actualCity = cityService.getCityByName(cityName);

        // Assert
        assertEquals(expectedCity, actualCity);
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, never()).put(anyString(), any(City.class));
    }

    @Test
    public void testGetCityByName_WhenCityNotInCache_ShouldFetchFromAPIAndAddToCache() throws IOException {
        // Arrange
        String cityName = "New York";
        City expectedCity = new City("New York", "United States", 40.7128, -74.0060, 0.4, 0.2, 0.3, 0.1, 15.5, 25.5);
        when(cityCache.get(cityName.toLowerCase())).thenReturn(null);
        when(httpClient.makeRequestByName(cityName)).thenReturn("{...}"); // Replace with actual API response
        when(httpClient.makeRequestByName_extra(cityName)).thenThrow(new JSONException("Invalid JSON")); // Simulate exception
        when(cityCache.getCacheDetails()).thenReturn(new HashMap<>());

        // Act
        City actualCity = cityService.getCityByName(cityName);

        // Assert
        assertEquals(expectedCity, actualCity);
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, times(1)).put(cityName.toLowerCase(), expectedCity);
        verify(cityCache, times(1)).incrementHitCount();
        verify(cityCache, times(1)).incrementMissCount();
    }

    @Test
    public void testGetCityByLatAndLon_ShouldFetchFromAPI() throws IOException {
        // Arrange
        Double lat = 51.5074;
        Double lon = -0.1278;
        City expectedCity = new City("London", "United Kingdom", 51.5074, -0.1278, 0.5, 0.1, 0.2, 0.3, 10.5, 20.5);
        when(httpClient.makeRequestByLAtAndLon(lat.toString(), lon.toString())).thenReturn("{...}"); // Replace with actual API response

        // Act
        City actualCity = cityService.getCityByLatAndLon(lat, lon);

        // Assert
        assertEquals(expectedCity, actualCity);
        verify(httpClient, times(1)).makeRequestByLAtAndLon(lat.toString(), lon.toString());
    }
}