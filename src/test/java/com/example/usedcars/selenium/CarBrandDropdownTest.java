package com.example.usedcars.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.util.List;
import java.util.stream.Collectors;

public class CarBrandDropdownTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Podesi putanju do chromedriver-a ako je potrebno
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBrandDropdownContainsAllBrands() {
        driver.get("http://localhost:3000/put-oglas"); // promeni URL po potrebi

        // Sačekaj da se dropdown pojavi i napuni
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement brandSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("brand-select")));

        // Prikupi sve opcije iz dropdowna
        List<WebElement> options = brandSelect.findElements(By.tagName("option"));
        List<String> actualBrands = options.stream().map(WebElement::getText).collect(Collectors.toList());

        // Primer očekivanih marki (prilagodi prema bazi)
        List<String> expectedBrands = List.of("AC", "Acura", "Alfa Romeo", "Alpina", "Alpine", "Aro", "Asia Motors");

        // Proveri da li su sve očekivane marke prisutne
        Assertions.assertTrue(actualBrands.containsAll(expectedBrands), "Nisu sve marke prikazane u dropdownu!");
    }
}
