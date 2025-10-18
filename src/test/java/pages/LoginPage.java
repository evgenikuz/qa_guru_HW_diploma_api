package pages;

import com.codeborne.selenide.SelenideElement;
import models.CreateUserBodyModel;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;

public class LoginPage {
    public SelenideElement header = $("h1"),
            inputUsername = $("#userName"),
            inputPassword = $("#password"),
            loginButton = $("#login"),
            errorText = $("#name");

    public LoginPage openPage() {
        step("Open login page", () -> open("/login"));
        return this;
    }

    public LoginPage removeAds() {
        step("Remove ads", () -> {
            executeJavaScript("$('footer').remove();");
            executeJavaScript("$('#fixedban').remove();");
        });
        return this;
    }

    public LoginPage addUsername(CreateUserBodyModel newUserData) {
        step("Insert username", () -> inputUsername.sendKeys(newUserData.getUsername()));
        return this;
    }

    public LoginPage addPassword(CreateUserBodyModel newUserData) {
        step("Insert password", () -> inputPassword.sendKeys(newUserData.getPassword()));
        return this;
    }

    public LoginPage clickLogin() {
        step("Click on Login button", () -> loginButton.click());
        return this;
    }

    public LoginPage checkLoginSuccessful() {
        step("Check URL", () -> webdriver().shouldHave(url("https://demoqa.com/profile")));
        return this;
    }

    public LoginPage checkLoginUnsuccessful() {
        step("Check unsuccessful login", () -> {
            errorText.shouldHave(text("Invalid username or password!"));
            webdriver().shouldNotHave(url("https://demoqa.com/profile"));
        });
        return this;
    }

}
