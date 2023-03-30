package tqs.lab3.lab3_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.lab3.lab3_2.models.Car;

import java.util.Optional;

public interface Repository extends JpaRepository<Car,Long> {
    //Find By Id is already implemented, no need to override
    // same applies to findAll()
}