package tqs.Hw1.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
class CityServiceTest {

    @Mock(lenient = true)
    private TTLCache cityCache;

    @Mock(lenient = true)
    private HTTPClient httpClient;

    @InjectMocks
    private CityServiceImpl cityService;

    private City dummyCity;
    private City dummyCity2;

    @BeforeEach
    void setUp() {
        dummyCity = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        dummyCity2 = new City("Porto","PT",41.1494512, -8.6107884, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCityByName_FromCache() throws IOException {
        // Arrange
        String cityName = "Oporto";
        when(cityCache.get(anyString())).thenReturn(dummyCity);

        // Act
        City actualCity = cityService.getCityByName(cityName.toLowerCase());

        // Assert
        assertEquals(dummyCity.getName(), actualCity.getName());
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, never()).put(anyString(), any(City.class));
    }

    @Test
    void testGetCityByName_FirstAPI() throws IOException {
        // Arrange
        String cityName = "Oporto";

        when(cityCache.get(cityName.toLowerCase())).thenReturn(null);
        when(httpClient.makeRequestByName(anyString())).thenReturn("{\"location\":{\"name\":\"Oporto\",\"region\":\"Porto\",\"country\":\"Portugal\",\"lat\":41.15,\"lon\":-8.62,\"tz_id\":\"Europe/Lisbon\",\"localtime_epoch\":1680797712,\"localtime\":\"2023-04-06 17:15\"},\"current\":{\"last_updated_epoch\":1680797700,\"last_updated\":\"2023-04-06 17:15\",\"temp_c\":22.0,\"temp_f\":71.6,\"is_day\":1,\"condition\":{\"text\":\"Sunny\",\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/113.png\",\"code\":1000},\"wind_mph\":10.5,\"wind_kph\":16.9,\"wind_degree\":320,\"wind_dir\":\"NW\",\"pressure_mb\":1015.0,\"pressure_in\":29.97,\"precip_mm\":0.0,\"precip_in\":0.0,\"humidity\":43,\"cloud\":0,\"feelslike_c\":24.4,\"feelslike_f\":75.8,\"vis_km\":10.0,\"vis_miles\":6.0,\"uv\":6.0,\"gust_mph\":8.9,\"gust_kph\":14.4,\"air_quality\":{\"co\":223.60000610351562,\"no2\":2.5999999046325684,\"o3\":115.9000015258789,\"so2\":2.0,\"pm2_5\":10.399999618530273,\"pm10\":15.300000190734863,\"us-epa-index\":1,\"gb-defra-index\":1}}}");
        when(cityCache.getCacheDetails()).thenReturn(new HashMap<>());

        // Act
        City actualCity = cityService.getCityByName(cityName);

        // Assert
        assertEquals(dummyCity.getName(), actualCity.getName());
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, times(1)).put(cityName.toLowerCase(), actualCity); // using actual city, cause dummy city will have diferent values on air quality
        verify(cityCache, times(1)).incrementHitCount();
    }

