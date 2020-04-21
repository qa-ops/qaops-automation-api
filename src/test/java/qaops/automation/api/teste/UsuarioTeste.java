package qaops.automation.api.teste;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UsuarioTeste extends BaseTeste {

    @Test
    public void testListaPaginaDeUsuarioEspecifica() {
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
    public void testSuccessfullyCreateaUser() {
        Usuario usuario = new Usuario("rafael","eng test", "email@gmail.com");

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
