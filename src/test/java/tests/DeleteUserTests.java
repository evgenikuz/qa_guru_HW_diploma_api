package tests;

import api.AccountApi;
import io.qameta.allure.*;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProfilePage;
import ui.LoginUI;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static tests.TestData.*;

@Epic("Работа с пользователями")
@Feature("Удаление пользователя")
public class DeleteUserTests extends TestBase {
    AccountApi accountApi = new AccountApi();
    LoginBodyModel loginUserData = new LoginBodyModel(usernameForSuccessfulCreation, passwordForSuccessfulCreation);
    CreateUserBodyModel newUserData = new CreateUserBodyModel(usernameForSuccessfulCreation, passwordForSuccessfulCreation);
    LoginPage loginPage = new LoginPage();
    LoginUI loginUI = new LoginUI();
//    ProfilePage profilePage = new ProfilePage();

    @Test
    @DisplayName("После удаления пользователя невозможна авторизация с его данными")
    @Story("Успешное удаление пользователя")
    @Owner("KharitonovaES")
    @Severity(CRITICAL)
    public void deleteUserTestWithUiChecks() {
        CreateUserResponseModel userResponse  = step("Create new account", () ->
                accountApi.createUserResponse(newUserData));

        step("Check account created successfully", () ->
                accountApi.createUserCheck(newUserData, userResponse));

        loginUI.loginUI(newUserData, loginUserData);
//        loginPage.openPage()
//                .removeAds()
//                .addUsername(newUserData)
//                .addPassword(newUserData)
//                .clickLogin()
//                .checkLoginSuccessful();
//        profilePage.openPage(loginUserData)
//                .logout();

        GetTokenResponseModel getTokenResponse = step("Get token", () ->
                accountApi.getTokenResponse(newUserData));

        step("Check token generated successfully", () ->
                accountApi.successfulTokenGenerationCheck(getTokenResponse));

        DeleteUserBodyModel deleteUserData = new DeleteUserBodyModel(userResponse.getUserId());
        step("Delete new account", () ->
                accountApi.deleteUserResponse(deleteUserData, userResponse, getTokenResponse));

        loginPage.openPage()
                .removeAds()
                .addUsername(newUserData)
                .addPassword(newUserData)
                .clickLogin()
                .checkLoginUnsuccessful();
    }
}
