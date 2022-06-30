package utils;

public class ListOrderRequestParams {
    /**
     * Идентификатор курьера. Если указан - возвращает все активные и завершенные заказы этого курьера
     */
    public int courierId;
    /**
     * Фильтр станций метро. Передается в виде JSON, например: { nearestStation: ["1", "2"] }.
     * При передаче, финальная выдача фильтруется по указанным станциям метро
     */
    public String nearestStation;

    /**
     * Количество заказов на странице. Максимум: 30
     * По умолчанию: 30
     */
    public int limit;

    /**
     * Текущая страница показа заказов.
     * По умолчанию: 0
     */
    public int page;


    public ListOrderRequestParams(int courierId, String nearestStation, int limit, int page){
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }
}
