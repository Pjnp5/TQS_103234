package org.example;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class BlazeDemoSearchSteps {
    private WebDriver driver;

    @When("I open the driver and navigate to {string}")
    public void openAndNavigate(String url){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @And("I going from {string} to {string}")
    public void ChoosePlaces(String fromPort, String toPort){
        driver.findElement(By.name("fromPort")).click();
        // 4 | select | name=fromPort | label=Philadelphia
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = '" + fromPort + "']")).click();
        }
        // 5 | click | css=.form-inline:nth-child(1) > option:nth-child(2) |
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(2)")).click();
        // 6 | click | name=toPort |
        driver.findElement(By.name("toPort")).click();
        // 7 | select | name=toPort | label=London
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = '" + toPort + "']")).click();
        }
        // 8 | click | css=.form-inline:nth-child(4) > option:nth-child(3) |
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        // 9 | click | css=.btn-primary |
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @And("I enter my data, {string}, {string}, {string}, {string}, {string}")
    public void PersonalData(String Name, String Adress, String City, String State, String ZipCode){
        // 10 | click | css=tr:nth-child(2) .btn |
        driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
        // 11 | type | id=inputName | Sou
        driver.findElement(By.id("inputName")).sendKeys(Name);
        // 12 | click | id=address |
        driver.findElement(By.id("address")).click();
        // 13 | type | id=address | 123 coisa
        driver.findElement(By.id("address")).sendKeys(Adress);
        // 14 | click | id=city |
        driver.findElement(By.id("city")).click();
        // 15 | type | id=city | wubba
        driver.findElement(By.id("city")).sendKeys(City);
        // 16 | click | id=state |
        driver.findElement(By.id("state")).click();
        // 17 | type | id=state | lubba
        driver.findElement(By.id("state")).sendKeys(State);
        // 18 | click | id=zipCode |
        driver.findElement(By.id("zipCode")).click();
        // 19 | type | id=zipCode | 9192
        driver.findElement(By.id("zipCode")).sendKeys(ZipCode);
    }
    @And("I enter my card data, {string}, {string}, {string}, {string}")
    public void CardData(String CardName, String Month, String Year, String NameOnCard){
        // 20 | select | id=cardType | label=American Express
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = '" + CardName + "']")).click();
        }
        // 21 | click | css=option:nth-child(2) |
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        // 22 | click | id=creditCardNumber |
        driver.findElement(By.id("creditCardNumber")).click();
        // 23 | type | id=creditCardNumber | 9182
        driver.findElement(By.id("creditCardNumber")).sendKeys(CardName);
        // 24 | click | id=creditCardMonth |
        driver.findElement(By.id("creditCardMonth")).click();
        // 25 | type | id=creditCardMonth | 12
        driver.findElement(By.id("creditCardMonth")).sendKeys(Month);
        // 26 | click | id=creditCardYear |
        driver.findElement(By.id("creditCardYear")).click();
        // 27 | type | id=creditCardYear | 2022
        driver.findElement(By.id("creditCardYear")).sendKeys(Year);
        // 28 | click | id=nameOnCard |
        driver.findElement(By.id("nameOnCard")).click();
        // 29 | type | id=nameOnCard | Mr G
        driver.findElement(By.id("nameOnCard")).sendKeys(NameOnCard);
        // 30 | click | css=.btn-primary |
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }


    @Then("I assert the website title,{string}, close the driver")
    public void AssertANdclose(String title){
        // 31 | assertTitle | BlazeDemo Confirmation |
        assertThat(driver.getTitle()).isEqualTo(title);
        driver.close();
    }

}