    // In the lat and lon search I havent implemented the cache, so i only check if the API works fine
    @Test
    void testGetCityByLatAndLon_FirstAPI() throws IOException {
        // Arrange
        Double lat = 41.15;
        Double lon = -8.62;
        when(httpClient.makeRequestByLAtAndLon(anyString(), anyString())).thenReturn("{\"location\":{\"name\":\"Oporto\",\"region\":\"Porto\",\"country\":\"Portugal\",\"lat\":41.15,\"lon\":-8.62,\"tz_id\":\"Europe/Lisbon\",\"localtime_epoch\":1680800697,\"localtime\":\"2023-04-06 18:04\"},\"current\":{\"last_updated_epoch\":1680800400,\"last_updated\":\"2023-04-06 18:00\",\"temp_c\":22.0,\"temp_f\":71.6,\"is_day\":1,\"condition\":{\"text\":\"Sunny\",\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/113.png\",\"code\":1000},\"wind_mph\":11.9,\"wind_kph\":19.1,\"wind_degree\":350,\"wind_dir\":\"N\",\"pressure_mb\":1014.0,\"pressure_in\":29.94,\"precip_mm\":0.0,\"precip_in\":0.0,\"humidity\":35,\"cloud\":0,\"feelslike_c\":24.5,\"feelslike_f\":76.1,\"vis_km\":10.0,\"vis_miles\":6.0,\"uv\":6.0,\"gust_mph\":7.4,\"gust_kph\":11.9,\"air_quality\":{\"co\":220.3000030517578,\"no2\":3.299999952316284,\"o3\":114.4000015258789,\"so2\":2.0999999046325684,\"pm2_5\":9.800000190734863,\"pm10\":14.199999809265137,\"us-epa-index\":1,\"gb-defra-index\":1}}}");

        // Act
        City actualCity = cityService.getCityByLatAndLon(lat, lon);


        // Assert
        assertEquals(dummyCity.getName(), actualCity.getName());
    }
    @Test
    void testGetCityByName_SecondAPI() throws IOException {
        // Arrange
        String cityName = "Oporto";
        when(cityCache.get(anyString())).thenReturn(null);
        when(httpClient.makeRequestByName(anyString())).thenReturn("{\n" +
                "  \"CO\": {\n" +
                "    \"concentration\": 226.97,\n" +
                "    \"aqi\": 2\n" +
                "  },\n" +
                "  \"NO2\": {\n" +
                "    \"concentration\": 2.57,\n" +
                "    \"aqi\": 3\n" +
                "  },\n" +
                "  \"O3\": {\n" +
                "    \"concentration\": 120.16,\n" +
                "    \"aqi\": 201\n" +
                "  },\n" +
                "  \"SO2\": {\n" +
                "    \"concentration\": 2.03,\n" +
                "    \"aqi\": 2\n" +
                "  },\n" +
                "  \"PM2.5\": {\n" +
                "    \"concentration\": 9.98,\n" +
                "    \"aqi\": 32\n" +
                "  },\n" +
                "  \"PM10\": {\n" +
                "    \"concentration\": 14.49,\n" +
                "    \"aqi\": 13\n" +
                "  },\n" +
                "  \"overall_aqi\": 201\n" +
                "}");
        when(httpClient.makeRequestByName_extra(anyString())).thenReturn("  {\n" +
                "    \"name\": \"Porto\",\n" +
                "    \"latitude\": 41.1494512,\n" +
                "    \"longitude\": -8.6107884,\n" +
                "    \"country\": \"PT\"\n" +
                "  }");

        // Act
        City actualCity = cityService.getCityByName(cityName.toLowerCase());

        // Assert
        assertEquals(dummyCity2.getName(), actualCity.getName());
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, times(1)).put(cityName.toLowerCase(), actualCity); // using actual city, cause dummy city will have diferent values on air quality
        verify(cityCache, times(1)).incrementHitCount();
    }

    @Test
    void testGetCityByLatAndLon_SecondAPI() throws IOException {
        // Arrange
        Double lat = 41.15;
        Double lon = -8.62;
        when(httpClient.makeRequestByLAtAndLon(anyString(),anyString())).thenReturn("{\n" +
                "  \"CO\": {\n" +
                "    \"concentration\": 226.97,\n" +
                "    \"aqi\": 2\n" +
                "  },\n" +
                "  \"NO2\": {\n" +
                "    \"concentration\": 2.57,\n" +
                "    \"aqi\": 3\n" +
                "  },\n" +
                "  \"O3\": {\n" +
                "    \"concentration\": 120.16,\n" +
                "    \"aqi\": 201\n" +
                "  },\n" +
                "  \"SO2\": {\n" +
                "    \"concentration\": 2.03,\n" +
                "    \"aqi\": 2\n" +
                "  },\n" +
                "  \"PM2.5\": {\n" +
                "    \"concentration\": 9.98,\n" +
                "    \"aqi\": 32\n" +
                "  },\n" +
                "  \"PM10\": {\n" +
                "    \"concentration\": 14.49,\n" +
                "    \"aqi\": 13\n" +
                "  },\n" +
                "  \"overall_aqi\": 201\n" +
                "}");
        when(httpClient.makeRequestByLAtAndLon_extra(anyString(),anyString())).thenReturn("{\n" +
                "  \"latitude\": 41.15,\n" +
                "  \"longitude\": -8.62,\n" +
                "  \"continent\": \"Europe\",\n" +
                "  \"lookupSource\": \"coordinates\",\n" +
                "  \"continentCode\": \"EU\",\n" +
                "  \"localityLanguageRequested\": \"en\",\n" +
                "  \"city\": \"Porto\",\n" +
                "  \"countryName\": \"Portugal\",\n" +
                "  \"countryCode\": \"PT\",\n" +
                "  \"postcode\": \"\",\n" +
                "  \"principalSubdivision\": \"Distrito do Porto\",\n" +
                "  \"principalSubdivisionCode\": \"PT-13\",\n" +
                "  \"plusCode\": \"8CHH592J+22\",\n" +
                "  \"locality\": \"Cedofeita\",\n" +
                "  \"localityInfo\": {\n" +
                "    \"administrative\": [\n" +
                "      {\n" +
                "        \"name\": \"Portugal\",\n" +
                "        \"description\": \"country in Southwestern Europe\",\n" +
                "        \"order\": 3,\n" +
                "        \"adminLevel\": 2,\n" +
                "        \"isoCode\": \"PT\",\n" +
                "        \"wikidataId\": \"Q45\",\n" +
                "        \"geonameId\": 2264397\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Norte Region\",\n" +
                "        \"description\": \"NUTS 2 region of Portugal\",\n" +
                "        \"order\": 5,\n" +
                "        \"adminLevel\": 4,\n" +
                "        \"wikidataId\": \"Q27662\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Distrito do Porto\",\n" +
                "        \"description\": \"district of Portugal\",\n" +
                "        \"order\": 6,\n" +
                "        \"adminLevel\": 6,\n" +
                "        \"isoCode\": \"PT-13\",\n" +
                "        \"wikidataId\": \"Q322792\",\n" +
                "        \"geonameId\": 2735941\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Metropolitan Area of Porto\",\n" +
                "        \"description\": \"NUTS 3 subregion of Portugal\",\n" +
                "        \"order\": 7,\n" +
                "        \"adminLevel\": 5,\n" +
                "        \"wikidataId\": \"Q2652989\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Porto\",\n" +
                "        \"description\": \"municipality and city in Portugal\",\n" +
                "        \"order\": 8,\n" +
                "        \"adminLevel\": 7,\n" +
                "        \"wikidataId\": \"Q36433\",\n" +
                "        \"geonameId\": 2735943\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Cedofeita, Santo Ildefonso, Se, Miragaia, Sao Nicolau e Vitoria\",\n" +
                "        \"description\": \"civil parish in Porto\",\n" +
                "        \"order\": 9,\n" +
                "        \"adminLevel\": 8,\n" +
                "        \"wikidataId\": \"Q15905280\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Cedofeita\",\n" +
                "        \"description\": \"locality and former civil parish in Portugal\",\n" +
                "        \"order\": 10,\n" +
                "        \"adminLevel\": 9,\n" +
                "        \"wikidataId\": \"Q1052596\",\n" +
                "        \"geonameId\": 8012855\n" +
                "      }\n" +
                "    ],\n" +
                "    \"informative\": [\n" +
                "      {\n" +
                "        \"name\": \"Europe\",\n" +
                "        \"description\": \"continent\",\n" +
                "        \"order\": 1,\n" +
                "        \"isoCode\": \"EU\",\n" +
                "        \"wikidataId\": \"Q46\",\n" +
                "        \"geonameId\": 6255148\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Iberian Peninsula\",\n" +
                "        \"description\": \"peninsula located in the extreme southwest of Europe\",\n" +
                "        \"order\": 2,\n" +
                "        \"wikidataId\": \"Q12837\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Europe/Lisbon\",\n" +
                "        \"description\": \"time zone\",\n" +
                "        \"order\": 4\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        // Act
        City actualCity = cityService.getCityByLatAndLon(lat, lon);

        // Assert
        assertEquals(dummyCity2.getName(), actualCity.getName());
    }
}

