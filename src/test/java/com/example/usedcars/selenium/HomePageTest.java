package com.example.usedcars.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageTitle() {
        driver.get("http://localhost:3000/");
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Polovni automobili") || title.contains("Used Cars"));
    }

    @Test
    public void testNavbarVisible() {
        driver.get("http://localhost:3000/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement navbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("nav")));
        Assertions.assertTrue(navbar.isDisplayed());
    }
}
