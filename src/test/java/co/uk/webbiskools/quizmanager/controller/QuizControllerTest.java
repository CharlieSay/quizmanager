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
import java.util.Optional;

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
        Optional<Quiz> optionalQuiz = Optional.of(quiz);
        when(quizService.getQuizById(any())).thenReturn(optionalQuiz);

        ResponseEntity<String> quizById = quizController.getQuizById("1");

        assertEquals(202,quizById.getStatusCodeValue());
        assertEquals(String.class,quizById.getBody().getClass());
        verify(quizService).getQuizById(any());
    }

    @Test
    public void should_ReturnEmptyOptional_WhenIdIsIncorrect(){
        Optional<Quiz> optionalQuiz = Optional.empty();
        when(quizService.getQuizById(any())).thenReturn(optionalQuiz);

        ResponseEntity<String> quizById = quizController.getQuizById("1");

        assertEquals(400,quizById.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,quizById.getStatusCode());
        verify(quizService).getQuizById(any());
    }

    @Test
    public void should_getAllQuizzes_WhenServiceReturnsAOptionalWithList(){
        List<Quiz> quizList = new ArrayList<>();
        Optional<List<Quiz>> optionalQuizList = Optional.of(quizList);
        when(quizService.getAllQuiz()).thenReturn(optionalQuizList);

        ResponseEntity<String> allQuizzes = quizController.getAllQuizzes();

        verify(quizService).getAllQuiz();

        assertEquals(202,allQuizzes.getStatusCodeValue());
        assertEquals(HttpStatus.ACCEPTED,allQuizzes.getStatusCode());
    }

    @Test
    public void shouldNot_getAllQuizzes_WhenServiceReturnsAOptionalWithoutList(){
        Optional<List<Quiz>> optionalQuizList = Optional.empty();
        when(quizService.getAllQuiz()).thenReturn(optionalQuizList);

        ResponseEntity<String> allQuizzes = quizController.getAllQuizzes();

        verify(quizService).getAllQuiz();
        assertEquals(400,allQuizzes.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,allQuizzes.getStatusCode());
    }

    @Test
    public void should_AddNewQuestion_ThenReturnOkResponseEntityWithHttpStatusAccepted(){
        String correctQuestion = "correctQuestion";

        when(quizService.addNewQuestion(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(true);

       quizController.addNewQuestion(correctQuestion, "1",
                "option1", "option2", "option3", "option4", "option5","1");

        verify(quizService).addNewQuestion(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString());
    }

    @Test
    public void should_NotAddNewQuestion_WhenFalseReturnedFromService(){
        String incorrectQuestion = "incorrectQuestion";
        when(quizService.addNewQuestion(eq(incorrectQuestion),any(),any(),any(),any(),any(),any(),any())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuestion(incorrectQuestion,
                "1", "option1", "option2", "option3", "option4", "option5","1");

        verify(quizService).addNewQuestion(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString());
        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
    }

    @Test
    public void should_AddNewQuiz_WhenTrueReturnedFromService(){
        when(quizService.addNewQuiz(any(),any(),any())).thenReturn(true);

        ResponseEntity<HttpStatus> quizById = quizController.addNewQuiz("Title","Description","32");

        assertEquals(202,quizById.getStatusCodeValue());
        verify(quizService).addNewQuiz(any(),any(),any());
    }

    @Test
    public void should_NotAddNewQuiz_WhenFalseReturnedFromService(){
        when(quizService.addNewQuiz(any(),any(),any())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addNewQuiz("Incorrect","1234","ABC");

        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
        verify(quizService).addNewQuiz(any(),any(),any());

    }

    @Test
    public void should_DeleteQuiz_WhenQuizIdValid(){
        when(quizService.deleteFrom(anyString(),anyString())).thenReturn(true);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.deleteQuiz("32");

        assertEquals(202,httpStatusResponseEntity.getStatusCodeValue());
        verify(quizService).deleteFrom(eq("QuizBank"),anyString());
    }

    @Test
    public void should_NotDeleteQuiz_WhenFalseReturnedFromService(){
        when(quizService.deleteFrom(anyString(),anyString())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.deleteQuiz("32");

        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
        verify(quizService).deleteFrom(eq("QuizBank"),anyString());

    }

    @Test
    public void should_DeleteQuestion_WhenQuestionIdValid(){
        when(quizService.deleteFrom(anyString(),anyString())).thenReturn(true);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.deleteQuestion("123");

        assertEquals(202,httpStatusResponseEntity.getStatusCodeValue());
        verify(quizService).deleteFrom(eq("QuestionBank"),anyString());
    }

    @Test
    public void should_NotDeleteQuestion_WhenFalseReturnedFromService(){
        when(quizService.deleteFrom(anyString(),anyString())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.deleteQuestion("123");

        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
        verify(quizService).deleteFrom(eq("QuestionBank"),anyString());
    }

    @Test
    public void should_DeleteOption_WhenOptionIdValid(){
        when(quizService.deleteFrom(anyString(),anyString())).thenReturn(true);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.deleteOption("1001");

        assertEquals(202,httpStatusResponseEntity.getStatusCodeValue());
        verify(quizService).deleteFrom(eq("OptionBank"),anyString());
    }

    @Test
    public void should_NotDeleteOption_WhenFalseReturnedFromService(){
        when(quizService.deleteFrom(anyString(),anyString())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.deleteOption("1001");

        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
        verify(quizService).deleteFrom(eq("OptionBank"),anyString());
    }

    @Test
    public void should_AddOption_WhenOptionIdValid(){
        when(quizService.addNewOption(anyString(),anyString())).thenReturn(true);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addOption("123","OptionText");

        assertEquals(202,httpStatusResponseEntity.getStatusCodeValue());
        verify(quizService).addNewOption(eq("123"),eq("OptionText"));
    }

    @Test
    public void should_NotAddOption_WhenFalseReturnedFromService(){
        when(quizService.addNewOption(anyString(),anyString())).thenReturn(false);

        ResponseEntity<HttpStatus> httpStatusResponseEntity = quizController.addOption("abc_incorrect","123");

        assertEquals(400,httpStatusResponseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST,httpStatusResponseEntity.getStatusCode());
        verify(quizService).addNewOption(eq("abc_incorrect"),eq("123"));
    }

    @Test
    public void should_ReturnQuizPage(){
        String page = quizController.getPage();

        assertEquals("quizPage.html",page);
    }

}