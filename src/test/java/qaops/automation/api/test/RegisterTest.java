package qaops.automation.api.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class RegisterTest extends BaseTest {

    private static final String REGISTER_NEW_USER = "/register";

    @Test
    public void testUnableToRegisterWhenPasswordMissing() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");

        given().
            body(user).
        when().
            post(REGISTER_NEW_USER).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body("error", is("Missing password"));
    }
}
