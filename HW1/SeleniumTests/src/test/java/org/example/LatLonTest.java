package org.example;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SeleniumJupiter.class)
class LatLonTest {

    @Test
    public void TestLatandLon(FirefoxDriver driver) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        // Test name: SeleniumTest
        // Step # | name | target | value
        // 1 | open | / |
        driver.get("http://127.0.0.1:3000/");
        // 2 | setWindowSize | 1680x1050 |
        driver.manage().window().setSize(new Dimension(1680, 1050));
        // 3 | getCacheValues and treat them
        int requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        int hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        int misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));
        Thread.sleep(1100);
        // 4 | click | id=rc-tabs-1-tab-latlon |
        driver.findElement(By.id("rc-tabs-1-tab-latlon")).click();
        // 5 | click | id=Latitude |
        driver.findElement(By.id("Latitude")).click();
        // 6 | type | id=Latitude | 38.72
        driver.findElement(By.id("Latitude")).sendKeys("41.15");
        // 7 | click | id=Longitude |
        driver.findElement(By.id("Longitude")).click();
        // 8 | type | id=Longitude | Aveiro
        driver.findElement(By.id("Longitude")).sendKeys("-8.62");
        // 9 | click | css=.ant-btn-primary > span |
        driver.findElement(By.cssSelector(".ant-btn-primary > span")).click();
        // 10 | doubleClick | css=.ant-table-cell-row-hover:nth-child(1) |
        WebElement table_body = driver.findElement(By.className("ant-table-tbody"));
        WebElement table_row = table_body.findElement(By.className("ant-table-row-level-0"));
        List<WebElement> table_cell = table_row.findElements(By.className("ant-table-cell"));
        // 11 | AssertInfo
        int new_requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        int new_hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        int new_misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));
        Thread.sleep(1100);

        assertThat(table_cell.get(0).getText()).isEqualTo("Oporto");
        assertThat(table_cell.get(1).getText()).isEqualTo("Portugal");
        assertThat(table_cell.get(2).getText()).isEqualTo("41.15");
        assertThat(table_cell.get(3).getText()).isEqualTo("-8.62");

        assertThat(new_requests).isEqualTo(requests + 1);
        assertThat(new_hits).isEqualTo(hits);
        assertThat(new_misses).isEqualTo(misses + 1);

        // 12 | click | css=.ant-btn-primary > span |
        driver.findElement(By.cssSelector(".ant-btn-primary > span")).click();

        // 13 | AssertInfo
        requests = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(1) > span")).getText().replace("Requests: ", ""));
        hits = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(2) > span")).getText().replace("Hits: ", ""));
        misses = Integer.parseInt(driver.findElement(By.cssSelector("li:nth-child(3) > span")).getText().replace("Misses: ", ""));

        assertThat(table_cell.get(0).getText()).isEqualTo("Oporto");
        assertThat(table_cell.get(1).getText()).isEqualTo("Portugal");
        assertThat(table_cell.get(2).getText()).isEqualTo("41.15");
        assertThat(table_cell.get(3).getText()).isEqualTo("-8.62");


        assertThat(requests).isEqualTo(new_requests + 1);
        assertThat(hits).isEqualTo(new_hits + 1);
        assertThat(misses).isEqualTo(new_misses);
        // 14 | close |  |
        driver.close();
    }
}
