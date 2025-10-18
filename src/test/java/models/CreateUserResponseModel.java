package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserResponseModel {
    @JsonProperty("userID")
    String userId;
    String username;
    List<CollectionOfIsbnsModel> books;
}
