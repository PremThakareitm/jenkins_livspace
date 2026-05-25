package specs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void loginShouldOpenDashboard() {
        login();

        WebElement welcomeHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(., 'Welcome back,!')]") ));

        Assert.assertTrue(welcomeHeading.getText().contains("Prem"), "Dashboard welcome heading was not shown after login.");
    }

    private void login() {
        driver.get("http://localhost:5173/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("premthakare@gmail.com");
        driver.findElement(By.id("password")).sendKeys("premthakare");
        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div/form/div[3]/button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(., 'Welcome back, Prem Thakare!')]")));
    }
}