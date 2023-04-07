package tqs.Hw1.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.Hw1.Controllers.CityController;
import tqs.Hw1.Models.City;
import tqs.Hw1.Services.CityService;


import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CityController.class)
class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService cityService;

    @Test
    void getCityByNameTest() throws Exception {
        City porto = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);

        when(cityService.getCityByName("Oporto")).thenReturn(porto);

        mvc.perform(get("/city/{name}", "Oporto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Oporto"));
    }

    @Test
    void getCityByLatAndLonTest() throws Exception {
        City porto = new City("Oporto","Portugal",41.15, -8.62, 360.5, 14.89, 46.5, 1.89, 21.29, 25.10);

        when(cityService.getCityByLatAndLon(41.15, -8.62)).thenReturn(porto);

        mvc.perform(get("/city/{lat}/{lon}", 41.15, -8.62).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(41.15))
                .andExpect(jsonPath("$.longitude").value(-8.62));
    }

    @Test
    void getCacheDetailsTest() throws Exception {
        Map<String, Object> cachedetails = new HashMap<>();
        cachedetails.put("hits", 1);
        cachedetails.put("misses", 1);
        cachedetails.put("requests", 2);

        when(cityService.getCacheDetails()).thenReturn(cachedetails);

        mvc.perform(get("/cacheDetails").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hits").value(1))
                .andExpect(jsonPath("$.misses").value(1))
                .andExpect(jsonPath("$.requests").value(2));
    }}
