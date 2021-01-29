package stepdef;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TrackCRUDTest {
	
	private static WebDriver driver;
	private static String URL = "http://localhost:8082/";
	private Map<String, Object> vars;
	JavascriptExecutor js;
	
	@Before
	public static void init() {
		System.setProperty("webdriver.chrome.driver",
				"src/test/resources/drivers/chromedriver-87-4280/chromedriver.exe");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.setHeadless(false);
		driver = new ChromeDriver(cOptions);
		
	}
	
	@After
	public void cleanUp() {
		driver.quit();
	}
	
	@Given("^I access the choonz frontpage to test Track$")
	public void i_access_the_choonz_frontpage_to_test_Track() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8082/");
	    driver.manage().window().setSize(new Dimension(1552, 840));
	}	

	@When("^I create a track$")
	public void i_create_a_track() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 driver.findElement(By.cssSelector(".nav-item:nth-child(1) > .nav-link")).click();
		    //Create an artist
		 WebDriverWait wait = new WebDriverWait(driver, 5);  
		 
		 wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='createTrackButton']")));
		    driver.findElement(By.xpath("//button[@id='createTrackButton']")).click();
		    driver.findElement(By.xpath("//*[@id='createTrackButton']")).click();
		    driver.get("http://localhost:8082/html/create_track.html");		    
		    driver.findElement(By.id("title")).click();
		    driver.findElement(By.id("title")).sendKeys("Test");
		    driver.findElement(By.id("lyrics")).sendKeys("Testlyric");
		    driver.findElement(By.id("duration")).sendKeys("1");
		    driver.findElement(By.id("artist")).sendKeys("1");
		    driver.findElement(By.id("genre")).sendKeys("1");
		    driver.findElement(By.id("album")).sendKeys("1");
		    driver.findElement(By.id("playlist")).sendKeys("1");
		    wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("#submitTrackButton")));
		    assertEquals("http://localhost:8082/html/create_track.html", driver.getCurrentUrl());
		    driver.findElement(By.xpath("//*[@id='submitTrackButton']")).click();
		    Thread.sleep(5000);	}

	@When("^I search for it by track name$")
	public void i_search_for_it_by_track_name() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		// Write code here that turns the phrase above into concrete actions
				WebDriverWait wait = new WebDriverWait(driver, 5);
			    driver.get("http://localhost:8082/html/tracks.html");		

				wait.until(
						ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/button[2]")));
				driver.findElement(By.cssSelector(".btn-outline-info")).click();
			    driver.findElement(By.id("track-input")).click();
			    driver.findElement(By.id("track-input")).sendKeys("Test");
			    driver.findElement(By.id("searchTrackButton")).click();
			    Thread.sleep(5000);
			    assertThat(driver.findElement(By.cssSelector("div:nth-child(6) > div.card-body > h5")).getText(), is("TEST"));
				}

	@Then("^I can update and delete the track$")
	public void i_can_update_and_delete_the_track() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		  Thread.sleep(1000);
			driver.findElement(By.id("title")).click();
		      driver.findElement(By.id("title")).clear();
		      driver.findElement(By.id("title")).sendKeys("2");
		      driver.findElement(By.id("lyrics")).clear();
		      driver.findElement(By.id("lyrics")).sendKeys("2");
		      driver.findElement(By.id("duration")).clear();
		      driver.findElement(By.id("duration")).sendKeys("2");
		      driver.findElement(By.id("artist")).clear();
		      driver.findElement(By.id("artist")).sendKeys("2");
		      driver.findElement(By.id("genre")).clear();
		      driver.findElement(By.id("genre")).sendKeys("2");
		      driver.findElement(By.id("album")).clear();
		      driver.findElement(By.id("album")).sendKeys("2");
		      driver.findElement(By.id("playlist")).clear();
		      driver.findElement(By.id("playlist")).sendKeys("2");
		      Thread.sleep(5000);
		      driver.findElement(By.cssSelector(".btn:nth-child(6)")).click();
		
		//Delete
		driver.findElement(By.cssSelector(".card:nth-child(6) .btn-danger")).click();
	    Thread.sleep(5000);	}

	
}


















