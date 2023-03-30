package tqs.lab3.lab3_2.ControllerTest;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import tqs.lab3.lab3_2.models.Car;
import tqs.lab3.lab3_2.repository.Repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource( locations = "application-integrationtest.properties")
//@AutoConfigureTestDatabase
public class CarControllerRestIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Repository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateCar(){
        Car bmw = new Car("I8","BMW");
        ResponseEntity<Car> entity = restTemplate
                .postForEntity("/api/v1/cars",
                        bmw, Car.class
                );
        List<Car> cars_db = carRepository.findAll();
        assertThat(cars_db)
                .extracting(Car::getModel)
                .containsOnly("I8");
    }

    @Test
    public void givenCars_when_GetCars_then200() {
        createTestCar("I8", "BMW");
        createTestCar("A4", "Audi");
        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/v1/cars",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Car>>() {
                        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("I8", "A4");
    }
    @Test
    void getCar_returnsCarDetails() {
        createTestCar("I8","BMW");
        ResponseEntity<Car> entity = restTemplate.getForEntity("/api/v1/cars/1", Car.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).extracting(Car::getModel).isEqualTo("I8");
    }


    private void createTestCar(String model, String maker) {
        Car car = new Car(model, maker);
        carRepository.saveAndFlush(car);
    }
}
