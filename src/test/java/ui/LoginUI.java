package ui;

import models.CreateUserBodyModel;
import models.LoginBodyModel;
import pages.LoginPage;
import pages.ProfilePage;

import static tests.TestData.passwordForSuccessfulCreation;
import static tests.TestData.usernameForSuccessfulCreation;

public class LoginUI {
     ProfilePage profilePage = new ProfilePage();
    LoginPage loginPage = new LoginPage();

    public void loginUI(CreateUserBodyModel newUserData, LoginBodyModel loginUserData) {
        loginPage.openPage()
                .removeAds()
                .addUsername(newUserData)
                .addPassword(newUserData)
                .clickLogin()
                .checkLoginSuccessful();
        profilePage.openPage(loginUserData)
                .logout();
    }
}
