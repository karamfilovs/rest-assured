import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public void getSingleClient() {
        Client client = new Client();
        client.setId(20);
        Response getSingleClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get(CLIENT_ENDPOINT + "/" + client.getId());
        System.out.println(getSingleClientResponse.asString());
    }

    @Test
    public void updateClient() {
        Client client = new Client();
        client.setId(20);
        client.setFirm_name("Restful Firm");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 67");
        client.setFirm_is_reg_vat(true);
        client.setFirm_vat_number("204328789");
        client.setFirm_mol("Ivan Petrov");
        Response updateClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .put(CLIENT_ENDPOINT + "/" + client.getId());
        System.out.println(updateClientResponse.asString());

    }

    @Test
    public void deleteClient() {
        Client client = new Client();
        client.setId(20);
        Response deleteClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .delete(CLIENT_ENDPOINT + "/" + client.getId());
        System.out.println(deleteClientResponse.asString());
    }

    @Test
    public void createDublicateClientUpdate() {
        Client client = new Client();
        client.setId(26);
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
                .put(CLIENT_ENDPOINT + "/" + client.getId());
        System.out.println(createClientResponse.asString());
    }

}
