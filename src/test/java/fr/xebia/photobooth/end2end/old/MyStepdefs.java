package fr.xebia.photobooth.end2end.old;

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

public class MyStepdefs {

    private PhantomJsTest phantomJsTest;
    private Optional<TomcatRule> tomcatRule;
    
    public MyStepdefs() throws IOException {
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
                Thread.sleep(100);
                while (!portIsNotBind(8080)) {
                    System.out.println("tomcat arrive pas à s'éteindre");
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /*******************************User choose a portrait color command*****************************************************/
    @Given("^I go to homepage$")
    public void I_go_to_homepage() {
        phantomJsTest.goTo("/");
        assertThat(phantomJsTest.title()).isEqualTo("XebiaMaton");
    }
    @Given("^I choose a portrait color command")
    public void I_choose_a_portrait_color_command() {
        phantomJsTest.click(".portrait-color");
        phantomJsTest.await().atMost(5, TimeUnit.SECONDS).until(".order-cmd").isPresent();
    }
    @When("^I confirm my command and its price$")
    public void I_confirm_my_command_and_its_price() throws Throwable {
        I_click_on_button("btn-order-cmd");
    }
    @Then("^video url should be displayed$")
    public void video_url_should_be_displayed() {
        assertThat(phantomJsTest.findFirst("#urlVideo").isDisplayed());
    }

    @Given("^video url is displayed$")
    public void video_url_is_displayed() throws Throwable {
        I_go_to_homepage();
        I_choose_a_portrait_color_command();
        video_url_should_be_displayed();
    }

    /*******************************User take a snapshot command*****************************************************/

    @Given("^I fill video url")
    public void I_fill_video_url() {
        phantomJsTest.await().atMost(5, TimeUnit.SECONDS).until("#urlVideo").areDisplayed();
    	phantomJsTest.fill("#urlVideo").with("http://webcam.hahd.fr/mjpg/video.mjpg?camera=1");
    	//phantomJsTest.fill("#urlPhoto").with("todo");
    }
    
    @Given("^I click on start Video")
    public void I_click_on_start_video() {
        phantomJsTest.click(".startIPWebcam");
    }
    @And("^video is displayed")
    public void video_is_displayed() {
        phantomJsTest.click(".startIPWebcam");
    	phantomJsTest.await().atMost(5, TimeUnit.SECONDS).until(".ipWebcamResult").with("src").contains("");
    }

    @When("^I click on \"([^\"]*)\" button$")
    public void I_click_on_button(String buttonCSSClass) throws Throwable {
    	phantomJsTest.takeScreenShot();
        phantomJsTest.click("." + buttonCSSClass);
    }

    @Then("^My picture should be displayed$")
    public void My_picture_should_be_displayed() {
        assertThat(phantomJsTest.find(".snapshotResult").getAttribute("src")).isNotEmpty();
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

    /*******************************User confirm picture and get its command*****************************************************/

    @Given("^my picture is displayed$")
    public void my_picture_is_displayed() throws Throwable {
        I_go_to_homepage();
        I_fill_video_url();
        I_click_on_start_video();
        video_is_displayed();
        I_click_on_button("snapshot");

        My_picture_should_be_displayed();
    }

    @Given("^a user wanted a full color portrait of himself$")
    public void a_user_wanted_a_full_color_portrait_of_himself() throws Throwable {
        my_picture_is_displayed();
    }

    @When("^the user take the picture$")
    public void the_user_take_the_picture() throws Throwable {
        I_click_on_button("ok");
    }

    @Then("^the photomaton should print the full color portrait of the user$")
    public void the_photomaton_should_print_the_full_color_portrait_of_the_user() throws Throwable {
        Thread.sleep(5000);
        String href = phantomJsTest.find(".link").getAttribute("href");

        assertThat(href).isNotNull().startsWith(format("http://localhost:%d/image", port()));
    }

    @Then("^I can send the link to my mother$")
    public void I_can_send_the_link_to_my_mother() throws Throwable {
        the_photomaton_should_print_the_full_color_portrait_of_the_user();
    }
}
