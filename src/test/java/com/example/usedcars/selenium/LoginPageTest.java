package com.example.usedcars.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageTest {
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
    public void testLoginFormVisible() {
        driver.get("http://localhost:3000/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-btn"));
        Assertions.assertTrue(username.isDisplayed());
        Assertions.assertTrue(password.isDisplayed());
        Assertions.assertTrue(loginBtn.isDisplayed());
    }

    @Test
    public void testLoginWithInvalidCredentialsShowsError() {
        driver.get("http://localhost:3000/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-btn"));
        username.sendKeys("wronguser");
        password.sendKeys("wrongpass");
        loginBtn.click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assertions.assertTrue(error.isDisplayed());
    }
}
