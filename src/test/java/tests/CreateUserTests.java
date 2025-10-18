package tests;

import api.AccountApi;
import io.qameta.allure.*;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static tests.TestData.*;

@Epic("Работа с пользователями")
@Feature("Создание пользователя")
public class CreateUserTests extends TestBase {
    AccountApi accountApi = new AccountApi();
    CreateUserBodyModel newUserData = new CreateUserBodyModel(usernameForSuccessfulCreation, passwordForSuccessfulCreation);
    CreateUserBodyModel emptyUserData = new CreateUserBodyModel(emptyUsername, emptyPassword);
    CreateUserBodyModel invalidUserData = new CreateUserBodyModel(invalidUsername, invalidPassword);

    @Test
    @DisplayName("Успешное создание нового пользователя")
    @Story("Создание нового пользователя")
    @Owner("KharitonovaES")
    @Severity(CRITICAL)
    public void successfulNewUserCreationTest() {
        CreateUserResponseModel userResponse  = step("Create new account", () ->
            accountApi.createUserResponse(newUserData));

        step("Check account created successfully", () ->
                accountApi.createUserCheck(newUserData, userResponse));

        GetTokenResponseModel getTokenResponse = step("Get token", () ->
                accountApi.getTokenResponse(newUserData));

        step("Check token generated successfully", () ->
                accountApi.successfulTokenGenerationCheck(getTokenResponse));

        DeleteUserBodyModel deleteUserData = new DeleteUserBodyModel(userResponse.getUserId());
        step("Delete new account", () ->
                accountApi.deleteUserResponse(deleteUserData, userResponse, getTokenResponse));
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    @Story("Создание нового пользователя")
    @Owner("KharitonovaES")
    @Severity(CRITICAL)
    public void CreationOfAnExistingUserTest() {
        CreateUserResponseModel userResponse  = step("Create new account", () ->
                accountApi.createUserResponse(newUserData));

        step("Check account created successfully", () -> {
            accountApi.createUserCheck(newUserData, userResponse);
        });

        FailedCreateUserResponseModel existingUserResponse  = step("Create same new account again", () ->
                accountApi.failedCreateUserResponse(newUserData, 406));

        step("Check account wasn't created because user already exists", () ->
            accountApi.createFailedUserCheck(existingUserResponse, 1204, "User exists!"));

        GetTokenResponseModel getTokenResponse = step("Get token", () ->
                accountApi.getTokenResponse(newUserData));

        step("Check token generated successfully", () ->
            accountApi.successfulTokenGenerationCheck(getTokenResponse));

        DeleteUserBodyModel deleteUserData = new DeleteUserBodyModel(userResponse.getUserId());
        step("Delete new account", () ->
                accountApi.deleteUserResponse(deleteUserData, userResponse, getTokenResponse));
    }

    @Test
    @DisplayName("При создании нового пользователя без данных возвращается ошибка UserName and Password required.")
    @Story("Создание нового пользователя без данных")
    @Owner("KharitonovaES")
    @Severity(CRITICAL)
    public void newUserWithNoCredentialsCreationTest() {
        FailedCreateUserResponseModel emptyUserResponse  = step("Create new account with no credentials", () ->
                accountApi.failedCreateUserResponse(emptyUserData, 400));

        step("Check account wasn't created because of no credentials", () ->
            accountApi.createFailedUserCheck(emptyUserResponse, 1200, "UserName and Password required."));
    }

    @Test
    @DisplayName("При создании нового пользователя с невалидными данными возвращается ошибка Passwords must have at least one...")
    @Story("Создание нового пользователя с невалидными данными")
    @Owner("KharitonovaES")
    @Severity(CRITICAL)
    public void newUserWithInvalidCredentialsCreationTest() {
        FailedCreateUserResponseModel badUserResponse  = step("Create new account with bad credentials", () ->
                accountApi.failedCreateUserResponse(invalidUserData, 400));

        step("Check account wasn't created because of bad credentials", () ->
            accountApi.createFailedUserCheck(badUserResponse, 1300, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer."));
    }
}
