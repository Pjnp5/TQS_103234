package tqs.Hw1.Models;

public class City {
    private String name; // city name
    private String country; // country code where city is located
    private Double latitude; // latitude of city
    private Double longitude; // longitude of city
    private long lastAcess; // the last time this city was accessed
    private Double co; // carbon monoxide level
    private Double no2; // nitrogen dioxide level
    private Double o3; // ozone level
    private Double so2; // sulfur dioxide level
    private Double pm2_5; // fine particulate matter level with diameter of less than 2.5 micrometers
    private Double pm10; // inhalable particulate matter level with diameter of less than 10 micrometers

    private Integer hitCount = 0; // number of times this city has been accessed

    /*---------- Constructors ----------*/

    public City(String name, String country, Double lat, Double lon, Double co, Double no2, Double o3, Double so2, Double pm2_5, Double pm10) {
        // Constructor for initializing city with its properties
        this.name = name;
        this.country = country;
        this.latitude = lat;
        this.longitude = lon;
        this.hitCount++;
        this.co = co;
        this.no2 = no2;
        this.o3 = o3;
        this.so2 = so2;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        lastAcess = System.currentTimeMillis() / 1000;
    }

    public City() {
        // Empty constructor for City class
    }

    /*---------- Setters, Getters & Counters ----------*/

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

    public Integer getHitCounter() {
        return hitCount;
    }

    public Double getCo() {
        return co;
    }

    public Double getNo2() {
        return no2;
    }

    public Double getO3() {
        return o3;
    }

    public Double getPm2_5() {
        return pm2_5;
    }

    public Double getPm10() {
        return pm10;
    }

    public Double getSo2() {
        return so2;
    }

    public long getLastAcess(){
        return lastAcess;
    }

    public void setLastAcess(){
        lastAcess = System.currentTimeMillis() / 1000;
    }

    // Increment the hit counter of the city
    public void incrementHitCounter() {
        hitCount++;
    }

    @Override
    public String toString() {
        // Override toString() to print City object properties
        return "City{name='" + name + '\'' +
                ", countryCode='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", co=" + co +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", so2=" + so2 +
                ", pm2_5=" + pm2_5 +
                ", pm10=" + pm10 +
                '}';
    }
}
