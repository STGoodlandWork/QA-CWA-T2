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

public class ArtistCRUDTest {
	
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
	
	@Given("^I access the choonz frontpage to test Artist$")
	public void i_access_the_choonz_frontpage_to_test_Artist() {
	    // Write code here that turns the phrase above into concrete actions
	    driver.get("http://localhost:8082/");
	    driver.manage().window().setSize(new Dimension(1552, 840));
	}

	@When("^I create an artist$")
	public void i_create_an_artist() throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		 driver.findElement(By.cssSelector(".nav-item:nth-child(2) > .nav-link")).click();
		    //Create an artist
		 WebDriverWait wait = new WebDriverWait(driver, 5);  
		 
		 wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='createArtistButton']")));
		    driver.findElement(By.xpath("//button[@id='createArtistButton']")).click();
		    driver.findElement(By.xpath("//*[@id='createArtistButton']")).click();
		    driver.get("http://localhost:8082/html/create_artist.html");		    
		    driver.findElement(By.id("name")).click();
		    driver.findElement(By.id("name")).sendKeys("Test");
		    wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("#submitArtistButton")));
		    assertEquals("http://localhost:8082/html/create_artist.html", driver.getCurrentUrl());
		    driver.findElement(By.xpath("//*[@id='submitArtistButton']")).click();
		    Thread.sleep(5000);
	}

	@When("^I search for it by artist name$")
	public void i_search_for_it_by_artist_name() throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/button[2]")));
		driver.findElement(By.cssSelector(".btn-outline-info")).click();
	    driver.findElement(By.id("artist-input")).click();
	    driver.findElement(By.id("artist-input")).sendKeys("Test");
	    driver.findElement(By.id("searchArtistButton")).click();
	    Thread.sleep(1000);
	    assertThat(driver.findElement(By.cssSelector("#ArtistDisplay > p")).getText(), is("Test"));
	    driver.findElement(By.xpath("/html/body/button")).click();
	}

	@Then("^I can update and delete the artist$")
	public void i_can_update_and_delete_the_artist() throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		//Update that artist
	      Thread.sleep(5000);

	    driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/button[2]")).click();
	      Thread.sleep(1000);
	      driver.findElement(By.id("name")).click();
	      driver.findElement(By.id("name")).clear();
	      driver.findElement(By.id("name")).sendKeys("2");
	      Thread.sleep(1000);
	      driver.findElement(By.cssSelector("#submitArtistButton")).click();
	      Thread.sleep(2000);
	      assertThat(driver.findElement(By.cssSelector("#ArtistDisplay > div:nth-child(3) > div.card-body > h5")).getText(), is("2"));
		
		// Delete
	    driver.findElement(By.cssSelector(".card:nth-child(3) .btn-danger")).click();
	    Thread.sleep(1000);
	}

	
}

















