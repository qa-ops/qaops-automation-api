package qaops.automation.api.teste;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class TesteRegistro extends TesteBase {

    private static final String REGISTRO_ENDPOINT = "/register";

    @Test
    public void testRegister() {
        Usuario usuario = new Usuario();
        usuario.setEmail("eve.holt@reqres.in");

        given().
            body(usuario).
        when().
            post(REGISTRO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body("error", is("Missing password"));
    }

}
