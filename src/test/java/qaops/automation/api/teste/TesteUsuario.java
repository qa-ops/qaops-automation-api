package qaops.automation.api.teste;

import org.apache.http.HttpStatus;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIO_ENDPOINT = "/user";

    @Test
    public void testeMostraPaginaEspecifica() {
        given().
            params("page","2").
        when().
            get(LISTA_USUARIOS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {
        Usuario usuario = new Usuario("rafael","eng test", "email@gmail.com");

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
            params("page", paginaEsperada).
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
