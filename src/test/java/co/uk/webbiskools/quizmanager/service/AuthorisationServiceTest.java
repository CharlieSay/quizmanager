package co.uk.webbiskools.quizmanager.service;

import co.uk.webbiskools.quizmanager.dao.Authorisation;
import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthorisationServiceTest {

    Connection connection;
    AuthorisationService authorisationService;
    ResultSet resultSet;
    PreparedStatement preparedStatement;

    @Before
    public void setUp(){
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        preparedStatement = mock(PreparedStatement.class);
        authorisationService = new AuthorisationService(connection);
    }

    @Test
    public void should_ReturnValidAuthorisation_WhenCredentialsAreOk() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(1);

        Authorisation authorisation = authorisationService.authoriseUserLogin("correctuser", "correctpassword");

        Optional<Integer> groupId = authorisation.getGroupId();
        AuthorisationToken authorisationToken = authorisation.getAuthorisationToken();

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt(anyString());
        assertThat(groupId.get(),is(1));
        assertThat(authorisationToken,is(AuthorisationToken.AUTHORISED));
    }

    @Test
    public void should_ReturnInvalidAuthorisation_WhenCredentialsAreNotOk() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        when(resultSet.getInt(anyString())).thenReturn(1);

        Authorisation authorisation = authorisationService.authoriseUserLogin("incorrectuser", "correctpassword");

        Optional<Integer> groupId = authorisation.getGroupId();
        AuthorisationToken authorisationToken = authorisation.getAuthorisationToken();

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        assertThat(groupId,is(Optional.empty()));
        assertThat(authorisationToken,is(AuthorisationToken.INCORRECT_CREDENETIALS));
    }

}