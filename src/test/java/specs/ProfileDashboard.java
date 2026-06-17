package specs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProfileDashboard {
    
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
    public void updateProfile() {

        login();

        // Click first navigation link
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/header/div/div/nav/div/a[1]")))
                .click();

        // Click edit/add profile button
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/div/div[1]/div[3]/button")))
                .click();

        // Enter surname
        WebElement surnameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='root']/div/main/div/div/div/div/div[2]/form/div[1]/h2/input")));

        surnameField.clear();
        surnameField.sendKeys("Thakare");

        // Click save button
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/div/div[1]/div[3]/button[1]")))
                .click();
    }

    private void login() {
        driver.get("http://localhost:5173/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("premthakare@gmail.com");
        driver.findElement(By.id("password")).sendKeys("premthakare");
        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div/form/div[3]/button")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        
    }
}