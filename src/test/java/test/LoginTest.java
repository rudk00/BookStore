package test;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginTest {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @BeforeTest
    private static void createUser(){
        logger.info("Sending a request to create a user");
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.given()
                .contentType("application/json")
                .body("""
                        {
                          "userName": "Test",
                          "password": "Test-123456$"
                        }""")
                .post("/Account/v1/User");
    }
    @Test(description = "Successful authorization in book store (Test)")
    public void successfulLoginTest(){
        logger.info("Start test successful login");
        logger.info("Sending the correct data and checking authorization");
        boolean actualResult = new LoginPage(driver)
                .openPage()
                .successLogin("Test","Test-123456$")
                .verificationOfSuccessfulAuthorization();
        if (actualResult) {
            logger.info("Authorization was successful");
        } else {
            logger.error("There were some problems");
        }
        logger.info("Finish test successful login");
        Assert.assertTrue(actualResult);
    }
    @Test(description = "Unsuccessful authorization in book store (Test)")
    public void unsuccessfulLoginTest(){
        logger.info("Start test unsuccessful login");
        logger.info("Try to send invalid data");
        boolean resultWithInvalidData = new LoginPage(driver)
                .openPage()
                .loginWithInvalidData("Test","Test-12345678$");
        if (resultWithInvalidData) {
            logger.info("Invalid user name or password (correct)");
        } else {
            logger.error("There were some problems");
        }
        logger.info("Try to send empty fields");
        boolean resultWithEmptyFields = new LoginPage(driver)
                .openPage()
                .loginWithEmptyFields("","");
        if (resultWithEmptyFields) {
            logger.info("Notification: Fill in the fields (correct)");
        } else {
            logger.error("There were some problems");
        }
        logger.info("Finish test unsuccessful login");
        Assert.assertTrue(resultWithInvalidData && resultWithEmptyFields);
    }
    @AfterMethod(alwaysRun = true)
    public void browserTearDown(){
        driver.quit();
        driver=null;
    }

}
