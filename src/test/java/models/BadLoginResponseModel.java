package models;

import lombok.Data;

@Data
public class BadLoginResponseModel {
    Integer code;
    String message;
}
