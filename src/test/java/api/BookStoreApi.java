package api;

import helpers.IsbnReader;
import models.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class BookStoreApi {
    List<String> expectedIsbns = IsbnReader.readIsbnsAsStrings();
    public void addAllBooksToIsbnCollection(AddListOfBooksBodyModel bookData, LoginResponseModel loginResponse) {
        bookData.setUserId(loginResponse.getUserId());
        List<CollectionOfIsbnsModel> isbnList = IsbnReader.readIsbnsAsCollection();
        bookData.setCollectionOfIsbns(isbnList);
    }

    public AddListOfBooksResponseModel bookAdd(AddListOfBooksBodyModel bookData, LoginResponseModel loginResponse) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(201))
                .extract().as(AddListOfBooksResponseModel.class);
    }

    public void booksCheck(AddListOfBooksResponseModel bookResponse) {
        for (int i = 0; i < expectedIsbns.size(); i++) {
            assertEquals(expectedIsbns.get(i), bookResponse.getBooks().get(i).getIsbn(),
                    "ISBN не совпадает для книги с индексом " + i);
        }
    }

    public GetListOfBooksResponseModel getAllBooks() {
        return given(requestSpec)
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(200))
                .extract().as(GetListOfBooksResponseModel.class);
    }

    public void checkAllBooksArePresentInStore(GetListOfBooksResponseModel allBooksResponse) {
        assertEquals(8, allBooksResponse.getBooks().size());
        List<String> actualISBNs = allBooksResponse.getBooks().stream()
                .map(GetListOfBooksResponseModel.Books::getIsbn)
                .toList();
        for (int i = 0; i < expectedIsbns.size(); i++) {
            assertEquals(expectedIsbns.get(i), actualISBNs.get(i),
                    "ISBN не совпадает для книги с индексом " + i);
        }
    }
}
