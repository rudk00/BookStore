package page;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import waits.CustomConditions;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BookStorePage {
    private final WebDriver driver;
    @FindBy(xpath = "//span[@class=\"mr-2\"]")
    List<WebElement> titlesOfBooks;
    public BookStorePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public BookStorePage openPage(){
        driver.get("https://demoqa.com/books");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXsCompleted());
        return this;
    }
    public List<String> getTitlesOfBooksFromPage(){
        List<String> titlesFromPage = new ArrayList<>();
        for (WebElement titleOfBook : titlesOfBooks) {
            titlesFromPage.add(titleOfBook.getText());
        }
        return titlesFromPage;
    }
    public List<String> getTitlesOfBooksFromAPI() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://demoqa.com/BookStore/v1/Books");
        CloseableHttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode booksNode = rootNode.get("books");
        List<String> titlesFromAPI = new ArrayList<>();
        for (JsonNode bookNode : booksNode) {
            titlesFromAPI.add(bookNode.get("title").asText());
        }
        return titlesFromAPI;
    }
}
