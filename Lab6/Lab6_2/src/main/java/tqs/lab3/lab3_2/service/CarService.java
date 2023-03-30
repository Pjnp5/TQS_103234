package tqs.lab3.lab3_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.lab3.lab3_2.models.Car;
import tqs.lab3.lab3_2.repository.Repository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private Repository Repository;

    public Car saveCar(Car car){
        return Repository.save(car);
    }

    public List<Car> getAllCars(){
        return Repository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId){
        return Repository.findById(carId);
    }
}