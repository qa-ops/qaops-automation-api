package qaops.automation.api.test;

import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.domain.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTest extends BaseTest {

    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String CREATE_USER_ENDPOINT = "/user";
    private static final String SHOW_USER_ENDPOINT = "/users/{userId}";

    @Test
    public void testSpecificPageIsDisplayed() {
        given().
            param("page","2").
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

    @Test
    public void testSizeOfItemsDisplayedAreTheSameAsPerPage() {
        int expectedPage = 2;

        int expectedItemsPerPage = getExpectedItemsPerPage(expectedPage);

        given().
            param("page",expectedPage).
        when().
            get(LIST_USERS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body(
                "page", is(expectedPage),
                "data.size()", is(expectedItemsPerPage ),
                "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(expectedItemsPerPage)
            );
    }

    @Test
    public void testShowSpecificUser() {
        User user = given().
            pathParam("userId", 2).
        when().
            get(SHOW_USER_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
        extract().
            body().jsonPath().getObject("data", User.class);


        assertThat(user.getEmail(), containsString("@reqres.in"));
        assertThat(user.getName(), containsString("Janet"));
        assertThat(user.getLastName(), containsString("Weaver"));
    }

    private int getExpectedItemsPerPage(int page) {
        return given().
                    param("page", page).
                when().
                    get(LIST_USERS_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    path("per_page");
    }
}
