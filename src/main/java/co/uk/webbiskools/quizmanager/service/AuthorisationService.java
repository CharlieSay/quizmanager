package co.uk.webbiskools.quizmanager.service;

import co.uk.webbiskools.quizmanager.dao.Authorisation;
import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class AuthorisationService {

    Connection connection;

    @Autowired
    public AuthorisationService(Connection connection) {
        this.connection = connection;
    }

    public Authorisation authoriseUserLogin(String username, String password){
        String statement = "select * FROM `Users` WHERE username=? AND passhash=SHA1(?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return new Authorisation(
                        Optional.of(resultSet.getInt("group_id")),
                        Optional.of(resultSet.getInt("id")),
                        AuthorisationToken.AUTHORISED);
            }
            return new Authorisation(Optional.empty(),Optional.empty(),AuthorisationToken.INCORRECT_CREDENETIALS);
        } catch (SQLException e) {
            return new Authorisation(Optional.empty(),Optional.empty(),AuthorisationToken.SERVICE_ERROR);
        }

    }
}
