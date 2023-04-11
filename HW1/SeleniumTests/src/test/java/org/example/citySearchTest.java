package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class citySearchTest {

    FirefoxDriver driver;
    int requests, hits, misses, new_requests, new_hits, new_misses;

    List<WebElement> table_cell;

    @Given("a website {string}")
    public void aWebsiteHttp(String website) {
        driver = new FirefoxDriver();
        // 1 | open | / |
        driver.get(website);
        // 2 | setWindowSize | 1680x1050 |
        driver.manage().window().setSize(new Dimension(1680, 1050));
    }

    @Then("inital cache values are checked")
    public void initalCacheValuesAreChecked() throws InterruptedException {
        // 3 | getCacheValues and treat them
        requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));
        Thread.sleep(1100);
    }
    @Then("a city with name {string} is searched")
    public void aCityWithNameAveiroIsSearched(String name) {
        // 4 | click | id=Name |
        driver.findElement(By.id("Name")).click();
        // 5 | type | id=Name | Aveiro
        driver.findElement(By.id("Name")).sendKeys(name);
        // 6 | click | css=.ant-btn-primary > span |
        driver.findElement(By.cssSelector(".ant-btn-primary > span")).click();
    }

    @Then("a city latitute {string} and longitude {string} is searched")
    public void aCityLatituteAndLongitudeIsSearched(String lat, String lon) {
        // 4 | click | id=rc-tabs-1-tab-latlon |
        driver.findElement(By.id("rc-tabs-1-tab-latlon")).click();
        // 5 | click | id=Latitude |
        driver.findElement(By.id("Latitude")).click();
        // 6 | type | id=Latitude | 38.72
        driver.findElement(By.id("Latitude")).sendKeys(lat);
        // 7 | click | id=Longitude |
        driver.findElement(By.id("Longitude")).click();
        // 8 | type | id=Longitude | Aveiro
        driver.findElement(By.id("Longitude")).sendKeys(lon);
        // 9 | click | css=.ant-btn-primary > span |
        driver.findElement(By.cssSelector(".ant-btn-primary > span")).click();
    }

    @Then("the recieved values for the name and the cache values are checked")
    public void recievedValuesAndCacheValuesAreChecked_name() throws InterruptedException {
        Thread.sleep(1000);
        // 8 | doubleClick | css=.ant-table-cell-row-hover:nth-child(1) |
        WebElement table_body = driver.findElement(By.className("ant-table-tbody"));
        WebElement table_row = table_body.findElement(By.className("ant-table-row-level-0"));
        table_cell = table_row.findElements(By.className("ant-table-cell"));
        // 9 | AssertInfo
        new_requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        new_hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        new_misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));
        Thread.sleep(1100);

        assertThat(table_cell.get(0).getText()).isEqualTo("Lisbon");
        assertThat(table_cell.get(1).getText()).isEqualTo("Portugal");
        assertThat(table_cell.get(2).getText()).isEqualTo("38.72");
        assertThat(table_cell.get(3).getText()).isEqualTo("-9.13");

        assertThat(new_requests).isEqualTo(requests + 1);
        assertThat(new_hits).isEqualTo(hits);
        assertThat(new_misses).isEqualTo(misses + 1);
    }

    @Then("the recieved values for the coords and the cache values are checked")
    public void recievedValuesAndCacheValuesAreChecked_coords() throws InterruptedException {
        Thread.sleep(1000);
        // 8 | doubleClick | css=.ant-table-cell-row-hover:nth-child(1) |
        WebElement table_body = driver.findElement(By.className("ant-table-tbody"));
        WebElement table_row = table_body.findElement(By.className("ant-table-row-level-0"));
        table_cell = table_row.findElements(By.className("ant-table-cell"));
        // 9 | AssertInfo
        new_requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        new_hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        new_misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));
        Thread.sleep(1100);

        assertThat(table_cell.get(0).getText()).isEqualTo("Coimbra");
        assertThat(table_cell.get(1).getText()).isEqualTo("Portugal");
        assertThat(table_cell.get(2).getText()).isEqualTo("40.2");
        assertThat(table_cell.get(3).getText()).isEqualTo("-8.42");

        assertThat(new_requests).isEqualTo(requests + 1);
        assertThat(new_hits).isEqualTo(hits);
        assertThat(new_misses).isEqualTo(misses + 1);
    }


    @Then("the city is searched again")
    public void theCityIsSearchedAgain() {
        // 10 | click | css=.ant-btn-primary > span |
        driver.findElement(By.cssSelector(".ant-btn-primary > span")).click();
    }

    @Then("the name cache values are checked again")
    public void theNameCacheValuesAreCheckedAgain() throws InterruptedException {
        // 11 | AssertInfo
        requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));

        Thread.sleep(1100);

        assertThat(table_cell.get(0).getText()).isEqualTo("Lisbon");
        assertThat(table_cell.get(1).getText()).isEqualTo("Portugal");
        assertThat(table_cell.get(2).getText()).isEqualTo("38.72");
        assertThat(table_cell.get(3).getText()).isEqualTo("-9.13");

        assertThat(requests).isEqualTo(new_requests + 1);
        assertThat(hits).isEqualTo(new_hits + 1);
        assertThat(misses).isEqualTo(new_misses);
    }

    @Then("the coords cache values are checked again")
    public void theCoordsCacheValuesAreCheckedAgain() throws InterruptedException {
        // 11 | AssertInfo
        requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));

        Thread.sleep(1100);

        assertThat(table_cell.get(0).getText()).isEqualTo("Coimbra");
        assertThat(table_cell.get(1).getText()).isEqualTo("Portugal");
        assertThat(table_cell.get(2).getText()).isEqualTo("40.2");
        assertThat(table_cell.get(3).getText()).isEqualTo("-8.42");

        assertThat(requests).isEqualTo(new_requests + 1);
        assertThat(hits).isEqualTo(new_hits + 1);
        assertThat(misses).isEqualTo(new_misses);
    }

    @Then("the website is closed")
    public void theWebsiteIsClosed() {
        driver.close();
    }



}
