package qaops.automation.api.teste;

import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIO_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userId}";

    @Test
    public void testeMostraPaginaEspecifica() {
        given().
            param("page","2").
        when().
            get(LISTA_USUARIOS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {
        Map<String, String> usuario = new HashMap<>();
        usuario.put("name", "rafael");
        usuario.put("job", "eng test");

        given().
            body(usuario).
        when().
            post(CRIAR_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }

    @Test
    public void testeTamanhoDosItemsMostradosIgualAoPerPage() {
        int paginaEsperada = 2;
        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);

        given().
            param("page", paginaEsperada).
        when().
           get(LISTA_USUARIOS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body(
                "page", is(paginaEsperada),
                "data.size()", is(perPageEsperado),
                "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(perPageEsperado)
            );
    }

    @Test
    public void testeMostraUsuarioEspecifico() {
        Usuario usuario = given().
            pathParam("userId", 2).
        when().
            get(MOSTRAR_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
        extract().
            body().jsonPath().getObject("data", Usuario.class);


        assertThat(usuario.getEmail(), containsString("@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastName(), is("Weaver"));

    }

    private int retornaPerPageEsperado(int page) {
        return given().
                    param("page", page).
                when().
                    get(LISTA_USUARIOS_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    path("per_page");
    }
}
