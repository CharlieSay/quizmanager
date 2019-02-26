package co.uk.webbiskools.quizmanager.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class BeanService{

    @Bean
    public Connection connection(){
        String URL = "jdbc:mysql://localhost:3306/Webbi";
        String username = "root";
        String password = "busted29";
        try {
            return DriverManager.getConnection(URL,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
