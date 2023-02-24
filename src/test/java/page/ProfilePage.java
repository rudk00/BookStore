package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    @FindBy(xpath = "//button[text()=\"Log out\"]")
    WebElement logOutButton;

    public ProfilePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public boolean verificationOfSuccessfulAuthorization(){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until((ExpectedConditions.urlToBe("https://demoqa.com/profile")));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until((ExpectedConditions.visibilityOf(logOutButton)));
        return logOutButton.isDisplayed();
    }
}
