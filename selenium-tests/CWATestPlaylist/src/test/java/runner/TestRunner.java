package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cuke/choonzPlaylist.feature", monochrome = true, glue = { "stepdef" })
public class TestRunner {

}
