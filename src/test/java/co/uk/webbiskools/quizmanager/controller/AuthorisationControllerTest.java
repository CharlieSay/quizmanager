package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Authorisation;
import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;
import co.uk.webbiskools.quizmanager.service.AuthorisationService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthorisationControllerTest {

    @Mock
    AuthorisationService authorisationService;

    @Mock
    Authorisation authorisation;

    @Before
    public void setUp(){
        authorisationService = mock(AuthorisationService.class);
        authorisation = mock(Authorisation.class);
    }

    @Test
    public void should_ReturnAuthorisedToken_WhenCredentialsAreCorrect(){
        when(authorisationService.authoriseUserLogin(anyString(),anyString())).thenReturn(authorisation);
        when(authorisation.getGroupId()).thenReturn(Optional.of(1));
        when(authorisation.getAuthorisationToken()).thenReturn(AuthorisationToken.AUTHORISED);

        AuthorisationController authorisationController = new AuthorisationController(authorisationService);

        ResponseEntity user = authorisationController.getUser("user", "password");

       verify(authorisation).getAuthorisationToken();
       verify(authorisationService).authoriseUserLogin(anyString(),anyString());
       assertTrue(user.getStatusCodeValue() == 200);
    }

}