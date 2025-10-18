package tests;

import api.AccountApi;
import io.qameta.allure.*;
import models.BadLoginResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static tests.TestData.*;

@Epic("Работа с пользователями")
@Feature("Авторизация пользователя")
public class AuthTests extends TestBase {
    LoginBodyModel userData = new LoginBodyModel(USERNAME, PASSWORD);
    LoginBodyModel badUserData = new LoginBodyModel(wrongUsername, wrongPassword);
    AccountApi accountApi = new AccountApi();

    @Test
    @DisplayName("Успешный вход в систему с валидной парой логин-пароль")
    @Story("Успешный вход в систему")
    @Owner("KharitonovaES")
    @Severity(CRITICAL)
    public void successfulLoginTest() {
        LoginResponseModel loginResponse = step("Make login request", () ->
                accountApi.login(userData));

        step("Check login successful", () ->
            accountApi.loginCheck(userData, loginResponse));
    }

    @Test
    @DisplayName("Ошибка User does not exist при входе в систему с неверным логином и паролем")
    @Story("Ошибка при входе в систему с неверным логином и паролем")
    @Severity(CRITICAL)
    @Owner("KharitonovaES")
    @Disabled("баг")
    public void unsuccessfulLoginTest() {
        BadLoginResponseModel badLoginResponse = step("Make incorrect login request", () ->
                accountApi.invalidLogin(badUserData));

        step("Check login unsuccessful", () ->
            accountApi.badLoginCheck(badLoginResponse));
    }
}
