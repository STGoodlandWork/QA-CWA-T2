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

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AlbumCRUDTest {
	
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

		/*		
  		//Code to stop Chrome autofilling and messing up the Update test
		cOptions.addArguments("--start-maximized");
		cOptions.addArguments("--disable-web-security");
		cOptions.addArguments("--no-proxy-server");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		cOptions.setExperimentalOption("prefs", prefs);
		*/
		
	}
	
	@After
	public void cleanUp() {
		driver.quit();
	}
	
	@Given("^I access the choonz frontpage$")
	public void i_access_the_choonz_frontpage() throws Throwable {
		// Go to desired website
		driver.get(URL);
	    driver.manage().window().setSize(new Dimension(1936, 1056));
	}
	
	@When("^I create an album$")
	public void i_create_an_album() throws Throwable {
		 //Go to album page
	    driver.findElement(By.cssSelector(".nav-item:nth-child(5) > .nav-link")).click();
    //Create an album
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    
	    wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='createAlbumButton']")));
	    driver.findElement(By.xpath("//button[@id='createAlbumButton']")).click();
	    driver.get("http://localhost:8082/html/create_album.html");		
	    driver.findElement(By.id("name")).sendKeys("1");
	    driver.findElement(By.id("artist")).sendKeys("1");
	    driver.findElement(By.id("genre")).sendKeys("1");
	    driver.findElement(By.id("img")).sendKeys("1");
	    wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("#submitAlbumButton")));
	    assertEquals("http://localhost:8082/html/create_album.html", driver.getCurrentUrl());
	    driver.findElement(By.xpath("//*[@id='submitAlbumButton']")).click();
	    Thread.sleep(5000);

	    
	    
	}
	
	@When("^I search for it by album name$")
	public void i_search_for_it_by_album_name() throws Throwable {
		//Read for the created artist
		
	    WebDriverWait wait = new WebDriverWait(driver, 5);
	    driver.get("http://localhost:8082/html/albums.html");		
		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#albumDisplay > div:nth-child(6) > div.card-body")));
	    assertThat(driver.findElement(By.cssSelector("#albumDisplay > div:nth-child(6) > div.card-body > h5")).getText(), is("1"));
	    driver.findElement(By.cssSelector(".btn-outline-info")).click();
	    driver.findElement(By.id("album-input")).click();
	    driver.findElement(By.id("album-input")).sendKeys("1");
	    driver.findElement(By.id("searchAlbumButton")).click();
	    Thread.sleep(5000);
	    assertThat(driver.findElement(By.cssSelector("#title > p")).getText(), is("1"));
	    driver.findElement(By.xpath("/html/body/button")).click();
	   
	    
	}
	
	@Then("^I can update and delete it$")
	public void i_can_update_and_delete_it() throws Throwable {
		//Update that artist
		Thread.sleep(1000);
	    driver.findElement(By.xpath("/html/body/div[3]/div[5]/div[2]/button[2]")).click();
	    Thread.sleep(500);
	      driver.findElement(By.cssSelector("#name")).click();
	      driver.findElement(By.id("name")).clear();
	      driver.findElement(By.id("name")).sendKeys("2");
	      driver.findElement(By.id("artist")).clear();
	      driver.findElement(By.id("artist")).sendKeys("2");
	      driver.findElement(By.id("genre")).clear();
	      driver.findElement(By.id("genre")).sendKeys("2");
	      driver.findElement(By.id("img")).clear();
	      driver.findElement(By.id("img")).sendKeys("2");
	      driver.findElement(By.xpath("/html/body/div[2]/form/button[1]")).click();	    
	      driver.get("http://localhost:8082/html/albums.html");		
	      assertThat(driver.findElement(By.cssSelector("#albumDisplay > div:nth-child(5) > div.card-body > h5")).getText(), is("2"));
	      
  //Delete that artist
	      
	      Thread.sleep(2000);
  driver.findElement(By.cssSelector(".card:nth-child(3) .btn-danger")).click();
  Thread.sleep(2000);
  
	}
	
}
