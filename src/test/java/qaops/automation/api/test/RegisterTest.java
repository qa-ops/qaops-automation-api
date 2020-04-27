package qaops.automation.api.test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import qaops.automation.api.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class RegisterTest extends BaseTest {

    private static final String REGISTER_NEW_USER_ENDPOINT = "/register";
    private static final String LOGIN_USER_ENDPOINT = "/login";

    @BeforeClass
    public static void setupRegister() {
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(HttpStatus.SC_BAD_REQUEST).
                build();

    }

    @Test
    public void testUnableToRegisterWhenPasswordMissing() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");

        given().
            body(user).
        when().
            post(REGISTER_NEW_USER_ENDPOINT).
        then().
            body("error", is("Missing password"));
    }

    //This test shold be in the LoginTest, but it is here in order to show the ResponseSpec just for one test class
    @Test
    public void testUnableToLogin() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");

        given().
            body(user).
        when().
            post(LOGIN_USER_ENDPOINT).
        then().
            body("error", is("Missing password"));

    }
}
