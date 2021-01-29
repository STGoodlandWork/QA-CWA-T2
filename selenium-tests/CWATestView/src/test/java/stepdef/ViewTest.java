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

public class ViewTest {
	
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
	
	@Given("^I access the choonz frontpage to view artists$")
	public void i_access_the_choonz_frontpage_to_view_artists() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8082/");
	    driver.manage().window().setSize(new Dimension(1552, 840));
	    driver.findElement(By.cssSelector(".nav-item:nth-child(2) > .nav-link")).click();
	    driver.findElement(By.cssSelector(".card:nth-child(1) .btn-success")).click();
	    }

	@When("^I view an artist$")
	public void i_view_an_artist() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 WebDriverWait wait = new WebDriverWait(driver, 5);  
		 	wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[1]/div[2]/button[1]")));
	    	driver.findElement(By.xpath("//*[@id=\"ArtistDisplay\"]/div[1]/div[2]/button[1]")).click();
	    	driver.get("http://localhost:5502/html/view_artist.html?=1");
	    	wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table[1]/thead/tr[2]/td[1]")));
	    	assertThat(driver.findElement(By.xpath("/html/body/table[1]/thead/tr[2]/td[1]")).getText(), is("Cotton"));
	    	driver.findElement(By.xpath("/html/body/button")).click();
	    	driver.get("http://localhost:5502/html/artists.html");
	    	assertThat(driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/h5")).getText(), is("THE MOUNTAIN GOATS"));
	    	driver.findElement(By.cssSelector(".card:nth-child(1) .btn-success")).click();
	    	driver.findElement(By.cssSelector("p")).click();
	    	driver.findElement(By.cssSelector("p")).click();
	      {
	        WebElement element = driver.findElement(By.cssSelector("p"));
	        Actions builder = new Actions(driver);
	        builder.doubleClick(element).perform();
	      }
	}

	@When("^I check for its tracks and albums$")
	public void i_check_for_its_tracks_and_albums() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 WebDriverWait wait = new WebDriverWait(driver, 5);  
		driver.findElement(By.cssSelector("p")).click();
	    assertThat(driver.findElement(By.cssSelector("p")).getText(), is("The Mountain Goats"));
	    driver.findElement(By.cssSelector(".table:nth-child(11) tr:nth-child(2) > td:nth-child(1)")).click();
	    assertThat(driver.findElement(By.cssSelector(".table:nth-child(11) tr:nth-child(2) > td:nth-child(1)")).getText(), is("Cotton"));
	    driver.findElement(By.cssSelector("#album tr:nth-child(2) > td:nth-child(1)")).click();
	    assertThat(driver.findElement(By.cssSelector("#album tr:nth-child(2) > td:nth-child(1)")).getText(), is("We Shall All Be Healed"));
	    driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(3) > .btn")).click();
	    driver.findElement(By.xpath("/html/body/table[1]/thead/tr[2]/td[1]")).click();
	    Thread.sleep(1000);
	    wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table[1]/thead/tr[2]/td[1]")));
	    assertThat(driver.findElement(By.xpath("/html/body/table[1]/thead/tr[2]/td[1]")).getText(), is("Cotton"));	}

	@Then("^I can also view the related tracks and albums$")
	public void i_can_also_view_the_related_tracks_and_albums() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 WebDriverWait wait = new WebDriverWait(driver, 5);  
		    driver.findElement(By.cssSelector(".nav-item:nth-child(5) > .nav-link")).click();
		    driver.get("http://localhost:5502/html/albums.html");
		    driver.findElement(By.cssSelector(".card:nth-child(1) .btn-success")).click();
		    driver.get("http://localhost:5502/html/view_album.html");
		    wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"albumDisplay\"]/div[1]/div[2]/button[1]")));
		    driver.findElement(By.xpath("//*[@id=\"albumDisplay\"]/div[1]/div[2]/button[1]")).click();
		    //Thread.sleep(10000);
		    driver.findElement(By.xpath("/html/body/div[2]/p")).click();
		    assertThat(driver.findElement(By.xpath("/html/body/div[2]/p")).getText(), is("We Shall All Be Healed"));
		    driver.findElement(By.cssSelector(".btn-warning")).click();
		    driver.findElement(By.cssSelector("#title > p")).click();
		    assertThat(driver.findElement(By.cssSelector("#title > p")).getText(), is("Cotton"));
		    driver.findElement(By.xpath("/html/body/nav/div/ul/li[3]/a")).click();
		    driver.get("http://localhost:5502/html/playlists.html");
		    Thread.sleep(1000);
		    driver.findElement(By.cssSelector(".card:nth-child(1) .btn-success")).click();
		    driver.findElement(By.cssSelector("#name > p")).click();
		    assertThat(driver.findElement(By.cssSelector("#name > p")).getText(), is("Sadbois"));
		    driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
		    driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
		    driver.findElement(By.cssSelector(".nav-item:nth-child(5) > .nav-link")).click();
		    Thread.sleep(5000);
		    driver.findElement(By.cssSelector(".card:nth-child(1) .btn-success")).click();
		    driver.findElement(By.cssSelector(".btn-warning")).click();
		    driver.findElement(By.cssSelector(".btn-dark")).click();
	}

	
}


















