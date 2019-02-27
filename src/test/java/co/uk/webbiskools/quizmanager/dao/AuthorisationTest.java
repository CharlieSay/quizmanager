package co.uk.webbiskools.quizmanager.dao;

import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class AuthorisationTest {

    @Test
    public void whenGroupIdIsNotPresentAndValidAuth_ShouldNullGroupId(){
        Authorisation authorisation;

        authorisation = new Authorisation(Optional.empty(),Optional.empty(), AuthorisationToken.INCORRECT_CREDENETIALS);

        assertEquals(Optional.empty(),authorisation.getGroupId());
    }

    @Test
    public void whenValidGroupIdAndValidAuth_ShouldReturnCompletedAuthorisation(){
        Authorisation authorisation;
        Integer groupIdValue = 1;
        Integer userIdValue = 1;
        AuthorisationToken authorisationToken = AuthorisationToken.AUTHORISED;

        authorisation = new Authorisation(Optional.of(groupIdValue),Optional.of(userIdValue),authorisationToken);

        assertEquals(authorisation.getGroupId().get(),groupIdValue);
        assertEquals(authorisation.getAuthorisationToken(),(authorisationToken));
    }

}