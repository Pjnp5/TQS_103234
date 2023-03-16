package org.example.Tests;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static io.github.bonigarcia.seljup.BrowserType.FIREFOX;
import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class Lab4_4DockerTest {

    @Test
    void testChrome(@DockerBrowser(type = CHROME) RemoteWebDriver driver) {
        driver.get("https://blazedemo.com/");
        assertThat(driver.getTitle()).contains("BlazeDemo");
    }

}