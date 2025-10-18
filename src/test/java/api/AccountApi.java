package api;

import models.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class AccountApi {
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
        assertEquals(171, loginResponse.getToken().length());
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

    public void emptyUsersBookListCheck(LoginBodyModel userData, LoginResponseModel loginResponse, AddListOfBooksResponseModel bookResponse, GetListOfBooksResponseModel userBookResponse) {
        assertEquals(loginResponse.getUserId(), userBookResponse.getUserId());
        assertEquals(userData.getUserName(), userBookResponse.getUsername());
        assert(userBookResponse.getBooks()).isEmpty();
    }

    public GetTokenResponseModel getTokenResponse(CreateUserBodyModel newUserData) {
        return given(requestSpec)
                .body(newUserData)
                .when()
                .post("/Account/v1/GenerateToken/")
                .then()
                .spec(responseSpec(200))
                .extract().as(GetTokenResponseModel.class);
    }

    public void successfulTokenGenerationCheck(GetTokenResponseModel getTokenResponse) {
        assert getTokenResponse.getToken().length() > 150;
        assertNotNull(getTokenResponse.getExpires());
        assertEquals("Success", getTokenResponse.getStatus());
        assertEquals("User authorized successfully.", getTokenResponse.getResult());
    }

    public void deleteUserResponse(DeleteUserBodyModel deleteUserData, CreateUserResponseModel createUserResponse, GetTokenResponseModel getTokenResponse) {
         given(requestSpec)
                .header("Authorization", "Bearer " + getTokenResponse.getToken())
                .body(deleteUserData)
                .when()
                .delete("/Account/v1/User/" + createUserResponse.getUserId())
                .then()
                .spec(responseSpec(204))
                .extract();
    }

    public CreateUserResponseModel createUserResponse(CreateUserBodyModel newUserData) {
        return given(requestSpec)
                .body(newUserData)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec(201))
                .extract().as(CreateUserResponseModel.class);
    }

    public void createUserCheck(CreateUserBodyModel newUserData, CreateUserResponseModel createUserResponse) {
        assertEquals(36, createUserResponse.getUserId().length());
        assertEquals(newUserData.getUsername(), createUserResponse.getUsername());
        assert(createUserResponse.getBooks()).isEmpty();
    }

    public FailedCreateUserResponseModel failedCreateUserResponse(CreateUserBodyModel newUserData, Integer serverCode) {
        return given(requestSpec)
                .body(newUserData)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec(serverCode))
                .extract().as(FailedCreateUserResponseModel.class);
    }

    public void createFailedUserCheck(FailedCreateUserResponseModel createFailedUserResponse, Integer code, String message) {
        assertEquals(code, createFailedUserResponse.getCode());
        assertEquals(message, createFailedUserResponse.getMessage());
    }

}
