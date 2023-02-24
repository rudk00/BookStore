package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import waits.CustomConditions;

import java.time.Duration;





public class LoginPage {
    private final WebDriver driver;
    @FindBy(xpath = "//input[@id=\"userName\"]")
    WebElement userNameInput;
    @FindBy(xpath = "//input[@id=\"password\"]")
    WebElement passwordInput;
    @FindBy(xpath = "//button[@id=\"login\"]")
    WebElement loginButton;
    @FindBy(xpath = "//p[text()=\"Invalid username or password!\"]")
    WebElement invalidDataText;
    @FindBy(xpath = "//input[@class=\"mr-sm-2 is-invalid form-control\"]")
    WebElement emptyFields;


    public LoginPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    private void enteringAndSendingData(String userName,String password){
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,200)");
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
    public LoginPage openPage(){
        driver.get("https://demoqa.com/login");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXsCompleted());
        return this;
    }
    public ProfilePage successLogin(String userName,String password){
        enteringAndSendingData(userName,password);
        return new ProfilePage(driver);
    }
    public boolean loginWithInvalidData(String userName, String password){
        enteringAndSendingData(userName,password);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(invalidDataText));
        return invalidDataText.isDisplayed();
    }
    public boolean loginWithEmptyFields(String userName,String password){
        enteringAndSendingData(userName,password);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(emptyFields));
        return emptyFields.isDisplayed();
    }
}
