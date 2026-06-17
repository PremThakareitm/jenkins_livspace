package specs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ConsultationAdd {
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
    public void bookingConsultationShouldShowSuccessMessage() {
        login();
        bookConsultation();

        Assert.assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Consultation Scheduled!")),
                "Success heading was not shown after scheduling consultation.");
        Assert.assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
                "Thank you for choosing Luxury Interiors. We'll send you a confirmation email with all the details.")),
                "Success message was not shown after scheduling consultation.");
    }

    private void login() {
        driver.get("http://localhost:5173/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("premthakare@gmail.com");
        driver.findElement(By.id("password")).sendKeys("premthakare");
        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div/form/div[3]/button")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        
    }

    private void bookConsultation() {
        driver.get("http://localhost:5173/consultation");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/main/div/div/div/div[2]/div/div/div/div[2]/div/button[2]")))
                .click();

        Select propertyType = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select"))));
        propertyType.selectByVisibleText("Penthouse");

        Select budgetRange = new Select(driver.findElements(By.cssSelector("select")).get(1));
        budgetRange.selectByVisibleText("Above ₹1Cr");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Continue']"))).click();

        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Select Date']")));
        setInputValue(dateInput, "May 26, 2026");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='09:00 AM']"))).click();

        WebElement scheduleContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Continue']")));
        clickElement(scheduleContinueButton);

        driver.findElement(By.xpath("//textarea[contains(@placeholder,'Tell us more about your project')]")).sendKeys("Please help us book this consultation.");

        WebElement scheduleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Schedule Consultation']")));
        clickElement(scheduleButton);
    }

    private void setInputValue(WebElement element, String value) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        element,
                        value);
                return;
            } catch (StaleElementReferenceException staleElementReferenceException) {
                if (attempt == 1) {
                    throw staleElementReferenceException;
                }

                element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Select Date']")));
            }
        }
    }

    private void clickElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}