import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.Item;

public class InvTest {
    private static final String CLIENTS_ENDPOINT = "/clients";
    private static final String INVOICES_ENDPOINT = "/invoices";
    private static final String ITEM_ENDPOINT = "/item";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
        item.setPrice_for_quantity(1);
        item.setQuantity_unit("кг.");
        Response createItemResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(item))
                .when()
                .post(ITEM_ENDPOINT);
        System.out.println(createItemResponse.asString());
    }

    @Test
    public void createDeleteItem(){
        Item item = new Item();
        item.setName("TOBEDELETED");
        item.setPrice_for_quantity(1);
        item.setQuantity_unit("кг.");
        Response createItemResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(item))
                .when()
                .post(ITEM_ENDPOINT);
        System.out.println(createItemResponse.asString());
        String id = JsonPath.parse(createItemResponse.getBody().asString()).read("$.success.id").toString();
        Response deleteResponse = RestAssured.given().contentType(ContentType.JSON).log().all().when().delete(ITEM_ENDPOINT + "/" + id);
        System.out.println(deleteResponse.asString());
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
