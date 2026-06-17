package specs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BookIdea {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void bookIdeaRecommendation() {

        login();

        // Open Recommendations page
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/header/div/div/nav/button[4]")))
                .click();

        // First filter
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/div[2]/div[2]/div/button[1]")))
                .click();

        // Second filter
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/div[2]/div[3]/div/button[2]")))
                .click();

        // Third filter
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/div[2]/div[4]/div/button[2]")))
                .click();

        // Wait for recommendations to load
        By recommendationCard = By.cssSelector(".grid > div");

        WebElement card = wait.until(
                ExpectedConditions.visibilityOfElementLocated(recommendationCard));

        wait.until(ExpectedConditions.elementToBeClickable(card));

        try {
            card.click();
        } catch (Exception e) {
            // Fallback for React applications
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", card);
        }

        // Wait for details page
       By bookNowButton = By.xpath("//button[contains(.,'Book Now')]");

WebElement btn = wait.until(
        ExpectedConditions.presenceOfElementLocated(bookNowButton));

((JavascriptExecutor) driver)
        .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

wait.until(ExpectedConditions.elementToBeClickable(btn));

((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", btn);
    }

    private void login() {

        driver.get("http://localhost:5173/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")))
                .sendKeys("premthakare@gmail.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("password")))
                .sendKeys("premthakare");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='root']/div/main/div/div/div/form/div[3]/button")))
                .click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));

        System.out.println("Login successful.");
    }
}