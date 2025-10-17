package tests;

import api.LoginApi;
import models.BadLoginResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static tests.TestData.*;

public class AuthTests extends TestBase {
    LoginBodyModel userData = new LoginBodyModel(USERNAME, PASSWORD);
    LoginBodyModel badUserData = new LoginBodyModel(wrongUsername, wrongPassword);
    LoginApi loginApi = new LoginApi();

    @Test
    public void successfulLoginTest() {
        LoginResponseModel loginResponse = step("Make login request", () ->
                loginApi.login(userData));

        step("Check login successful", () -> {
            loginApi.loginCheck(userData, loginResponse);
        });
    }

    @Test
    public void unsuccessfulLoginTest() {
        BadLoginResponseModel badLoginResponse = step("Make incorrect login request", () ->
                loginApi.invalidLogin(badUserData));

        step("Check login unsuccessful", () -> {
            loginApi.badLoginCheck(badLoginResponse);
        });
    }
}
