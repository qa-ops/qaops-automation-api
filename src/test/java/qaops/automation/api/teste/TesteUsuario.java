package qaops.automation.api.teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;


public class TesteUsuario {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }


    @Test
    public void testeListaPaginaDeUsuarioEspecifica() {
        given().
            params("page","2").
        when().
            get("/users").
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {
        Usuario usuario = new Usuario("rafael","eng test");

        given().
            contentType(ContentType.JSON).
            body(usuario).
        when().
            post("/user").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }
}
