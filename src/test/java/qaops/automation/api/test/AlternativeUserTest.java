package qaops.automation.api.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import qaops.automation.api.domain.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlternativeUserTest {

    private static final String BASE_URL = "https://reqres.in";
    private static final String BASE_PATH = "/api";

    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String CREATE_USER_ENDPOINT = "/user";
    private static final String SHOW_USER_ENDPOINT = "/users/{userId}";

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testSpecificPageIsDisplayed() {
        String uri = getUri(LIST_USERS_ENDPOINT);

        given().
            param("page","2").
        when().
            get(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }


    @Test
    public void testAbleToCreateNewUser() {
        String uri = getUri(CREATE_USER_ENDPOINT);

        Map<String, String> user = new HashMap<>();
        user.put("name", "rafael");
        user.put("job", "eng test");

        given().
            contentType(ContentType.JSON).
            body(user).
        when().
            post(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }

    @Test
    public void testSizeOfItemsDisplayedAreTheSameAsPerPage() {
        String uri = getUri(LIST_USERS_ENDPOINT);

        int expectedPage = 2;

        int expectedItemsPerPage = getExpectedItemsPerPage(expectedPage);

        given().
            param("page",expectedPage).
        when().
            get(uri).
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body(
                "page", is(expectedPage),
                "data.size()", is(expectedItemsPerPage ),
                "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(expectedItemsPerPage)
            );
    }

    @Test
    public void testShowSpecificUser() {
        String uri = getUri(SHOW_USER_ENDPOINT);

        User user = given().
            pathParam("userId", 2).
        when().
            get(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_OK).
        extract().
            body().jsonPath().getObject("data", User.class);


        assertThat(user.getEmail(), containsString("@reqres.in"));
        assertThat(user.getName(), containsString("Janet"));
        assertThat(user.getLastName(), containsString("Weaver"));
    }

    private int getExpectedItemsPerPage(int page) {
        String uri = getUri(LIST_USERS_ENDPOINT);

        return given().
                    param("page", page).
                when().
                    get(uri).
                then().
                    contentType(ContentType.JSON).
                    statusCode(HttpStatus.SC_OK).
                extract().
                    path("per_page");
    }

    private String getUri(String endpoint) {
        return BASE_URL + BASE_PATH + endpoint;
    }
}
