package org.codeid;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codeid.entities.Category;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;


@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryResourceTest {

    @Test
    @Order(1)
    void testFindAllCategories(){
        Response result =
                given()
                        .when().get("/category")
                        .then()
                        .statusCode(200)
                        .body(
                                containsString("tv"),
                                containsString("hp"),
                                containsString("radio")
                        )
                        .extract()
                        .response();

        List<Category> categories = result.jsonPath().getList("$");
        assertThat(categories,not(empty()));
        assertThat(categories,hasSize(3));

    }

    @Test
    @Order(2)
    void testGetCategoryById() {
        Category category =
                given()
                        .when().get("/category/{id}", 2)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Category.class);

        assertThat(category.categoryName, equalTo("hp"));
        assertThat(category.description, equalTo("smart"));

    }

    @Test
    @Order(3)
    void testCreateCategory() throws Exception {
        Category category = new Category();
        //category.categoryId=4L;
        category.categoryName = "laptop";
        category.description="dell laptop";

        Category returnedCategory =
                given()
                        .contentType(ContentType.JSON)
                        .body(category)
                        .when().post("/category")
                        .then()
                        .statusCode(201)
                        .extract()
                        .as(Category.class);

        assertThat(returnedCategory, notNullValue());
        category.categoryId = returnedCategory.categoryId;
        assertThat(returnedCategory, equalTo(category));

        Response result =
                given()
                        .when().get("/category")
                        .then()
                        .statusCode(200)
                        .body(
                                containsString("tv"),
                                containsString("hp"),
                                containsString("radio"),
                                containsString("laptop")
                        )
                        .extract()
                        .response();

        List<Category> categories = result.jsonPath().getList("$");
        assertThat(categories, not(Matchers.empty()));
        assertThat(categories, hasSize(4));
    }

    @Test
    @Order(4)
    void testPutCategory()  {

        Category category =
                given()
                        .when().get("/category/{id}", 1L)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Category.class);

        category.categoryName ="dell laptop bekas";
        category.description = "jual ajah";

        Category returnedCategory =
                given()
                        .contentType(ContentType.JSON)
                        .body(category)
                        .when().put("/category/{id}",1L)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Category.class);

        assertThat(returnedCategory, notNullValue());
        assertThat(category.categoryName, equalTo("dell laptop bekas"));
        assertThat(category.description, equalTo("jual ajah"));

    }

    @Test
    @Order(5)
    void testDeleteCategory()  {


        given()
                .when().delete("/category/{id}", 1L)
                .then()
                .statusCode(204);

/*

        Response result =
                given()
                        .when().get("/category")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        List<Category> categories = result.jsonPath().getList("$");
        assertThat(categories, not(empty()));
        assertThat(categories, hasSize(2));*/

    }


    @Test
    void testFindCategoryFailure() {
        given()
                .when().get("/category/{id}", 5)
                .then()
                .statusCode(404);
    }

    @Test
    void testCreateCategoryFailure() throws Exception {
        Category category = new Category();
        category.categoryId=4L;
        category.categoryName = "laptop";
        category.description="dell laptop";


        given()
                .contentType(ContentType.JSON)
                .body(category)
                .when().post("/category")
                .then()
                .statusCode(400);

    }
}
