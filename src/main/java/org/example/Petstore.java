package org.example;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Petstore {

    @BeforeClass
    public void setup() {
        baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void cadastrarNovoPedidoDePetComSucesso() {
        orderPet pet = new orderPet();
        pet.setId("1999");
        pet.setPetId("2908");
        pet.setQuantity("2");
        pet.setShipDate(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        pet.setStatus("placed");
        pet.setComplete("true");

        given().contentType(ContentType.JSON).body(pet)
                .when().post("/store/order").then().statusCode(200)
                .body("id", equalTo(Integer.parseInt(pet.getId())))
                .log().all();
    }

    @Test
    public void pesquisarPetInexistente() {

        given().pathParam("id", "12345678").when().get("/pet/{id}").then()
                .statusCode(404).body("message", equalTo("Pet not found")).log().all();

    }

    @Test
    public void atualizarDadosPetExistente() {

        Tag tag = new Tag();
        tag.setId(29);
        tag.setName("MyTag");

        Category category = new Category();
        category.setId(1999);
        category.setName("Siegh's Dog");

        Pet pet = new Pet();
        pet.setId(2);
        pet.setCategory(category);
        pet.setName("Apollo");
        pet.setPhotoUrls(List.of("https://www.petz.com.br/cachorro/racas/chow-chow/img/chow-chow-guia-racas.webp"));
        pet.setTags(List.of(tag));
        pet.setStatus("pending");

        given().contentType(ContentType.JSON).body(pet).when().put("/pet").then()
                .body("category.id", equalTo(category.getId()))
                .statusCode(200).log().all();
    }

    @Test
    public void pesquisarPetInformandoIdComFormatoInvalido() {

        String idInvalido = "123!asd";

        given().get("/pet/" + idInvalido).then()
                .body("message", containsString("NumberFormatException: For input string:"))
                .body("message", containsString(idInvalido)).body("code", equalTo(404))
                        .statusCode(404).log().all();

    }

    @Test
    public void pesquisarPetStatusPending() {

        given().queryParam("status", "pending").get("/pet/findByStatus/").then()
                .body("status", everyItem(equalTo("pending")))
                .body("id", notNullValue())
                .body("name", everyItem(not(isEmptyOrNullString()))).statusCode(200).log().all();

    }

    @Test
    public void realizarRequisicaoPetMetodoInvalido() {

        given().when().delete("/pet").then().statusCode(405).log().all();
    }

}