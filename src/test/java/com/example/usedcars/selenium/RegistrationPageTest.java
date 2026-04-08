package com.example.usedcars.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPageTest {
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
    public void testRegistrationFormVisible() {
        driver.get("http://localhost:3000/register");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement registerBtn = driver.findElement(By.id("register-btn"));
        Assertions.assertTrue(username.isDisplayed());
        Assertions.assertTrue(email.isDisplayed());
        Assertions.assertTrue(password.isDisplayed());
        Assertions.assertTrue(registerBtn.isDisplayed());
    }
}
