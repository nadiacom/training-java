package front;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by ebiz on 30/03/17.
 */
public class TestUtils {

    private WebDriver driver;

    public TestUtils(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement find(String target){
        if(target.contains("//")){
            return driver.findElement(By.xpath(target));
        } else {
            return driver.findElement(By.id(target));
        }
    }

    public void click(String target){
        find(target).click();
    }

}
