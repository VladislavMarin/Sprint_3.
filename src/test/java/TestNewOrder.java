import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.NewOrderSerializationClass;
import utils.Utils;


import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestNewOrder {
    //Ручка
    String endPoint = Utils.END_POINT_CREATE_ORDER;

    String trackNumberString;

    private final NewOrderSerializationClass colour;

    public TestNewOrder(NewOrderSerializationClass colour) {
        this.colour = colour;
    }

    // POJO. Цвет Чёрный
    private static final NewOrderSerializationClass colourBlack =
            new NewOrderSerializationClass("BLACK");
    // POJO. Цвет Серый
    private static final NewOrderSerializationClass colourGrey =
            new NewOrderSerializationClass("GREY");
    // POJO. Цвет отсутсвует
    private static final NewOrderSerializationClass colourMissing =
            new NewOrderSerializationClass("");
    // POJO. Цвет Серый + Чёрный
    private static final NewOrderSerializationClass colourGreyAndBlack =
            new NewOrderSerializationClass("GREY , BLACK");

    @Parameterized.Parameters
    public static Object[][] changeColour() {
        return new Object[][]{
                {colourBlack},
                {colourGrey},
                {colourMissing},
                {colourGreyAndBlack}
        };
    }

    /**
     * Тест на создание заказа с разнымим значениями поля "colour" тела запроса
     * Выполняется параметризованный тест на создание заказа с разными значениями поля "colour"
     * значения : "BLACK" ; "GREY"; "" ; "GREY , BLACK".
     */
    @Test
    public void testNewOrderParam() {
        TestNewOrder body = new TestNewOrder(colour);
        Response response = given()
                .spec(Utils.requestSpec)
                .body(body)
                .when()
                .post(endPoint);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

        // Получение номера заказа (значения поля "track")
        String trackNumber = response.then().extract()
                .body().asString();

        JsonPath jsonPath = new JsonPath(trackNumber);

        trackNumberString = jsonPath.getString("track");
    }

    /**
     * Метод на отмену заказа
     */
    @After
    public void tearDown() {
        String endPoint = "/api/v1/orders/cancel";

        Response response = given()
                .spec(Utils.requestSpec)
                .param("track", trackNumberString)
                .when()
                .put(endPoint);

        response.then().statusCode(200);
    }
}
