package api;

import models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class BookListApi {
    public void addBookToISBNCollection(AddListOfBooksBodyModel bookData, LoginResponseModel loginResponse) {
        bookData.setUserId(loginResponse.getUserId());
        List<CollectionOfIsbnsModel> isbnList = new ArrayList<>();
        CollectionOfIsbnsModel isbn1 = new CollectionOfIsbnsModel();
        isbn1.setIsbn("9781449325862");
        isbnList.add(isbn1);
        CollectionOfIsbnsModel isbn2 = new CollectionOfIsbnsModel();
        isbn2.setIsbn("9781449331818");
        isbnList.add(isbn2);
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
        assertEquals("9781449325862", bookResponse.getBooks().get(0).getIsbn());
        assertEquals("9781449331818", bookResponse.getBooks().get(1).getIsbn());
    }

    public GetListOfBooksResponseModel getAllBooks() {
        return given(requestSpec)
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(200))
                .extract().as(GetListOfBooksResponseModel.class);
    }

    public void checkAllBooksPresent(GetListOfBooksResponseModel allBooksResponse) {
        assertEquals(8, allBooksResponse.getBooks().size());
        List<String> expectedISBNs;
        try {
            expectedISBNs = Files.readAllLines(Paths.get("src/test/resources/isbns.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл с ISBN", e);
        }
        List<String> actualISBNs = allBooksResponse.getBooks().stream()
                .map(GetListOfBooksResponseModel.Books::getIsbn)
                .toList();

        for (int i = 0; i < expectedISBNs.size(); i++) {
            assertEquals(expectedISBNs.get(i), actualISBNs.get(i),
                    "ISBN не совпадает для книги с индексом " + i);
        }
    }
}
