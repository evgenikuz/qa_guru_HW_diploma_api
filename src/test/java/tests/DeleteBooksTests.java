package tests;

import api.BookStoreApi;
import api.AccountApi;
import io.qameta.allure.*;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.DeleteUI;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static tests.TestData.PASSWORD;
import static tests.TestData.USERNAME;

@Epic("Работа с профилем")
@Feature("Удаление книг")
public class DeleteBooksTests extends TestBase {
    LoginBodyModel userData = new LoginBodyModel(USERNAME, PASSWORD);
    AccountApi accountApi = new AccountApi();
    BookStoreApi bookApi = new BookStoreApi();
    AddListOfBooksBodyModel bookData = new AddListOfBooksBodyModel();
    DeleteUI deleteUI = new DeleteUI();

    @Test
    @DisplayName("Кнопка Delete All Books в профиле удаляет все книги из профиля")
    @Story("Удаление всех книг по кнопке в профиле")
    @Owner("KharitonovaES")
    @Severity(NORMAL)
    public void deleteAllBooksTest() {
        LoginResponseModel loginResponse = step("Make login request", () ->
                accountApi.login(userData));

        step("Check login successful", () ->
            accountApi.loginCheck(userData, loginResponse));

        bookApi.addAllBooksToIsbnCollection(bookData, loginResponse);
        AddListOfBooksResponseModel bookResponse = step("Make request to add list of all books to profile", () ->
                bookApi.bookAdd(bookData, loginResponse));

        step("Check all books are added", () ->
            bookApi.booksCheck(bookResponse));

        step("Delete all books with UI", () ->
            deleteUI.DeleteAllBooksWithUI(loginResponse, userData, bookResponse));

        GetListOfBooksResponseModel userBookResponse = step("Make request to get a list of user's books", () ->
                accountApi.getUserBookResponse(loginResponse));

        step("Confirm removal of all books with api by response", () ->
            accountApi.emptyUsersBookListCheck(userData, loginResponse, bookResponse, userBookResponse));
    }
}
