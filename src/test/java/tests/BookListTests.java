package tests;

import api.BookStoreApi;
import io.qameta.allure.*;
import models.GetListOfBooksResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("Книги")
@Feature("Работа с БД книг")
public class BookListTests extends TestBase {
    BookStoreApi bookApi = new BookStoreApi();

    @Test
    @DisplayName("Запрос возвращает список всех книг в БД")
    @Story("Получение списка всех книг")
    @Owner("KharitonovaES")
    @Severity(NORMAL)
    public void getAllBooksTest() {
        GetListOfBooksResponseModel allBooksInStore = step("Get all books in store",  () ->
                bookApi.getAllBooks());
        step("Check all books are present", () ->
            bookApi.checkAllBooksArePresentInStore(allBooksInStore));
    }
}
