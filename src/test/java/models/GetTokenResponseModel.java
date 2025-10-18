package models;

import lombok.Data;

import java.util.Date;

@Data
public class GetTokenResponseModel {
    String token, status, result;
    Date expires;
}
