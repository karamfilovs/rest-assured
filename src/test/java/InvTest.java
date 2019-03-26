import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.Client;
import pojos.Item;


public class InvTest {
    private static final String CLIENTS_ENDPOINT = "/clients";
    private static final String CLIENT_ENDPOINT = "/client";
    private static final String INVOICES_ENDPOINT = "/invoices";
    private static final String ITEM_ENDPOINT = "/item";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        RestAssured.baseURI = "https://marinar.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.authentication = RestAssured.preemptive().basic("marina_radeva@yahoo.com", "ravers");
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

    @Test
    public void createNewClient() {
        Client client = new Client();
        client.setFirm_name("Restful Firm");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 67");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Ivan Petrov");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT);
        System.out.println(createClientResponse.asString());
    }

    @Test
    public void createSingleClientToGet() {
        Client client = new Client();
        client.setFirm_name("Firm to get");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 67");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Ivan Ivanov");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT );
        System.out.println(createClientResponse.asString());
        String id = JsonPath.parse(createClientResponse.getBody().asString()).read("$.success.id").toString();
        Response getSingleClientResponse = RestAssured.given().contentType(ContentType.JSON).log().all().when().get(CLIENT_ENDPOINT + "/" + id);
        System.out.println(getSingleClientResponse.asString());
    }

    @Test
    public void createClientToUpdate() {
        Client client = new Client();
        client.setFirm_name("Firm to update");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 69");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Ivan Georgiev");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT );
        System.out.println(createClientResponse.asString());
        String id = JsonPath.parse(createClientResponse.getBody().asString()).read("$.success.id").toString();
        client.setId(id);
        client.setFirm_name("Firm to update");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 69");
        client.setFirm_is_reg_vat(true);
        client.setFirm_vat_number("204321963");
        client.setFirm_mol("Ivan Georgiev");
        Response updateClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .put(CLIENT_ENDPOINT + "/" + id);
        System.out.println(updateClientResponse.asString());


    }

    @Test
    public void createClientToDelete() {
        Client client = new Client();
        client.setFirm_name("Firm to delete");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 69");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Ivan Georgiev");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT );
        System.out.println(createClientResponse.asString());
        String id = JsonPath.parse(createClientResponse.getBody().asString()).read("$.success.id").toString();
        Response deleteClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .delete(CLIENT_ENDPOINT + "/" + id);
        System.out.println(deleteClientResponse.asString());
    }

    @Test
    public void createClientToDuplicateViaUpdate() {
        Client client = new Client();
        client.setFirm_name("Firm to duplicate");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 69");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Petur Stoqnov");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT );
        System.out.println(createClientResponse.asString());
        String id = JsonPath.parse(createClientResponse.getBody().asString()).read("$.success.id").toString();
        client.setId(id);
        client.setFirm_name("Firm to duplicate");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 69");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Petur Stoqnov");
        Response duplicateClientViaUpdateResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .put(CLIENT_ENDPOINT + "/" + id);
        System.out.println(duplicateClientViaUpdateResponse.asString());

    }

}
