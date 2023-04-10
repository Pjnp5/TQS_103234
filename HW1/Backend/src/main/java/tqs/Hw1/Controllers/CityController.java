package tqs.Hw1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.Hw1.Models.City;
import tqs.Hw1.Services.CityService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
public class CityController {
    @Autowired
    CityService cityService;

    // Get city by name
    @GetMapping("/name")
    public City getCityByName(@RequestParam String name) throws IOException, URISyntaxException {
        return cityService.getCityByName(name);
    }

    // Get city by latitude and longitude
    @GetMapping("/coords")
    public City getCityByLatAndLon(@RequestParam Double  lat, @RequestParam Double lon) throws IOException, URISyntaxException {
        return cityService.getCityByLatAndLon(lat, lon);
    }

    // Get cache details
    @GetMapping("/cacheDetails")
    public Map<String, Object> getCacheDetails() {
        return cityService.getCacheDetails();
    }
}
