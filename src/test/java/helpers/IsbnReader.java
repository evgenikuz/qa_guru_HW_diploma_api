package helpers;

import models.CollectionOfIsbnsModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IsbnReader {
    public static List<CollectionOfIsbnsModel> readIsbnsAsCollection() {
        List<CollectionOfIsbnsModel> isbnList = new ArrayList<>();
        try {
            List<String> isbns = Files.readAllLines(Paths.get("src/test/resources/isbns.txt"));

            for (String isbn : isbns) {
                CollectionOfIsbnsModel isbnModel = new CollectionOfIsbnsModel();
                isbnModel.setIsbn(isbn);
                isbnList.add(isbnModel);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл с ISBN", e);
        }

        return isbnList;
    }

    public static List<String> readIsbnsAsStrings() {
        List<String> isbnsAsStrings;
        try {
            isbnsAsStrings = Files.readAllLines(Paths.get("src/test/resources/isbns.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл с ISBN", e);
        }
        return isbnsAsStrings;
    }
}
