package tests;

import api.BookListApi;
import models.AddListOfBooksBodyModel;
import models.GetListOfBooksResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class BookListTests extends TestBase {
    BookListApi bookApi = new BookListApi();

    @Test
    public void getAllBooksTest() {
        GetListOfBooksResponseModel allBooksInStore = step("Get all books in store",  () ->
                bookApi.getAllBooks());
        step("Check all books are present", () -> {
            bookApi.checkAllBooksPresent(allBooksInStore);
        });
    }
}
