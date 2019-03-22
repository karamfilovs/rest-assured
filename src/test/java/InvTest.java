import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvTest {
    private static final String CLIENTS_ENDPOINT = "/clients";
    private static final String INVOICES_ENDPOINT = "/invoices";
    private static final String ITEM_ENDPOINT = "/item";

    static {
        RestAssured.baseURI = "https://st2016.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.authentication = RestAssured.preemptive().basic("karamfilovs@gmail.com", "123456");
    }

    @Test
    public void getAllInvoices() {
        Response getInvoiceResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get(INVOICES_ENDPOINT);
        Assertions.assertEquals(200, getInvoiceResponse.getStatusCode());
    }

    @Test
    public void createNewItem(){
        Item item = new Item();
        item.setName("RestAsssuredExample");
        item.setPrice_currency("лв");
        item.setPrice_for_quantity("1");
        item.setQuantity_unit("кг.");

        Gson gson = new Gson();
        Response createItemResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(item))
                .when()
                .post(ITEM_ENDPOINT);
        System.out.println(createItemResponse.asString());
        item.setName("UpdatedName");
        Response updateItemResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(item))
                .when()
                .put(ITEM_ENDPOINT + "/2873");
        System.out.println(updateItemResponse.asString());


    }







    @Test
    public void getAllClients(){
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get(CLIENTS_ENDPOINT);
        System.out.println(response.asString());
    }


    @Test
    public void getAllItems(){
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get("/items");
        System.out.println(response.asString());
    }


}
