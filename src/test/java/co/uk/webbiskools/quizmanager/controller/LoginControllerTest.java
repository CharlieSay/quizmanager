package co.uk.webbiskools.quizmanager.controller;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LoginControllerTest {

    @Test
    public void should_ReturnLoginPageHTML(){
        LoginController loginController = new LoginController();
        String string = loginController.returnLoginPage();
        assertThat(string,is("login.html"));
    }

}