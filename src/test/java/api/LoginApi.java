package api;

import models.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class LoginApi {
    public LoginResponseModel login(LoginBodyModel userData) {
        return given(requestSpec)
                .body(userData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec(200))
                .extract().as(LoginResponseModel.class);
    }

    public BadLoginResponseModel invalidLogin(LoginBodyModel userData) {
        return given(requestSpec)
                .body(userData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec(400))
                .extract().as(BadLoginResponseModel.class);
    }

    public void loginCheck(LoginBodyModel userData, LoginResponseModel loginResponse) {
        assertEquals(userData.getUserName(), loginResponse.getUsername());
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImthdGVfc21pdGgiLCJwYXNzd29yZCI6IkthdGVzbWl0aDkkIiwiaWF0IjoxNzYwNzI1NzIxfQ.FtUDcjtkTAP_gOso6LskKDoH3Fz-s2dVbaQbJauuxMo", loginResponse.getToken());
        assertEquals("450c031d-1bc6-4338-92a9-c5972049648c", loginResponse.getUserId());
    }

    public void badLoginCheck(BadLoginResponseModel badLoginResponse) {
        assertEquals(400, badLoginResponse.getCode());
        assertEquals("Wrong Login or Password", badLoginResponse.getMessage());
    }

    public GetListOfBooksResponseModel getUserBookResponse(LoginResponseModel loginResponse) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .when()
                .get("/Account/v1/User/" + loginResponse.getUserId())
                .then()
                .spec(responseSpec(200))
                .extract().as(GetListOfBooksResponseModel.class);
    }

    public void usersBookListCheck(LoginBodyModel userData, LoginResponseModel loginResponse, AddListOfBooksResponseModel bookResponse, GetListOfBooksResponseModel userBookResponse) {
        assertEquals(loginResponse.getUserId(), userBookResponse.getUserId());
        assertEquals(userData.getUserName(), userBookResponse.getUsername());
        assertEquals(bookResponse.getBooks().get(0).getIsbn(), userBookResponse.getBooks().get(0).getIsbn());
        assertFalse(userBookResponse.getBooks().stream().anyMatch(books -> bookResponse.getBooks().get(1).getIsbn().equals(books.getIsbn())));
    }
}
