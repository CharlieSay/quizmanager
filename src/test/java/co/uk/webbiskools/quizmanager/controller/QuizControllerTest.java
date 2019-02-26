package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.service.QuizService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizControllerTest {

    QuizController quizController;

    QuizService quizService;

    @Before
    public void setUp(){
        quizService = mock(QuizService.class);
        quizController = new QuizController(quizService);
    }

    @Test
    public void should_AddNewQuestion_ThenReturnOkResponseEntityWithHttpStatusAccepted(){
        when(quizService.addNewQuestion(eq("correctQuestion"),any(),any(),any(),any(),any())).thenReturn(true);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuestion("correctQuestion", "1", "option1", "option2", "option3",
                "option4", "option5");

        assertEquals(200,httpStatusResponseEntity.getStatusCodeValue());
    }

}