package Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class:
 * 1.	Create new client
 * 2.	Get single client
 * 3.	Get all clients
 * 4.	Delete client
 * 5.	Update client
 * 6.	Attempt to create duplicate client via update
 */

public class ClientTestClass {

    private static final String CLIENT_ENDPOINT = "/client";
    private static final String CLIENTS_ENDPOINT = "/clients";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Static method for setting the correct URL and login for this account
     */
    static {
        RestAssured.baseURI = "https://sptechnics.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.authentication = RestAssured.preemptive().basic("stilian_popov@abv.bg", "Compaq123");
    }

    /**
     * This method create new client
     */
    @Test
    public void newClientPOST() {
        ClientClass newClient = new ClientClass();
        newClient.setFirm_name("T-mobile");
        newClient.setFirm_town("Den Haag");
        newClient.setFirm_addr("bv. Waldorpstraat 60 2521 CC");
        newClient.setFirm_bulstat("000016194616");
        newClient.setFirm_is_reg_vat(true);
        newClient.setFirm_vat_number("NL806709996B01");
        newClient.setFirm_mol("Сорен Абилдгаард");
        newClient.setFirm_mol_en("Soren Abildgaard");
        Response createClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(newClient))
                .when()
                .post(CLIENT_ENDPOINT);
        Assertions.assertEquals(200, createClientResponse.getStatusCode());
        System.out.println(createClientResponse.asString());
    }

    /**
     * This method show information about single client
     */
    @Test
    public void singleClientGET() {
        Response singleClientGET = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get("/client/80");
        Assertions.assertEquals(200, singleClientGET.getStatusCode());
        System.out.println(singleClientGET.asString());
    }

    /**
     * This method show information about all clients
     */
    @Test
    public void allClientsGET() {
        Response allClientsGET = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get(CLIENTS_ENDPOINT);
        Assertions.assertEquals(200, allClientsGET.getStatusCode());
        System.out.println(allClientsGET.asString());
    }

    /**
     * This method delete a single client
     */
    @Test
    public void singleClientDeleted() {
        Response singleClientDeleted = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .delete("client/56");
        Assertions.assertEquals(200, singleClientDeleted.getStatusCode());
        System.out.println(singleClientDeleted.asString());
    }

    /**
     * This method update information in existing client
     */
    @Test
    public void updateClient() {
        ClientClass updateClient = new ClientClass();
        updateClient.setId(80);
        updateClient.setFirm_name("Апартамент ГЕЙТ");
        updateClient.setFirm_town("София");
        updateClient.setFirm_addr("ул.„Латинка 8, кв.„Изток");
        updateClient.setFirm_is_reg_vat(false);
        updateClient.setFirm_mol("Цветан Цветанов");
        Response updateClientResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(updateClient))
                .when()
                .put(CLIENT_ENDPOINT + "/" + updateClient.getId());
        Assertions.assertEquals(200, updateClientResponse.getStatusCode());
        System.out.println(updateClientResponse.asString());
    }

    @Test
    public void duplicateClientUpdate() {
        ClientClass duplicateClientUpdate = new ClientClass();
        duplicateClientUpdate.setId(86);
        duplicateClientUpdate.setFirm_name("Апартамент ГЕЙТ");
        duplicateClientUpdate.setFirm_town("София");
        duplicateClientUpdate.setFirm_addr("ул.„Латинка 8, кв.„Изток");
        duplicateClientUpdate.setFirm_is_reg_vat(false);
        duplicateClientUpdate.setFirm_mol("Цветан Цветанов");
        Response duplicateClientUpdateResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(gson.toJson(duplicateClientUpdate))
                .when()
                .put(CLIENT_ENDPOINT + "/" + duplicateClientUpdate.getId());
        Assertions.assertEquals(200, duplicateClientUpdateResponse.getStatusCode());
        System.out.println(duplicateClientUpdateResponse.asString());
    }
}
