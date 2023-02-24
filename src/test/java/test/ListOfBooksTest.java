package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.BookStorePage;

import java.io.IOException;
import java.util.List;


public class ListOfBooksTest {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(ListOfBooksTest.class);

    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test(description = "Test matching books on the page and in the API response")
    public void matchingBooksOnThePageAndInTheAPIResponseTest() throws IOException {
        logger.info("Start test matching books on the page and in the API response");
        List<String> titlesFromPage = new BookStorePage(driver).openPage().getTitlesOfBooksFromPage();
        logger.info("Opened the page and took out the titles of the books from there");
        List<String> titlesFromAPI = new BookStorePage(driver).getTitlesOfBooksFromAPI();
        logger.info("Sent a request and received book titles from the response");
        Assert.assertEquals(titlesFromPage,titlesFromAPI);
        logger.info("List comparison result: " + titlesFromAPI.equals(titlesFromPage));
    }
    @AfterMethod(alwaysRun = true)
    public void browserTearDown(){
        driver.quit();
        driver=null;
    }
}
