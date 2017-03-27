package front;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by ebiz on 24/03/17.
 */


public class DashboardTest {
    private WebDriver driver;
    private String baseUrl = "http://127.0.0.1:8080/dashboard";

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/ebiz/outils/drivers/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testAddComputerRedirect() {
        driver.get(baseUrl);
        StopWatch pageLoad = new StopWatch();
        pageLoad.start();
        driver.findElement(By.xpath("//a[@id='addComputer']")).click();
        pageLoad.stop();
    }

    @Test
    public void testEditComputerRedirect() {
        driver.get(baseUrl);
        StopWatch pageLoad = new StopWatch();
        pageLoad.start();
        driver.findElement(By.xpath("//a[@id='editComputer']")).click();
        pageLoad.stop();
    }
}