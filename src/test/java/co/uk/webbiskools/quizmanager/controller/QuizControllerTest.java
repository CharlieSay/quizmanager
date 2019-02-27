package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Quiz;
import co.uk.webbiskools.quizmanager.service.QuizService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class QuizControllerTest {

    QuizController quizController;

    QuizService quizService;

    Quiz quiz;

    @Before
    public void setUp(){
        quiz = mock(Quiz.class);
        quizService = mock(QuizService.class);
        quizController = new QuizController(quizService);
    }

//    @Test
//    public void should_GetQuizById_IfQuizIdIsValid(){
//        when(quizService.getQuizById(anyInt())).thenReturn(new Quiz(1,"Title"));
//
//        ResponseEntity<String> quizById = quizController.getQuizById("1");
//
//        verify(quizController.getQuizById(eq("1")));
//        assertEquals(quizById.getStatusCode(),200);
//    }

    @Test
    public void should_getAllQuizzes(){
        List<Quiz> quizList = new ArrayList<>();
        when(quizService.getAllQuiz()).thenReturn(quizList);

        ResponseEntity<String> allQuizzes = quizController.getAllQuizzes();

        verify(quizService).getAllQuiz();

        assertNotNull(allQuizzes);
    }

    @Test
    public void should_AddNewQuestion_ThenReturnOkResponseEntityWithHttpStatusAccepted(){
        when(quizService.addNewQuestion(eq("correctQuestion"),any(),any(),any(),any(),any())).thenReturn(true);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuestion("correctQuestion", "1", "option1", "option2", "option3",
                "option4", "option5");

        assertEquals(200,httpStatusResponseEntity.getStatusCodeValue());
    }

    @Test
    public void should_NotAddNewQuestion_WhenFalseReturnedFromService(){
        when(quizService.addNewQuestion(any(),any(),any(),any(),any())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuestion("incorrectQuestion", "1", "option1", "option2", "option3",
                "option4", "option5");

        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
    }

}