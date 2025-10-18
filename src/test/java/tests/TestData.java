package tests;

import com.github.javafaker.Faker;
import config.UserConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestData {
    static Faker faker = new Faker();
    static UserConfig userConfig = ConfigFactory.create(UserConfig.class, System.getProperties());

    public static final String USERNAME = userConfig.getUsername();
    public static final String PASSWORD = userConfig.getPassword();
    public static String wrongUsername = "CrazyFrog";
    public static String wrongPassword = "dsjkhfewhf";
    public static String usernameForSuccessfulCreation = faker.name().username();
    public static String passwordForSuccessfulCreation = "Person1_pass%1";
    public static String emptyUsername = "";
    public static String emptyPassword = "";
    public static String invalidUsername = "rew";
    public static String invalidPassword = "rew";
}
