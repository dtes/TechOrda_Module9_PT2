package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hooks {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    @Before
    public void setUp() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();

            // Проверяем, нужно ли использовать существующий Chrome
            String reuseChrome = System.getProperty("chrome.reuse", "true");
            String debugPort = System.getProperty("chrome.debug.port", "9222");

            if ("true".equalsIgnoreCase(reuseChrome)) {
                // Подключаемся к существующему Chrome через remote debugging
                options.setExperimentalOption("debuggerAddress", "127.0.0.1:" + debugPort);
                System.out.println("Connecting to existing Chrome instance on port " + debugPort);
            } else {
                // Стандартный режим - запускаем новый Chrome
//                options.addArguments("--headless");  // Отключено для ручной аутентификации
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--start-maximized");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                System.out.println("Starting new Chrome instance");
            }

            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
    }

    @Given("I open the browser")
    public void iOpenTheBrowser() {
        if (driver == null) {
            setUp();
        }
    }

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver.get(url);
//        PageUtils.acceptAllCookies();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            String reuseChrome = System.getProperty("chrome.reuse", "true");

            if ("true".equalsIgnoreCase(reuseChrome)) {
                // В режиме переиспользования не закрываем браузер
                System.out.println("Keeping Chrome instance open (reuse mode)");
                driver = null;
                wait = null;
            } else {
                // В стандартном режиме закрываем браузер
                driver.quit();
                driver = null;
                wait = null;
                System.out.println("Chrome instance closed");
            }
        }
    }
}
