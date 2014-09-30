package fr.xebia.photobooth.api;

import fr.xebia.photobooth.domain.Order;
import fr.xebia.tests.TomcatRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class PhotoResourceIT {	
	@ClassRule
	public static TomcatRule tomcatRule = new TomcatRule();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_validate_cmd_then_download_picture_and_create_file() {
        Order order = new Order("COLOR", "PORTRAIT", "0.0", "/img/dog.jpeg");

        String body = "{\"colorimetry\":'COLOR',\"format\":'PORTRAIT', \"money\":'0.0'}";
        //,{"url":http://camera1.mairie-brest.fr/axis-cgi/jpg/image.cgi"}


        given().port(tomcatRule.port())
                .body(order)
                .contentType(JSON)
                .log().all().
                when().post("/rest/photos/saveWithURL").
                then().statusCode(200).
                log().all().
                body(startsWith("image")).and().body(endsWith("png"));
    }


    @Test
    public void should_return_true_if_valid_order() {
        Order order = new Order("COLOR", "PORTRAIT", "0.0", null);

        given().port(tomcatRule.port())
               .body(order)
               .contentType(JSON).log().all().
        when().post("/rest/photos/validate").
        then()
              .statusCode(200).log().all()

              .body(equalTo("true"));
    }

}
