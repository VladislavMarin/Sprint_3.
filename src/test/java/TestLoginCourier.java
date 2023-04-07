
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestLoginCourier {

    //Ручка
    String endPoint = Utils.END_POINT_LOGIN_COURIER;

    @Before
    public void setUp() {
        Utils.createNewCourier();
    }

    @After
    public void tearDown() {
        Utils.deleteNewCourier();
    }

    /**
     * Тест авторизации зарегистрированного курьера
     * Выполняется запрос с валидными данными зарегистрированного курьера
     */
    @Test
    public void testLoginCourierValid() {

        File json = new File("src/test/resources/loginCourierValid.json");

        Response response = given()
                .spec(Utils.requestSpec).body(json)
                .when()
                .post(endPoint);
        // Выполняется проверка статуса ответа (должен быть 200)
        // полянется проверка, что успешный запрос возвращает id.
        response.then().assertThat().body("id", notNullValue())
                .and().statusCode(200);

    }

    /**
     * Тест авторизации незарегистрированого курьера
     * Выполняется запрос в данными незарегестрированного курьера
     */
    @Test
    public void testLoginCourierNoValid() {
        File json = new File("src/test/resources/loginCourierNoValid.json");

        String message = "Учетная запись не найдена";

        Response response = given()
                .spec(Utils.requestSpec).body(json)
                .when()
                .post(endPoint);
        // Выполняется проверка сообщения ответа "message" и статус (должен быть 404)
        response.then().assertThat().body("message", equalTo(message))
                .and()
                .statusCode(404);
    }

    /**
     * Тест на авторизацию без поля "login"
     * Выполняется запрос на логирования курьера.
     * В теле запроса остуствует поле "login"
     */
    @Test
    public void testLoginCourierNoLogin(){
        File json = new File("src/test/resources/loginCourierNoLogin.json");

        String message = "Недостаточно данных для входа";

        Response response = given()
                .spec(Utils.requestSpec).body(json)
                .when()
                .post(endPoint);
        // Выполняется проверка сообщения ответа "message" и статус (должен быть 400)
        response.then().assertThat().body("message", equalTo(message))
                .and()
                .statusCode(400);
    }
    /**
     * Тест на авторизацию без поля "password"
     * Выполняется запрос на логирования курьера.
     * В теле запроса остуствует поле "login"
     * На момент написания теста сервис был недоступен
     */
    @Test
    public void testLoginCourierNoPassword(){

        File json = new File("src/test/resources/loginCourierNoPassword.json");

        String message = "Недостаточно данных для входа";

        Response response = given()
                .spec(Utils.requestSpec).body(json)
                .when()
                .post(endPoint);
        // Выполняется проверка сообщения ответа "message" и статус (должен быть 400)
        response.then().assertThat().body("message", equalTo(message))
                .and()
                .statusCode(400);
    }
}
