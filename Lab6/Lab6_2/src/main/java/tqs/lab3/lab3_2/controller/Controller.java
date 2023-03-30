package tqs.lab3.lab3_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.lab3.lab3_2.models.Car;
import tqs.lab3.lab3_2.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        Car carsaved =  carService.saveCar(car);
        return new ResponseEntity<>(carsaved, HttpStatus.CREATED);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars(){
        List<Car> cars = carService.getAllCars();
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCar(@PathVariable long id){
        Car car = carService.getCarDetails(id).orElse(null);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }


}