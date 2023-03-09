package tqs.lab3.lab3_2.RepositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.lab3.lab3_2.models.Car;
import tqs.lab3.lab3_2.repository.Repository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private Repository carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenFindCarByExistingId_thenReturnCar(){
        Car car = new Car("Series 1", "BMW");
        entityManager.persistAndFlush(car);
        Car car_db = carRepository.findById(car.getCarId()).orElse(null);
        assertThat(car_db).isNotNull();
        assertThat(car_db.getCarId()).isEqualTo(car.getCarId());
        assertThat(car_db.getModel()).isEqualTo(car.getModel());
    }
    @Test
    public void whenInvalidId_thenReturnNull() {
        Car car_db = carRepository.findById(-9999L).orElse(null);
        assertThat(car_db).isNull();
    }
    @Test
    public void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car bmw = new Car("Series 1","BMW");
        Car audi = new Car("A5","Audi");
        Arrays.asList(bmw,audi).forEach(car -> {
            entityManager.persist(car);
        });
        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(2).extracting(Car::getModel).containsOnly(
                bmw.getModel(),audi.getModel()
        );
    }
}
