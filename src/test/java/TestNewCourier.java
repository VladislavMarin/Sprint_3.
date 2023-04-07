import io.restassured.response.Response;
import org.junit.*;
import utils.Utils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestNewCourier {

    String endPoint = Utils.END_POINT_NEW_COURIER;

    @Before
    public void setUp() {
        Utils.createNewCourier();
    }

    @After
    public void tearDown() {
        Utils.deleteNewCourier();
    }

    /**
     * Тест на создание нового курьера
     * Запрсо регистрирует в исстеме нового клиента
     */
    @Test
    public void testNewCourierValid() {
        //Вызыван метод из @After, тк сначала выполняется @Before (создается курьер)
        //Как сделать так, что бы @Before не выполнялся только для одного теста мне не удалось
        //Прошу прощения за такие костыли
        tearDown();

        File json = new File("src/test/resources/newCourierValid.json");

        // Переменные для наглядности
        // statusCode - код ответа

        Response response = given()
                .spec(Utils.requestSpec)
                .body(json)
                .when()
                .post(endPoint);
        // Выполняется проверка, что курьер создается, что запрос возвращает 201
        response.then().statusCode(201);
        // Выполняется проверка, что запрос возвращает "{"ok":true}"
        Assert.assertEquals(response.then().extract().body().asString(), "{\"ok\":true}");

    }

    /**
     * Тест на создание курьера без одного из полей
     * Выполняется проверка запроса на регистрацию курбера в теле которого отсуствует поле
     * В данном тесте запрос без поля "login"
     */
    @Test
    public void testNewCourierNoValidNoLogin() {

        File jsonNoLogin = new File("src/test/resources/newCourierNoValidNoLogin.json");

        // Переменные для наглядности
        // responseMessage - ожидаемый текст в ответе.
        // statusCode - код ответа.
        String responseMessage = "Недостаточно данных для создания учетной записи";


        Response response = given()
                .spec(Utils.requestSpec)
                .body(jsonNoLogin)
                .when()
                .post(endPoint);

        // Выполянется проверка сообщения ("message") ответа
        // Выполняется проверка статуса кода (должен быть 400)
        response.then().assertThat().body("message", equalTo(responseMessage))
                .and()
                .statusCode(400);
    }

    /**
     * Тест на создание курьера без одного из полей
     * Выполняется проверка запроса на регистрацию курбера в теле которого отсуствует поле
     * В данном тесте запрос без поля "password"
     */
    @Test
    public void testNewCourierNoValidNoPassword() {

        File jsonNoLogin = new File("src/test/resources/newCourierNoValidNoPassword.json");

        // Переменные для наглядности
        // responseMessage - ожидаемый текст в ответе.
        // statusCode - код ответа.
        String responseMessage = "Недостаточно данных для создания учетной записи";

        Response response = given()
                .spec(Utils.requestSpec)
                .body(jsonNoLogin)
                .when()
                .post(endPoint);

        // Выполянется проверка сообщения ("message") ответа
        // Выполняется проверка статуса кода (должен быть 400)
        response.then().assertThat()
                .body("message", equalTo(responseMessage))
                .and()
                .statusCode(400);
    }

    /**
     * Тест на создание двух одинаковых курьеров
     * Выполняется проверка на регистрицию курьера в запросе которого указаны
     * данные уже существующего курьера
     */

    @Test
    public void testNewCourierDuplicate() {

        File json = new File("src/test/resources/newCourierDuplicate.json");

        // Переменные для наглядности
        // responseMessage - ожидаемый текст в ответе
        // statusCode - код ответа
        String responseMessage = "Этот логин уже используется. Попробуйте другой.";

        Response response = given().
                spec(Utils.requestSpec).body(json)
                .when()
                .post(endPoint);


        // Выполняется проверка статуса кода ответа (должен быть 409)
        // и содержание поля "message" ("Этот логин уже используется. Попробуйте другой.")
        response.then().assertThat().body("message", equalTo(responseMessage))
                .and()
                .statusCode(409);
    }

}
