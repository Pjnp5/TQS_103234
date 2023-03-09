package tqs.lab3.lab3_2.ServiceTest;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.lab3.lab3_2.models.Car;
import tqs.lab3.lab3_2.repository.Repository;
import tqs.lab3.lab3_2.service.CarService;

import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;

import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock(lenient = true)
    private Repository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        Car bmw = new Car("Series 1", "BMW");
        // Need to set Id since we would only get the Ids of the Objects if they were stored in a DB,
        bmw.setCarId(1L);
        Car audi = new Car("A5", "Audi");
        List<Car> cars = Arrays.asList(bmw,audi);
        given(carRepository.findById(bmw.getCarId())).willReturn(Optional.of(bmw));
        given(carRepository.findById(audi.getCarId())).willReturn(Optional.of(audi));
        given(carRepository.findById(-5555L)).willReturn(Optional.empty());
        given(carRepository.findAll()).willReturn(cars);

    }
    @Test
    public void whenValidId_thenCarShouldBeFound() {
        Long id = 1L;
        Car found = carService.getCarDetails(id).orElse(null);
        //assertJ Library!
        assertThat(found).isNotNull();
        assertThat(found.getCarId()).isEqualTo(id);
        verifyFindByIdIsCalledOnce();
    }
    @Test
    public void whenInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> not_found = carService.getCarDetails(-5555L);
        verifyFindByIdIsCalledOnce();
        assertThat(not_found).isEmpty();
    }

    @Test
    public void given2Cars_whengetAll_thenReturn2Records() {
        Car bmw = new Car("Series 1", "BMW");
        Car audi = new Car("A5", "Audi");
        List<Car> allCars = carService.getAllCars();
        verifyFindAllCarsisCalledOnce();
        assertThat(allCars)
                .hasSize(2)
                .extracting(Car::getModel)
                .contains(bmw.getModel(),bmw.getModel());
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }
    private void verifyFindAllCarsisCalledOnce(){
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}