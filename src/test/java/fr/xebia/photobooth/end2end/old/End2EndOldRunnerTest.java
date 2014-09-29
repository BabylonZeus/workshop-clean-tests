package fr.xebia.photobooth.end2end.old;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/fr/xebia/photobooth/end2end/old"},
        monochrome = true, format = {"pretty", "html:target/cucumber", "rerun:target/rerun.txt"})
public class End2EndOldRunnerTest {
}
