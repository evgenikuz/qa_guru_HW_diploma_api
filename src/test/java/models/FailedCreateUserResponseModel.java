package models;

import lombok.Data;

@Data
public class FailedCreateUserResponseModel {
    Integer code;
    String message;
}
