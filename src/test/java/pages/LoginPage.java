package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.CreateUserBodyModel;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginPage {
    public SelenideElement header = $("h1"),
            inputUsername = $("#userName"),
            inputPassword = $("#password"),
            loginButton = $("#login"),
            errorText = $("#name");

    @Step("Open login page")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Remove ads")
    public LoginPage removeAds() {
        executeJavaScript("$('footer').remove();");
        executeJavaScript("$('#fixedban').remove();");
        return this;
    }

    @Step("Insert username")
    public LoginPage addUsername(CreateUserBodyModel newUserData) {
        inputUsername.sendKeys(newUserData.getUsername());
        return this;
    }

    @Step("Insert password")
    public LoginPage addPassword(CreateUserBodyModel newUserData) {
        inputPassword.sendKeys(newUserData.getPassword());
        return this;
    }

    @Step("Click on Login button")
    public LoginPage clickLogin() {
        loginButton.click();
        return this;
    }

    @Step("Check URL")
    public LoginPage checkLoginSuccessful() {
        webdriver().shouldHave(url("https://demoqa.com/profile"));
        return this;
    }

    @Step("Check unsuccessful login")
    public LoginPage checkLoginUnsuccessful() {
        errorText.shouldHave(text("Invalid username or password!"));
        webdriver().shouldNotHave(url("https://demoqa.com/profile"));
        return this;
    }

}
