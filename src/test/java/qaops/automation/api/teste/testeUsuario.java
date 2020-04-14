package qaops.automation.api.teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class testeUsuario {

    @BeforeClass
    public static void setup() {
        baseURI = "https://reqres.in";
        basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Test
    public void testListUserMetadata() {
        given().
            params("page", "2").
        when().
            get("/users").
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testSuccessfullyCreateaUser() {
        Usuario usuario = new Usuario("rafael", "eng test");

        given().
            contentType(ContentType.JSON).
            body(usuario).
        when().
            post("/user").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }

    @Test
    public void testePesquisaPorUsuario() {
        String user = "2";
        when().
            get("/users/" + user).
        then().
            statusCode(HttpStatus.SC_OK).
            body("data.email", containsString("@reqres.in"));
    }

    @Test
    public void testePesquisaPorUsuario2() {
        given().
            pathParam("user", "2").
        when().
            get("/users/{user}").
        then().
            statusCode(HttpStatus.SC_OK).
            body("data.email", containsString("@reqres.in"));
    }

//    @Test
//    public void testeUserNotAbleToRegister() {
//        given().
//            body()
//        when().
//            post("/register").
//        then().
//            statusCode(HttpStatus.SC_BAD_REQUEST).
//            body("error", is("Missing password"));
//    }
}
