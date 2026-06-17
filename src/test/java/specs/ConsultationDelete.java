package specs;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ConsultationDelete {

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
    public void deleteConsultation() {

        login();

        // Click Consultation menu
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/header/div/div/nav/div/a[2]")))
                .click();

        // Open consultation details
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div[3]/div[2]/div/div[1]/a")))
                .click();

        // Delete button
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/div[2]/div[1]/div/div/div[2]/button")))
                .click();

        // Handle JavaScript confirm popup
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        Assert.assertTrue(
                alertText.contains("Delete this consultation"),
                "Unexpected confirmation message: " + alertText);

        alert.accept();

        // Optional: wait until alert disappears
        wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));

        System.out.println("Consultation deleted successfully.");
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