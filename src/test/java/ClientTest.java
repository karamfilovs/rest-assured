import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pojos.Client;

import java.util.logging.Logger;

public class ClientTest {

    private static final String CLIENT_ENDPOINT = "/client";
    private static final String CLIENTS_ENDPOINT = "/clients";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    static {
        RestAssured.baseURI = "https://peychev-korporeyted-odd.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.authentication = RestAssured.preemptive().basic("peychev.vn@gmail.com", "Vanessa1574");
    }


    @Test
    public void createNewClient() {
        Client client = new Client();
        client.setFirm_name("Ястреб");
        client.setFirm_name_en("Hawk");
        client.setFirm_town("София");
        client.setFirm_addr("Чурулик 15");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Иван Чайков");
        Response createClientResponese = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .post(CLIENT_ENDPOINT);
        Assertions.assertEquals(200, createClientResponese.getStatusCode());
    }

    @Test
    public void getSingleClient() {
        Response getSingleClient = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get("client/38");

        Assertions.assertEquals(200, getSingleClient.getStatusCode());
    }

    @Test
    public void getAllClients() {
        Response getAllClients = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get(CLIENTS_ENDPOINT);
        Assertions.assertEquals(200, getAllClients.getStatusCode());
        ;
    }

    @Test
    public void deleteSingleClient() {
        Response deleteSingleClient = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .delete("client/38");
        Assertions.assertEquals(200, deleteSingleClient.getStatusCode());
    }

    @Test
    public void updateClient() {
        Client client = new Client();
        client.setId(6);
        client.setFirm_name("Зоокът 2000");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 345 A");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Ivan Ivanov");
        Response updateClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .put(CLIENT_ENDPOINT + "/" + client.getId());
        System.out.println(updateClientResponse.asString());
        Assertions.assertEquals(200, updateClientResponse.getStatusCode());
    }

    @Test //такъв клиент не съществува
    public void createDuplicateClient() {
        Client client = new Client();
        client.setId(61);
        client.setFirm_name("Зоокът 2000");
        client.setFirm_town("Sofia");
        client.setFirm_addr("bul. Bulgaria 345 A");
        client.setFirm_is_reg_vat(false);
        client.setFirm_mol("Ivan Ivanov");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(client))
                .when()
                .put(CLIENT_ENDPOINT + "/" + client.getId());
        System.out.println(createClientResponse.asString());
        Assertions.assertEquals(200, createClientResponse.getStatusCode());
    }
}
