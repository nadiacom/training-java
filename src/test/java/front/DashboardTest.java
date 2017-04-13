package front;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * Created by ebiz on 24/03/17.
 */

@Ignore
public class DashboardTest {

    private WebDriver driver;
    private String baseUrl = "http://127.0.0.1:8080/dashboard";
    private TestUtils testUtils;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
        //driver = new FirefoxDriver();
        driver = new FirefoxDriver(new FirefoxBinary(new File("path/to/your/firefox.exe")));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        testUtils = new TestUtils(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    //Redirect
    @Test
    public void testAddComputerRedirect() {
        driver.get(baseUrl);
        StopWatch pageLoad = new StopWatch();
        pageLoad.start();
        testUtils.click("//a[@id='addComputer']");
        pageLoad.stop();
    }

    @Test
    public void testEditComputerRedirect() {
        driver.get(baseUrl);
        StopWatch pageLoad = new StopWatch();
        pageLoad.start();
        testUtils.click("//a[@id='editComputer']");
        pageLoad.stop();
    }

    //Create, edit and delete tests are interdependent, they are done in the same test/thread
    @Test
    public void testCreateEditDelete () {

        driver.get("http://localhost:8080/add");

        //CREATE
        // Find view's elements
        WebElement computerName = testUtils.find("name");
        WebElement introduced = testUtils.find("introduced");
        WebElement discontinued = testUtils.find("discontinued");
        Select companyId = new Select(testUtils.find("companyId"));

        //Set values
        computerName.sendKeys("test_selenium");
        introduced.sendKeys("2015-01-01");
        discontinued.sendKeys("2017-01-01");
        companyId.selectByVisibleText("1 - Apple Inc.");
        computerName.submit(); //Submit can be used on any element inside that form.

        driver.get("http://localhost:8080/dashboard");

        //Wait for page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElement(By.id("searchbox")) != null);
            }
        });

        //Use searchbox to retrieve newly computer "test_selenium"
        WebElement searchBox = testUtils.find("searchbox");
        WebElement searchSubmit = testUtils.find("searchsubmit");
        searchBox.sendKeys("test_selenium");
        searchSubmit.click();

        //Wait to page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElements(By.tagName("tr")).size() > 1);
            }
        });

        //Get computer fields
        WebElement results = testUtils.find("results");
        WebElement firstRow = results.findElement(By.tagName("tr"));
        List<WebElement> rowCells = firstRow.findElements(By.tagName("td"));
        computerName = results.findElement(By.tagName("a"));

        //Check
        assertEquals("test_selenium", computerName.getText());
        assertEquals("2015-01-01", rowCells.get(2).getText());
        assertEquals("2017-01-01", rowCells.get(3).getText());
        assertEquals("Apple Inc.", rowCells.get(4).getText());

        //EDIT
        //Click on computer name link
        computerName.click();

        //Wait for page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElement(By.id("introduced")) != null);
            }
        });

        //Get view's elements
        computerName = testUtils.find("name");
        introduced = testUtils.find("introduced");
        discontinued = testUtils.find("discontinued");
        companyId = new Select(testUtils.find("companyId"));

        //Clear values
        computerName.clear();
        introduced.clear();
        discontinued.clear();

        //Set values
        computerName.sendKeys("test_selenium_edit");
        introduced.sendKeys("2015-02-01");
        discontinued.sendKeys("2017-02-01");
        companyId.selectByVisibleText("3 - RCA");
        computerName.submit(); //Submit can be used on any element inside that form.

        //Go to dashboard, check edition
        driver.get("http://localhost:8080/dashboard");

        //Wait for page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElement(By.id("searchbox")) != null);
            }
        });

        //Use searchbox to retrieve newly computer "test_selenium_edit"
        searchBox = testUtils.find("searchbox");
        searchSubmit = testUtils.find("searchsubmit");
        searchBox.sendKeys("test_selenium_edit");
        searchSubmit.click();

        //Wait to page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElements(By.tagName("tr")).size() > 1);
            }
        });

        //Get computer fields
        results = testUtils.find("results");
        firstRow = results.findElement(By.tagName("tr"));
        rowCells = firstRow.findElements(By.tagName("td"));
        computerName = results.findElement(By.tagName("a"));

        //Check
        assertEquals("test_selenium_edit", computerName.getText());
        assertEquals("2015-02-01", rowCells.get(2).getText());
        assertEquals("2017-02-01", rowCells.get(3).getText());
        assertEquals("RCA", rowCells.get(4).getText());

        //DELETE
        //Go to dashboard
        driver.get("http://localhost:8080/dashboard");

        //Wait for page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElement(By.id("searchbox")) != null);
            }
        });

        //Use searchbox to retrieve newly computer "test_selenium_edit"
        searchBox = testUtils.find("searchbox");
        searchSubmit = testUtils.find("searchsubmit");
        searchBox.sendKeys("test_selenium_edit");
        searchSubmit.click();

        //Wait to page to be loaded
        (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver d) {
                return (d.findElements(By.tagName("tr")).size() > 1);
            }
        });
        //Edit mode
        testUtils.click("editComputer");

        //Wait for edition mode to be loaded
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("cb")));

        //Click on checkbox
        testUtils.click("cb");
        //Clieck delete button
        testUtils.click("deleteSelected");
        //Click yes on alert box
        driver.switchTo().alert().accept();
        driver.get("http://localhost:8080/dashboard");

    }
}