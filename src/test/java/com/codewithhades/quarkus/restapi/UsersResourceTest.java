package com.codewithhades.quarkus.restapi;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class UsersResourceTest {

    @Test
    void usersResourceIntegrationTest() throws Exception {
        countOfUsersShouldBe(0);
        User obiWan = userShouldBeAdded(new UserRequest("Obi-Wan", "Kenobi"));
        userShouldBeRetrievable(obiWan);
        User anakin = userShouldBeAdded(new UserRequest("Anakin", "Skywalker"));
        userShouldBeRetrievable(anakin);
        countOfUsersShouldBe(2);
        User darthVader = userShouldBeUpdated(anakin.getId(), new UserRequest("Darth", "Vader"));
        userShouldBeRetrievable(darthVader);
        userShouldBeDeleted(darthVader);
        countOfUsersShouldBe(1);
    }

    private void countOfUsersShouldBe(int count) {
        given().contentType(ContentType.JSON)
                .when().get("/api/users")
                .then().statusCode(200)
                .body("size()", is(count));
    }

    private User userShouldBeAdded(UserRequest userRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when().post("/api/users")
                .then().statusCode(200)
                .body("name", is(userRequest.getName()))
                .body("surname", is(userRequest.getSurname()))
                .extract()
                .as(User.class);
    }

    private void userShouldBeRetrievable(User user) {
        given().contentType(ContentType.JSON)
                .when().get("/api/users/" + user.getId())
                .then().statusCode(200)
                .body("id", is(user.getId()))
                .body("name", is(user.getName()))
                .body("surname", is(user.getSurname()));
    }

    private User userShouldBeUpdated(String id, UserRequest userRequest) {
        return given().contentType(ContentType.JSON)
                .body(userRequest)
                .when().put("/api/users/" + id)
                .then().statusCode(200)
                .body("id", is(id))
                .body("name", is(userRequest.getName()))
                .body("surname", is(userRequest.getSurname()))
                .extract().body().as(User.class);
    }

    private void userShouldBeDeleted(User user) {
        given().contentType(ContentType.JSON)
                .when().delete("/api/users/" + user.getId())
                .then().statusCode(200);
        given().contentType(ContentType.JSON)
                .when().get("/api/users/" + user.getId())
                .then().statusCode(204);
        given().contentType(ContentType.JSON)
                .body("{}")
                .when().put("/api/users/" + user.getId())
                .then().statusCode(204);
        given().contentType(ContentType.JSON)
                .when().delete("/api/users/" + user.getId())
                .then().statusCode(204);
    }

}