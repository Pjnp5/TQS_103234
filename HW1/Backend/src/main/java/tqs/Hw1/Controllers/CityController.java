package tqs.Hw1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tqs.Hw1.Models.City;
import tqs.Hw1.Services.CityService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class CityController {
    @Autowired
    CityService cityService;

    // Get city by name
    @GetMapping("/city/{name}")
    public City getCityByName(@PathVariable(value = "name") String name) throws IOException, URISyntaxException {
        return cityService.getCityByName(name);
    }

    // Get city by latitude and longitude
    @GetMapping("/city/{lat}/{lon}")
    public City getCityByLatAndLon(@PathVariable(value = "lat") Double lat, @PathVariable(value = "lon") Double lon) throws IOException, URISyntaxException {
        return cityService.getCityByLatAndLon(lat, lon);
    }

    // Get cache details
    @GetMapping("/cacheDetails")
    public Map<String, Object> getCacheDetails() {
        return cityService.getCacheDetails();
    }
}
