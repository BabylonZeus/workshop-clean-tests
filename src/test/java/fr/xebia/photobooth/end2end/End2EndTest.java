package fr.xebia.photobooth.end2end;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/fr/xebia/photobooth/end2end"},
        monochrome = true, format = {"pretty", "html:target/cucumber", "rerun:target/rerun.txt"},
        strict = false)
public class End2EndTest {
}
