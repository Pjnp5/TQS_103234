package tqs.lab3.lab3_2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue
    private Long carId;

    private String maker;
    private String model;

    public Car(){
    }
    public  Car(String model, String maker){
        this.model = model;
        this.maker = maker;
    }
}