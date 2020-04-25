package qaops.automation.api.teste;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class TesteRegistro extends TesteBase {

    private static final String REGISTRO_ENDPOINT = "/register";
    private static final String LOGIN_ENDPOINT = "/login";

    @BeforeClass
    public static void setupRegistro() {
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(HttpStatus.SC_BAD_REQUEST).
                build();
    }


    @Test
    public void testRegister() {
        Usuario usuario = new Usuario();
        usuario.setEmail("eve.holt@reqres.in");
        given().
            body(usuario).
        when().
            post(REGISTRO_ENDPOINT).
        then().
            body("error", is("Missing password"));
    }

    //Esse teste deveria estar no TesteLogin, porém esta aqui para mostrar a funcionalidade de ResponseSpecification
    @Test
    public void testeLoginSemSucesso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("eve.holt@reqres.in");

        given().
            body(usuario).
        when().
            post(LOGIN_ENDPOINT).
        then().
            body("error", is("Missing password"));
    }

}
