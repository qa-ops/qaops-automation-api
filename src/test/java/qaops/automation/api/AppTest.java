/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package qaops.automation.api;

import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AppTest {

    @Test
    public void testeListaMetadosDoUsuario() {
        when().
            get("https://reqres.in/api/users?page=2").
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }
}
