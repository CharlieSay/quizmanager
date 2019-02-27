package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Quiz;
import co.uk.webbiskools.quizmanager.service.QuizService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class QuizControllerTest {

    QuizController quizController;

    QuizService quizService;


    @Before
    public void setUp(){
        quizService = mock(QuizService.class);
        quizController = new QuizController(quizService);
    }

    @Test
    public void should_GetQuizById_WhenValidQuizId(){
        Quiz quiz = new Quiz(1,"","", LocalDate.now(),1,1);
        when(quizService.getQuizById(any())).thenReturn(quiz);

        ResponseEntity<String> quizById = quizController.getQuizById("1");

        assertEquals(200,quizById.getStatusCodeValue());
        verify(quizService).getQuizById(any());
    }

    @Test
    public void should_getAllQuizzes(){
        List<Quiz> quizList = new ArrayList<>();
        when(quizService.getAllQuiz()).thenReturn(quizList);

        ResponseEntity<String> allQuizzes = quizController.getAllQuizzes();

        verify(quizService).getAllQuiz();

        assertNotNull(allQuizzes);
    }
//
//    @Test
//    public void should_AddNewQuestion_ThenReturnOkResponseEntityWithHttpStatusAccepted(){
//        when(quizService.addNewQuestion(any(),any(),any(),any(),any(),any())).thenReturn(true);
//
//        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuestion("correctQuestion", "1",
//                "option1", "option2", "option3", "option4", "option5");
//
//        assertEquals(HttpStatus.ACCEPTED,httpStatusResponseEntity.getStatusCode());
//    }

    @Test
    public void should_NotAddNewQuestion_WhenFalseReturnedFromService(){
        when(quizService.addNewQuestion(any(),any(),any(),any(),any(),any(),any())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuestion("incorrectQuestion",
                "1", "option1", "option2", "option3", "option4", "option5");

        verify(quizService).addNewQuestion(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString());
        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
    }

}