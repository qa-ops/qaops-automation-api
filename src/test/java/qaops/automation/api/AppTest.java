package qaops.automation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AppTest {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Test
    public void testListUserMetadata() {
        when().
            get("https://reqres.in/api/users?page=2").
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testSuccessfullyCreateaUser() {
        given().
            contentType(ContentType.JSON).
            body("{\"name\": \"rafael\", \"job\": \"eng test\" }").
        when().
            post("https://reqres.in/api/user").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }
}
