package tests;

import com.github.javafaker.Faker;

public class TestData {
    static Faker faker = new Faker();

    public static final String USERNAME = System.getProperty("username");
    public static final String PASSWORD = System.getProperty("password");
    public static String wrongUsername = "CrazyFrog";
    public static String wrongPassword = "dsjkhfewhf";
    public static String usernameForSuccessfulCreation = faker.name().username();
    public static String passwordForSuccessfulCreation = "Person1_pass%1";
    public static String emptyUsername = "";
    public static String emptyPassword = "";
    public static String invalidUsername = "rew";
    public static String invalidPassword = "rew";
}
