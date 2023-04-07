package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Utils {
    /**
     * Эндпоинт для создания курьера
     */
    public static final String END_POINT_NEW_COURIER = "/api/v1/courier";
    /**
     * Эндпоинт для авторизации курьера
     */
    public static final String END_POINT_LOGIN_COURIER = "/api/v1/courier/login";

    /**
     * Эндпоинт для создания заказа
     */
    public static final String END_POINT_CREATE_ORDER = "/api/v1/orders";

    /**
     * Эндпоинт для получения списка заказов
     */
    public static final String END_POINT_ORDER_LIST = "/api/v1/orders";

    /**
     * Спецификация запроса
     */
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://qa-scooter.praktikum-services.ru")
            .build().header("Content-Type", "application/json");

    /**
     * Метод для создания курьера (используется в @Before)
     */
    public static void createNewCourier() {
        String endPointNewCourier = "/api/v1/courier";
        File json = new File("src/test/resources/newCourierValid.json");

        Response response = given().
                spec(requestSpec).
                body(json).
                when().
                post(endPointNewCourier);

        response.then().statusCode(201);
    }

    /**
     * Метод для удаления курьера (используется в @After)
     */
    public static void deleteNewCourier() {

        String endPointLogin = "/api/v1/courier/login";

        File jsonLogin = new File("src/test/resources/loginCourierValid.json");

        Response responseLogin = given()
                .spec(requestSpec).body(jsonLogin)
                .when()
                .post(endPointLogin);

        String idCourier = responseLogin.then().extract()
                .body().asString();

        JsonPath jsonPath = new JsonPath(idCourier);

        String id = jsonPath.getString("id");

        String endPointDeleteCourier = "/api/v1/courier/{id}";

        Response responseDeleteCourier = given()
                .spec(requestSpec)
                .when()
                .delete(endPointDeleteCourier, id);

        responseDeleteCourier.then().statusCode(200);
    }

    /**
     * Метод получения id курьера
     * @return id курьера
     */
    public static int getCourierId() {

        String endPointLogin = "/api/v1/courier/login";

        File jsonLogin = new File("src/test/resources/loginCourierValid.json");

        Response responseLogin = given()
                .spec(requestSpec).body(jsonLogin)
                .when()
                .post(endPointLogin);

        String idCourier = responseLogin.then().extract()
                .body().asString();

        JsonPath jsonPath = new JsonPath(idCourier);

        return jsonPath.getInt("id");

    }
}
