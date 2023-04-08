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
    void testGetCityByLatandLon_FromCache() throws IOException {
        // Arrange
        String cityName = "Oporto";

        when(httpClient.makeRequestByLAtAndLon_extra(anyString(),anyString())).thenReturn("{\"latitude\": 41.15,"+"  \"longitude\": -8.62,"+"  \"continent\": \"Europe\","+"  \"lookupSource\": \"coordinates\","+"  \"continentCode\": \"EU\","+"  \"localityLanguageRequested\": \"en\","+"  \"city\": \"Porto\","+"  \"countryName\": \"Portugal\","+"  \"countryCode\": \"PT\","+"  \"postcode\": \"\","+"  \"principalSubdivision\": \"Distrito do Porto\","+"  \"principalSubdivisionCode\": \"PT-13\","+"  \"plusCode\": \"8CHH592J+22\","+"  \"locality\": \"Cedofeita\","+"  \"localityInfo\": {"+"    \"administrative\": ["+"      {"+"        \"name\": \"Portugal\","+"        \"description\": \"country in Southwestern Europe\","+"        \"order\": 3,"+"        \"adminLevel\": 2,"+"        \"isoCode\": \"PT\","+"        \"wikidataId\": \"Q45\","+"        \"geonameId\": 2264397"+"      },"+"      {"+"        \"name\": \"Norte Region\","+"        \"description\": \"NUTS 2 region of Portugal\","+"        \"order\": 5,"+"        \"adminLevel\": 4,"+"        \"wikidataId\": \"Q27662\""+"      },"+"      {"+"        \"name\": \"Distrito do Porto\","+"        \"description\": \"district of Portugal\","+"        \"order\": 6,"+"        \"adminLevel\": 6,"+"        \"isoCode\": \"PT-13\","+"        \"wikidataId\": \"Q322792\","+"        \"geonameId\": 2735941"+"      },"+"      {"+"        \"name\": \"Metropolitan Area of Porto\","+"        \"description\": \"NUTS 3 subregion of Portugal\","+"        \"order\": 7,"+"        \"adminLevel\": 5,"+"        \"wikidataId\": \"Q2652989\""+"      },"+"      {"+"        \"name\": \"Porto\","+"        \"description\": \"municipality and city in Portugal\","+"        \"order\": 8,"+"        \"adminLevel\": 7,"+"        \"wikidataId\": \"Q36433\","+"        \"geonameId\": 2735943"+"      },"+"      {"+"        \"name\": \"Cedofeita, Santo Ildefonso, Se, Miragaia, Sao Nicolau e Vitoria\","+"        \"description\": \"civil parish in Porto\","+"        \"order\": 9,"+"        \"adminLevel\": 8,"+"        \"wikidataId\": \"Q15905280\""+"      },"+"      {"+"        \"name\": \"Cedofeita\","+"        \"description\": \"locality and former civil parish in Portugal\","+"        \"order\": 10,"+"        \"adminLevel\": 9,"+"        \"wikidataId\": \"Q1052596\","+"        \"geonameId\": 8012855"+"      }"+"    ],"+"    \"informative\": ["+"      {"+"        \"name\": \"Europe\","+"        \"description\": \"continent\","+"        \"order\": 1,"+"        \"isoCode\": \"EU\","+"        \"wikidataId\": \"Q46\","+"        \"geonameId\": 6255148"+"      },"+"      {"+"        \"name\": \"Iberian Peninsula\","+"        \"description\": \"peninsula located in the extreme southwest of Europe\","+"        \"order\": 2,"+"        \"wikidataId\": \"Q12837\""+"      },"+"      {"+"        \"name\": \"Europe/Lisbon\","+"        \"description\": \"time zone\","+"        \"order\": 4"+"      }"+"    ]"+"  }"+"}");


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
    }

    @Test
    void testGetCityByLatAndLon_FirstAPI() throws IOException {
        // Arrange
        Double lat = 41.15;
        Double lon = -8.62;

        String cityName = "Porto";

        when(httpClient.makeRequestByLAtAndLon_extra(anyString(),anyString())).thenReturn("{\"latitude\": 41.15,"+"  \"longitude\": -8.62,"+"  \"continent\": \"Europe\","+"  \"lookupSource\": \"coordinates\","+"  \"continentCode\": \"EU\","+"  \"localityLanguageRequested\": \"en\","+"  \"city\": \"Porto\","+"  \"countryName\": \"Portugal\","+"  \"countryCode\": \"PT\","+"  \"postcode\": \"\","+"  \"principalSubdivision\": \"Distrito do Porto\","+"  \"principalSubdivisionCode\": \"PT-13\","+"  \"plusCode\": \"8CHH592J+22\","+"  \"locality\": \"Cedofeita\","+"  \"localityInfo\": {"+"    \"administrative\": ["+"      {"+"        \"name\": \"Portugal\","+"        \"description\": \"country in Southwestern Europe\","+"        \"order\": 3,"+"        \"adminLevel\": 2,"+"        \"isoCode\": \"PT\","+"        \"wikidataId\": \"Q45\","+"        \"geonameId\": 2264397"+"      },"+"      {"+"        \"name\": \"Norte Region\","+"        \"description\": \"NUTS 2 region of Portugal\","+"        \"order\": 5,"+"        \"adminLevel\": 4,"+"        \"wikidataId\": \"Q27662\""+"      },"+"      {"+"        \"name\": \"Distrito do Porto\","+"        \"description\": \"district of Portugal\","+"        \"order\": 6,"+"        \"adminLevel\": 6,"+"        \"isoCode\": \"PT-13\","+"        \"wikidataId\": \"Q322792\","+"        \"geonameId\": 2735941"+"      },"+"      {"+"        \"name\": \"Metropolitan Area of Porto\","+"        \"description\": \"NUTS 3 subregion of Portugal\","+"        \"order\": 7,"+"        \"adminLevel\": 5,"+"        \"wikidataId\": \"Q2652989\""+"      },"+"      {"+"        \"name\": \"Porto\","+"        \"description\": \"municipality and city in Portugal\","+"        \"order\": 8,"+"        \"adminLevel\": 7,"+"        \"wikidataId\": \"Q36433\","+"        \"geonameId\": 2735943"+"      },"+"      {"+"        \"name\": \"Cedofeita, Santo Ildefonso, Se, Miragaia, Sao Nicolau e Vitoria\","+"        \"description\": \"civil parish in Porto\","+"        \"order\": 9,"+"        \"adminLevel\": 8,"+"        \"wikidataId\": \"Q15905280\""+"      },"+"      {"+"        \"name\": \"Cedofeita\","+"        \"description\": \"locality and former civil parish in Portugal\","+"        \"order\": 10,"+"        \"adminLevel\": 9,"+"        \"wikidataId\": \"Q1052596\","+"        \"geonameId\": 8012855"+"      }"+"    ],"+"    \"informative\": ["+"      {"+"        \"name\": \"Europe\","+"        \"description\": \"continent\","+"        \"order\": 1,"+"        \"isoCode\": \"EU\","+"        \"wikidataId\": \"Q46\","+"        \"geonameId\": 6255148"+"      },"+"      {"+"        \"name\": \"Iberian Peninsula\","+"        \"description\": \"peninsula located in the extreme southwest of Europe\","+"        \"order\": 2,"+"        \"wikidataId\": \"Q12837\""+"      },"+"      {"+"        \"name\": \"Europe/Lisbon\","+"        \"description\": \"time zone\","+"        \"order\": 4"+"      }"+"    ]"+"  }"+"}");


        when(cityCache.get(anyString())).thenReturn(null);
        when(httpClient.makeRequestByLAtAndLon(anyString(), anyString())).thenReturn("{\"location\":{\"name\":\"Oporto\",\"region\":\"Porto\",\"country\":\"Portugal\",\"lat\":41.15,\"lon\":-8.62,\"tz_id\":\"Europe/Lisbon\",\"localtime_epoch\":1680800697,\"localtime\":\"2023-04-06 18:04\"},\"current\":{\"last_updated_epoch\":1680800400,\"last_updated\":\"2023-04-06 18:00\",\"temp_c\":22.0,\"temp_f\":71.6,\"is_day\":1,\"condition\":{\"text\":\"Sunny\",\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/113.png\",\"code\":1000},\"wind_mph\":11.9,\"wind_kph\":19.1,\"wind_degree\":350,\"wind_dir\":\"N\",\"pressure_mb\":1014.0,\"pressure_in\":29.94,\"precip_mm\":0.0,\"precip_in\":0.0,\"humidity\":35,\"cloud\":0,\"feelslike_c\":24.5,\"feelslike_f\":76.1,\"vis_km\":10.0,\"vis_miles\":6.0,\"uv\":6.0,\"gust_mph\":7.4,\"gust_kph\":11.9,\"air_quality\":{\"co\":220.3000030517578,\"no2\":3.299999952316284,\"o3\":114.4000015258789,\"so2\":2.0999999046325684,\"pm2_5\":9.800000190734863,\"pm10\":14.199999809265137,\"us-epa-index\":1,\"gb-defra-index\":1}}}");

        // Act
        City actualCity = cityService.getCityByLatAndLon(lat, lon);


        // Assert
        assertEquals(dummyCity.getName(), actualCity.getName());
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, times(1)).put(cityName.toLowerCase(), actualCity); // using actual city, cause dummy city will have diferent values on air quality

    }
    @Test
    void testGetCityByName_SecondAPI() throws IOException {
        // Arrange
        String cityName = "Oporto";
        when(cityCache.get(anyString())).thenReturn(null);
        when(httpClient.makeRequestByName(anyString())).thenReturn("{"+"  \"CO\": {"+"    \"concentration\": 226.97,"+"    \"aqi\": 2"+"  },"+"  \"NO2\": {"+"    \"concentration\": 2.57,"+"    \"aqi\": 3"+"  },"+"  \"O3\": {"+"    \"concentration\": 120.16,"+"    \"aqi\": 201"+"  },"+"  \"SO2\": {"+"    \"concentration\": 2.03,"+"    \"aqi\": 2"+"  },"+"  \"PM2.5\": {"+"    \"concentration\": 9.98,"+"    \"aqi\": 32"+"  },"+"  \"PM10\": {"+"    \"concentration\": 14.49,"+"    \"aqi\": 13"+"  },"+"  \"overall_aqi\": 201"+"}");
        when(httpClient.makeRequestByName_extra(anyString())).thenReturn("{" +
                "\"name\": \"Porto\"," +
                "\"latitude\": 41.1494512," +
                "\"longitude\": -8.6107884," +
                "\"country\": \"PT\"" +
                "  }");

        // Act
        City actualCity = cityService.getCityByName(cityName.toLowerCase());

        // Assert
        assertEquals(dummyCity2.getName(), actualCity.getName());
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, times(1)).put(cityName.toLowerCase(), actualCity); // using actual city, cause dummy city will have diferent values on air quality
    }

    @Test
    void testGetCityByLatAndLon_SecondAPI() throws IOException {
        // Arrange
        Double lat = 41.15;
        Double lon = -8.62;

        String cityName = "Porto";

        when(cityCache.get(anyString())).thenReturn(null);
        when(httpClient.makeRequestByLAtAndLon(anyString(),anyString())).thenReturn("{"+"  \"CO\": {"+"    \"concentration\": 226.97,"+"    \"aqi\": 2"+"  },"+"  \"NO2\": {"+"    \"concentration\": 2.57,"+"    \"aqi\": 3"+"  },"+"  \"O3\": {"+"    \"concentration\": 120.16,"+"    \"aqi\": 201"+"  },"+"  \"SO2\": {"+"    \"concentration\": 2.03,"+"    \"aqi\": 2"+"  },"+"  \"PM2.5\": {"+"    \"concentration\": 9.98,"+"    \"aqi\": 32"+"  },"+"  \"PM10\": {"+"    \"concentration\": 14.49,"+"    \"aqi\": 13"+"  },"+"  \"overall_aqi\": 201"+"}");
        when(httpClient.makeRequestByLAtAndLon_extra(anyString(),anyString())).thenReturn("{\"latitude\": 41.15,"+"  \"longitude\": -8.62,"+"  \"continent\": \"Europe\","+"  \"lookupSource\": \"coordinates\","+"  \"continentCode\": \"EU\","+"  \"localityLanguageRequested\": \"en\","+"  \"city\": \"Porto\","+"  \"countryName\": \"Portugal\","+"  \"countryCode\": \"PT\","+"  \"postcode\": \"\","+"  \"principalSubdivision\": \"Distrito do Porto\","+"  \"principalSubdivisionCode\": \"PT-13\","+"  \"plusCode\": \"8CHH592J+22\","+"  \"locality\": \"Cedofeita\","+"  \"localityInfo\": {"+"    \"administrative\": ["+"      {"+"        \"name\": \"Portugal\","+"        \"description\": \"country in Southwestern Europe\","+"        \"order\": 3,"+"        \"adminLevel\": 2,"+"        \"isoCode\": \"PT\","+"        \"wikidataId\": \"Q45\","+"        \"geonameId\": 2264397"+"      },"+"      {"+"        \"name\": \"Norte Region\","+"        \"description\": \"NUTS 2 region of Portugal\","+"        \"order\": 5,"+"        \"adminLevel\": 4,"+"        \"wikidataId\": \"Q27662\""+"      },"+"      {"+"        \"name\": \"Distrito do Porto\","+"        \"description\": \"district of Portugal\","+"        \"order\": 6,"+"        \"adminLevel\": 6,"+"        \"isoCode\": \"PT-13\","+"        \"wikidataId\": \"Q322792\","+"        \"geonameId\": 2735941"+"      },"+"      {"+"        \"name\": \"Metropolitan Area of Porto\","+"        \"description\": \"NUTS 3 subregion of Portugal\","+"        \"order\": 7,"+"        \"adminLevel\": 5,"+"        \"wikidataId\": \"Q2652989\""+"      },"+"      {"+"        \"name\": \"Porto\","+"        \"description\": \"municipality and city in Portugal\","+"        \"order\": 8,"+"        \"adminLevel\": 7,"+"        \"wikidataId\": \"Q36433\","+"        \"geonameId\": 2735943"+"      },"+"      {"+"        \"name\": \"Cedofeita, Santo Ildefonso, Se, Miragaia, Sao Nicolau e Vitoria\","+"        \"description\": \"civil parish in Porto\","+"        \"order\": 9,"+"        \"adminLevel\": 8,"+"        \"wikidataId\": \"Q15905280\""+"      },"+"      {"+"        \"name\": \"Cedofeita\","+"        \"description\": \"locality and former civil parish in Portugal\","+"        \"order\": 10,"+"        \"adminLevel\": 9,"+"        \"wikidataId\": \"Q1052596\","+"        \"geonameId\": 8012855"+"      }"+"    ],"+"    \"informative\": ["+"      {"+"        \"name\": \"Europe\","+"        \"description\": \"continent\","+"        \"order\": 1,"+"        \"isoCode\": \"EU\","+"        \"wikidataId\": \"Q46\","+"        \"geonameId\": 6255148"+"      },"+"      {"+"        \"name\": \"Iberian Peninsula\","+"        \"description\": \"peninsula located in the extreme southwest of Europe\","+"        \"order\": 2,"+"        \"wikidataId\": \"Q12837\""+"      },"+"      {"+"        \"name\": \"Europe/Lisbon\","+"        \"description\": \"time zone\","+"        \"order\": 4"+"      }"+"    ]"+"  }"+"}");

        // Act
        City actualCity = cityService.getCityByLatAndLon(lat, lon);

        // Assert
        assertEquals(dummyCity2.getName(), actualCity.getName());
        verify(cityCache, times(1)).get(cityName.toLowerCase());
        verify(cityCache, times(1)).put(cityName.toLowerCase(), actualCity); // using actual city, cause dummy city will have diferent values on air quality

    }
}

