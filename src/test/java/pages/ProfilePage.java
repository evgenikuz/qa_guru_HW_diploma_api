package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.LoginBodyModel;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {
    public SelenideElement userName = $("#userName-value"),
            OkButton = $("#closeSmallModal-ok");
    public ElementsCollection bookNames = $$(".mr-2"),
                                buttons = $$("#submit");

    @Step("Open UI profile")
    public ProfilePage openPage(LoginBodyModel userData) {
        open("/profile");
        userName.shouldHave(text(userData.getUserName()));
        return this;
    }

    @Step("Remove ads")
    public ProfilePage removeAds() {
        executeJavaScript("$('footer').remove();");
        executeJavaScript("$('#fixedban').remove();");
        return this;
    }

    @Step("Click on Delete All Books button")
    public ProfilePage clickOnDeleteAllBooksButton() {
        buttons.findBy(text("Delete All Books")).click();
        return this;
    }

    @Step("Confirm removal of a book with UI")
    public ProfilePage clickOnOkButton() {
        OkButton.click();
        return this;
    }

    @Step("Close browser confirmation window with UI")
    public ProfilePage closeConfirmationWindow() {
        Selenide.confirm();
        return this;
    }

    @Step("Logout with UI")
    public ProfilePage logout() {
        buttons.findBy(text("Log Out")).click();
        return this;
    }
}

