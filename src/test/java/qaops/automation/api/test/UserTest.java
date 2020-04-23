package qaops.automation.api.test;

import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserTest extends BaseTest {

    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String CREATE_USER_ENDPOINT = "/user";

    @Test
    public void testSpecificPageIsDisplayed() {
        given().
            params("page","2").
        when().
            get(LIST_USERS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testAbleToCreateNewUser() {
        User user = new User("rafael", "eng test", "email@gmail.com");

        given().
            body(user).
        when().
            post(CREATE_USER_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }
}
