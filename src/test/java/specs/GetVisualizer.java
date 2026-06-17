package specs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetVisualizer {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void openVisualizer() {

        login();

        driver.get("http://localhost:5173/visualizer");

        wait.until(ExpectedConditions.urlContains("/visualizer"));

        System.out.println("Visualizer page opened successfully");
    }

    private void login() {
        driver.get("http://localhost:5173/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                .sendKeys("premthakare@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("premthakare");

        driver.findElement(
                By.xpath("//*[@id='root']/div/main/div/div/div/form/div[3]/button"))
                .click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }
}