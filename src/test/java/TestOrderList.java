import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ListOrderRequestParams;
import utils.Utils;

import static io.restassured.RestAssured.given;


public class TestOrderList {

    /**
     * Ручка
     */
    private String endPoint = Utils.END_POINT_ORDER_LIST;


    @Before
    public void setUp() {
        Utils.createNewCourier();
    }

    @After
    public void tearDown(){
        Utils.deleteNewCourier();
    }

    /**
     * Тест на получение списка заказов для курьера с определенным id
     * Выполняется проверка запроса с Query параметрами
     * id - id курьера
     * nearestStation - 0
     * limit - 0
     * page - 0
     */
    @Test
    public void testGetOrderList() {


        ListOrderRequestParams list =
                new ListOrderRequestParams(Utils.getCourierId(), "0", 0, 0);

        Response response = given()
                .spec(Utils.requestSpec)
                .param("courierId", list.courierId)
                .param("nearestStation", list.nearestStation)
                .param("limit", list.limit)
                .param("page", list.page)
                .when()
                .get(endPoint);

        response.then().statusCode(200);
    }

    /**
     * Тест на получение списка заказов если не заполнить Query параметры при выполнении запроса
     * Выполняется запрос с пустыми значениями Query параметров
     */
    @Test
    public void testOrderListNullParam() {
        Response response = given()
                .spec(Utils.requestSpec)
                .param("courierId", "")
                .param("nearestStation", "")
                .param("limit", "")
                .param("page", "")
                .when()
                .get(endPoint);

        response.then().statusCode(200);
    }
}
