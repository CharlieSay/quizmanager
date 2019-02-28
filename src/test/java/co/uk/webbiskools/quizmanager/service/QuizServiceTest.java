package co.uk.webbiskools.quizmanager.service;

import co.uk.webbiskools.quizmanager.dao.Option;
import co.uk.webbiskools.quizmanager.dao.Question;
import co.uk.webbiskools.quizmanager.dao.Quiz;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class QuizServiceTest{

    QuizService quizService;

    Connection connection;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Before
    public void setUp(){
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        quizService = new QuizService(connection);
    }

    @Test
    public void should_DeleteFrom_WhenIdAndTableExists() throws SQLException {
        String SQLStatement = "DELETE FROM RandomTable WHERE id = (?)";
        String typeBank = "RandomTable";
        String id = "1";
        when(connection.prepareStatement(SQLStatement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,"1");
        when(preparedStatement.execute()).thenReturn(true);

        quizService.deleteFrom(typeBank,id);

        verify(connection).prepareStatement(eq(SQLStatement));
        verify(preparedStatement).setString(1,id);
        verify(preparedStatement).execute();
    }

    @Test
    public void should_NotDeleteFrom_WhenSQLErrorOccurs() throws SQLException {
        String SQLStatement = "DELETE FROM SQLError WHERE id = (?)";

        when(connection.prepareStatement(SQLStatement)).thenThrow(SQLException.class);

        Boolean aBoolean = quizService.deleteFrom("SQLError", "Blah");

        assertFalse(aBoolean);
    }

    @Test
    public void should_AddNewOption_WhenQuestionIdAndTextAreValid() throws SQLException {
        String SQLStatement = "INSERT INTO OptionBank(option_text, question_id, correct_answer) VALUES (?,?,?)";
        String questionId = "1";
        String optionText = "optionText";
        when(connection.prepareStatement(SQLStatement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,optionText);
        doNothing().when(preparedStatement).setInt(2,Integer.parseInt(questionId));
        doNothing().when(preparedStatement).setBoolean(3,false);
        when(preparedStatement.execute()).thenReturn(true);

        quizService.addNewOption(questionId,optionText);

        verify(connection).prepareStatement(eq(SQLStatement));
        verify(preparedStatement).setString(1,optionText);
        verify(preparedStatement).setInt(2,Integer.parseInt(questionId));
        verify(preparedStatement).setBoolean(3,false);
        verify(preparedStatement).execute();
    }

    @Test
    public void shouldNot_AddOption_WhenSQLErrorOccurs() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        Boolean aBoolean = quizService.addNewOption("SQLError", "SQLError");

        assertFalse(aBoolean);
    }

    @Test
    public void should_AddQuiz_WhenTitleDescriptionCreatorIdValid() throws SQLException {
        String SQLStatement = "INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES (?,?,CURRENT_DATE,?)";
        String title = "title";
        String description = "description";
        String creatorId = "1";
        when(connection.prepareStatement(SQLStatement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,title);
        doNothing().when(preparedStatement).setString(2,description);
        doNothing().when(preparedStatement).setInt(3,Integer.parseInt(creatorId));
        when(preparedStatement.execute()).thenReturn(true);

        Boolean booleanResult = quizService.addNewQuiz(title, description, creatorId);

        verify(connection).prepareStatement(eq(SQLStatement));
        verify(preparedStatement).setString(1,title);
        verify(preparedStatement).setString(2,description);
        verify(preparedStatement).setInt(3,Integer.parseInt(creatorId));
        verify(preparedStatement).execute();
        assertTrue(booleanResult);
    }

    @Test
    public void shouldNot_AddQuiz_WhenSQLErrorOccurs() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        Boolean aBoolean = quizService.addNewQuiz("SQLError", "SQLError","1");

        assertFalse(aBoolean);
    }

//    @Test
//    public void should_AddNewQuestion_WhenQuestionQuizIdCorrectOptionOptionsAreValid() throws Exception {
//        String SQLStatement = "INSERT INTO QuestionBank(question_text, quiz_id) VALUES (?,?)";
//        String questionTitle = "QuestionTitle";
//        String quizId = "1";
//        String[] options = {"options1","options2"};
//        ResultSet resultSet = mock(ResultSet.class);
//
//        when(connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
//        doNothing().when(preparedStatement).setString(1,questionTitle);
//        doNothing().when(preparedStatement).setString(2,quizId);
//        when(preparedStatement.execute()).thenReturn(true);
//        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getLong(1)).thenReturn(1L);
//
//
//        Boolean result = Whitebox.invokeMethod(quizService, "addNewOptionFromQuestion", "Arg",1l,true);
//        quizService.addNewQuestion(questionTitle,quizId,"1",options);
//    }

    @Test
    public void should_NotAddQuestion_WhenSQLError() throws SQLException {
        String SQLStatement = "INSERT INTO QuestionBank(question_text, quiz_id) VALUES (?,?)";
        when(connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS)).thenThrow(SQLException.class);

        Boolean aBoolean = quizService.addNewQuestion("SQLError", "SQLError","noErorr","corectError");

        assertFalse(aBoolean);
    }

    @Test
    public void should_GetAllQuizzes_WhenQuizzesExist() throws SQLException {
     when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("QuizTitle");

        Optional<List<Quiz>> allQuiz = quizService.getAllQuiz();

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet,atLeastOnce()).next();
        verify(resultSet,atMost(1)).getInt(1);
        verify(resultSet,atMost(2)).getString(anyString());
        assertTrue(allQuiz.isPresent());
    }

    @Test
    public void should_ReturnEmptyOptionalWhenSQLError() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        Optional<List<Quiz>> allQuiz = quizService.getAllQuiz();

        assertFalse(allQuiz.isPresent());
    }

//    @Test
//    public void should_ReturnLoadedOptional_WhenGettingQuizByValidId() throws Exception {
//        String SQLStatment = "select * from `QuizBank` WHERE id=?";
//        Integer quizId = 1;
//        List<Question> questionList = new ArrayList();
//        questionList.add(new Question(1,"question",1,null));
//        when(connection.prepareStatement(SQLStatment)).thenReturn(preparedStatement);
//        doNothing().when(preparedStatement).setInt(1,quizId);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//        doReturn(true).doReturn(false).when(resultSet).next();
//        when(resultSet.getInt(1)).thenReturn(1);
//        when(resultSet.getInt(1)).thenReturn(1);PRIMARY
//        when(resultSet.getString(2)).thenReturn("QuizTitle");
//        when(resultSet.getString(3)).thenReturn("Description");
//        when(resultSet.getDate(4)).thenReturn(null);
//        when(resultSet.getInt(5)).thenReturn(4);
//        doNothing().when(preparedStatement).setInt(1,quizId);
//        when(quizService.getAmountOfQuestions(quizId)).thenReturn(1);
//        when(quizService.getQuestionList(quizId)).thenReturn(questionList);
//
//        Optional<Quiz> quizById = quizService.getQuizById(quizId);
//
//        assertTrue(quizById.isPresent());
//    }

    @Test
    public void should_GetOptionList_WhenQuestionIdIsValid() throws SQLException {
        String statement = "select * FROM `OptionBank` WHERE question_id=?";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,any());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("OptionTitle");
        when(resultSet.getInt(3)).thenReturn(11);
        when(resultSet.getBoolean(4)).thenReturn(true);

        List<Option> optionList = quizService.getOptionList(1);

        assertNotNull(optionList);
    }

}