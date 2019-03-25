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
    private static final String INVOICES_ENDPOINT = "/invoices";
    private static final String ITEM_ENDPOINT = "/item";
    private static final String CLIENT_ENDPOINT = "/client";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        RestAssured.baseURI = "https://hovsep-krikoryan.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.authentication = RestAssured.preemptive().basic("h.krikoryan@gmail.com", "123456");
    }

    /**
     * This test retrieves all invoices
     */
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

    /**
     * This test creates a new item and checks if it's been properly created
     */
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

    /**
     * This test retrieves all clients
     */
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

    /**
     * This test retrieves all items
     */
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

    /**
     * This test creates a new client and checks if he/she has been properly created
     */
    @Test
    public void postNewClient() {
        Client client = new Client();
        client.setFirm_name("Произволен тест");
        client.setFirm_name_en("Some random test");
        client.setFirm_town("Варна");
        client.setFirm_addr("Ул. Славейков 22");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Иван Иванов");
        client.setFirm_mol_en("Ivan Ivanov");
        Response postNewClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT);
        System.out.println(postNewClientResponse.asString());
    }

    /**
     * This test gets a single user through his id
     * @param - setId - sets the id we would want to get
     */
    @Test
    public void getSingleClient(){
        Client getclient = new Client();
        getclient.setId(38);
        Response getClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get(CLIENT_ENDPOINT + "/" + getclient.getId());
        System.out.println(getClientResponse.asString());
    }

    /**
     * This test deletes a client
     * The desired id to be deleted is done in the deleteclient.setId()
     */
    @Test
    public void deleteClient(){
        Client deleteclient = new Client();
        deleteclient.setId(38);
        Response deleteClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .delete(CLIENT_ENDPOINT + "/" + deleteclient.getId());
        System.out.println(deleteClientResponse.asString());
    }

    /**
     * This test updates an already existing client
     */
    @Test
    public void putClient(){
        Client updateclient = new Client();
        updateclient.setId(35);
        updateclient.setFirm_name("Ново име на фирмата");
        updateclient.setFirm_name_en("New firm name");
        updateclient.setFirm_town("Бургас");
        updateclient.setFirm_addr("Някаква си друга улица");
        updateclient.setFirm_bulstat("");
        updateclient.setFirm_is_reg_vat(false);
        updateclient.setFirm_vat_number("");
        updateclient.setFirm_mol("Петър Петров");
        updateclient.setFirm_mol_en("Petar Petrov");
        Response updateClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(updateclient))
                .when()
                .put(CLIENT_ENDPOINT + "/" + updateclient.getId());
        System.out.println(updateClientResponse.asString());
    }

    /**
     * This method attempts to create a duplicate client via update
     */
    @Test
    public void putDuplicateClient(){
        Client updatedupclient = new Client();
        updatedupclient.setId(20);
        updatedupclient.setFirm_name("Ново име на фирмата");
        updatedupclient.setFirm_name_en("New firm name");
        updatedupclient.setFirm_town("Бургас");
        updatedupclient.setFirm_addr("Някаква си друга улица");
        updatedupclient.setFirm_bulstat("");
        updatedupclient.setFirm_is_reg_vat(false);
        updatedupclient.setFirm_vat_number("");
        updatedupclient.setFirm_mol("Петър Петров");
        updatedupclient.setFirm_mol_en("Petar Petrov");
        Response updateClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(updatedupclient))
                .when()
                .put(CLIENT_ENDPOINT + "/" + updatedupclient.getId());
        System.out.println(updateClientResponse.asString());
    }
}
