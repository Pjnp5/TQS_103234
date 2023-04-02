package tqs.Hw1.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String country;
    private Double latitude;
    private Double longitude;
    private String localtime;



    private Integer hitCount = 0; // number of time city got called


    /*---------- Constructors ----------*/

    public City() {

    }

    public City(String name, String country, Double latitude, Double longitude, String localtime) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localtime = localtime;
        this.hitCount++;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLocaltime() {
        return localtime;
    }

    public Integer getHitCounter() {
        return hitCount;
    }

    public void incrementHitCounter() {
        hitCount++;
    }



    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryCode='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", localtime=" + localtime +
                '}';
    }
}
