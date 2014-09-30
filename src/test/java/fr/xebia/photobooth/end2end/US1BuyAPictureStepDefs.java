package fr.xebia.photobooth.end2end;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import fr.xebia.tests.PhantomJsTest;
import fr.xebia.tests.TomcatRule;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class US1BuyAPictureStepDefs {

    private PhantomJsTest phantomJsTest;
    private Optional<TomcatRule> tomcatRule;

    public US1BuyAPictureStepDefs() throws IOException {
        tomcatRule = tomcatRuleOrEmptyIfAlreadyStarted();
    }

    @Before
    public void startTomcatAndWebDriver() {
        tomcatRule.ifPresent(TomcatRule::before);
        phantomJsTest = new PhantomJsTest(format("http://localhost:%d", port()));
        phantomJsTest.starting();
    }

    private Integer port() {
        return tomcatRule.map(TomcatRule::port).orElse(8080);
    }

    @After
    public void stopTomcatAndWebDriver() {
        phantomJsTest.getDriver().close();
        tomcatRule.ifPresent(rule -> {
            rule.after();
            try {
                if (!portIsNotBind(port())) {
                    System.err.println("tomcat arrive pas à s'éteindre");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Given("^I'm in front of the photomaton camera$")
    public void I_m_in_front_of_the_photomaton_camera() throws Throwable {
        I_go_to_homepage();
        I_choose_a_portrait_color_command();
        video_url_should_be_displayed();
    }

    @When("^I take the picture$")
    public void I_take_the_picture() throws Throwable {
        I_click_on_button("snapshot");
    }

    @And("^decide to print it$")
    public void decide_to_print_it() throws Throwable {
        I_click_on_button("ok");
    }

    @Then("^the photomaton print the processed picture$")
    public void the_photomaton_print_the_processed_picture() throws Throwable {
        String href = phantomJsTest.find("#savedLink").getAttribute("href");
        assertThat(href).isNotNull().startsWith(format("http://localhost:%d/image", port()));
    }

    public void I_go_to_homepage() {
        phantomJsTest.goTo("/");
        assertThat(phantomJsTest.title()).isEqualTo("XebiaMaton");
    }

    public void I_choose_a_portrait_color_command() {
        phantomJsTest.click(".PORTRAIT-COLOR");
        phantomJsTest.await().atMost(5, TimeUnit.SECONDS).until(".order-cmd").isPresent();
    }

    public void video_url_should_be_displayed() {
        assertThat(phantomJsTest.findFirst("#urlVideo").isDisplayed());
    }

    public void I_click_on_button(String buttonCSSClass) throws Throwable {
        phantomJsTest.takeScreenShot();
        phantomJsTest.click("." + buttonCSSClass);
    }

    private Optional<TomcatRule> tomcatRuleOrEmptyIfAlreadyStarted() throws IOException {
        if (portIsNotBind(8080)) {
            return Optional.of(new TomcatRule());
        } else {
            return Optional.empty();
        }
    }

    private boolean portIsNotBind(int port) throws IOException {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return true;
        } catch (BindException e) {
            return false;
        }
    }


}
