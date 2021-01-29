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

public class PlaylistCRUDTest {
	
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
	
	@Given("^I access the choonz frontpage to test Playlist$")
	public void i_access_the_choonz_frontpage_to_test_Playlist() throws Throwable {
		// Go to desired website
				driver.get(URL);
			    driver.manage().window().setSize(new Dimension(1936, 1056));
	}

	@When("^I create a playlist$")
	public void i_create_a_playlist() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 //Go to album page
	    driver.findElement(By.cssSelector(".nav-item:nth-child(3) > .nav-link")).click();
    //Create an album
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='createPlaylistButton']")));
	    driver.findElement(By.xpath("//button[@id='createPlaylistButton']")).click();
	    driver.get("http://localhost:8082/html/create_playlist.html");	
	    Thread.sleep(1000);
	    driver.findElement(By.id("name")).sendKeys("1");
	    driver.findElement(By.id("img")).sendKeys("1");
	    driver.findElement(By.id("description")).sendKeys("1");
	    wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("#submitPlaylistButton")));
	    assertEquals("http://localhost:8082/html/create_playlist.html", driver.getCurrentUrl());
	    driver.findElement(By.xpath("//*[@id='submitPlaylistButton']")).click();
	    Thread.sleep(1000);
	}

	@When("^I search for it by playlist name$")
	public void i_search_for_it_by_playlist_name() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 WebDriverWait wait = new WebDriverWait(driver, 5);
		    driver.get("http://localhost:8082/html/playlists.html");		
			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#allPlaylist > div:nth-child(3) > div.card-header")));
		    assertThat(driver.findElement(By.cssSelector("#allPlaylist > div:nth-child(3) > div.card-body > h5")).getText(), is("1"));
		    driver.findElement(By.cssSelector(".btn-outline-info")).click();
		    driver.findElement(By.id("playlist-input")).click();
		    driver.findElement(By.id("playlist-input")).sendKeys("1");
		    driver.findElement(By.cssSelector("#allPlaylist > div:nth-child(3) > div.card-body > button.btn.btn-success")).click();
		    Thread.sleep(5000);
		    assertThat(driver.findElement(By.cssSelector("#name > p")).getText(), is("1"));
		    driver.findElement(By.xpath("/html/body/button")).click();
		    driver.findElement(By.cssSelector(".card:nth-child(3) .card-title")).click();
		    assertThat(driver.findElement(By.cssSelector(".card:nth-child(3) .card-title")).getText(), is("1"));
		    	}

	@Then("^I can update and delete the playlist$")
	public void i_can_update_and_delete_the_playlist() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		//Update that artist
		Thread.sleep(1000);
	    driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/button[2]")).click();
	    Thread.sleep(500);
	      driver.findElement(By.id("name")).click();
	      driver.findElement(By.id("name")).clear();
	      driver.findElement(By.id("name")).sendKeys("2");
	      driver.findElement(By.id("img")).clear();
	      driver.findElement(By.id("img")).sendKeys("2");
	      driver.findElement(By.id("description")).clear();
	      driver.findElement(By.id("description")).sendKeys("2");
	      Thread.sleep(5000);
	      driver.findElement(By.cssSelector("body > div > form > button.btn.btn-success")).click();
//Delete that artist
	      Thread.sleep(10000);
driver.findElement(By.cssSelector(".card:nth-child(2) .btn-danger")).click();
Thread.sleep(2000);	}
	
}













