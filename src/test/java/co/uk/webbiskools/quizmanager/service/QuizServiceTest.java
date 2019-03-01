package co.uk.webbiskools.quizmanager.service;

import co.uk.webbiskools.quizmanager.dao.Option;
import co.uk.webbiskools.quizmanager.dao.Question;
import co.uk.webbiskools.quizmanager.dao.Quiz;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
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

    @Test
    public void should_NotAddQuestion_WhenSQLError() throws SQLException {
        String SQLStatement = "INSERT INTO QuestionBank(question_text, quiz_id) VALUES (?,?)";
        when(connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,SQLStatement);
        doNothing().when(preparedStatement).setString(2,"SQLError");
        when(preparedStatement.execute()).thenThrow(SQLException.class);

        Boolean aBoolean = quizService.addNewQuestion("SQLError", "SQLError","noErorr","corectError");

        assertFalse(aBoolean);
    }

    @Test
    public void should_AddQuestion_WhenNoError() throws SQLException {
        String SQLStatement = "INSERT INTO QuestionBank(question_text, quiz_id) VALUES (?,?)";
        String question = "My Question?";
        String quizId = "1";
        String correctOption = "1";
        String[] optionsList = {"1","2"};
        Integer long1l = Integer.parseInt(Long.toString(1l));
        String addNewOptionQuestionSQL  ="INSERT INTO OptionBank(option_text, question_id, correct_answer) VALUES (?,?,?);" ;
        when(connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,question);
        doNothing().when(preparedStatement).setString(2,quizId);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        doReturn(true).when(resultSet).next();
        when(resultSet.getLong(1)).thenReturn(1L);
        when(connection.prepareStatement(addNewOptionQuestionSQL)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,"1");
        doNothing().when(preparedStatement).setInt(2,long1l);
        doNothing().when(preparedStatement).setBoolean(3,true);
        when(preparedStatement.execute()).thenReturn(true);

        Boolean aBoolean = quizService.addNewQuestion(question, quizId, correctOption, optionsList);

        assertTrue(aBoolean);
    }


    @Test
    public void shouldReturnFalse_AddQuestion_WhenNoError() throws SQLException {
        String SQLStatement = "INSERT INTO QuestionBank(question_text, quiz_id) VALUES (?,?)";
        String question = "My Question?";
        String quizId = "1";
        String correctOption = "1";
        String[] optionsList = {"1","2"};
        Integer long1l = Integer.parseInt(Long.toString(1l));
        String addNewOptionQuestionSQL  ="INSERT INTO OptionBank(option_text, question_id, correct_answer) VALUES (?,?,?);" ;
        when(connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,question);
        doNothing().when(preparedStatement).setString(2,quizId);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        doReturn(true).when(resultSet).next();
        when(resultSet.getLong(1)).thenReturn(1L);
        when(connection.prepareStatement(addNewOptionQuestionSQL)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1,"1");
        doNothing().when(preparedStatement).setInt(2,long1l);
        doNothing().when(preparedStatement).setBoolean(3,true);
        when(preparedStatement.execute()).thenThrow(SQLException.class);

        Boolean aBoolean = quizService.addNewQuestion(question, quizId, correctOption, optionsList);

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

    @Test
    public void should_GetAmountOfQuestions_WhenQuestionsExist() throws SQLException {
        String sql = "SELECT count(quiz_id) FROM QuestionBank WHERE quiz_id = ?";
        Integer quizId = 1;
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,quizId);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);

        Integer amountOfQuestions = quizService.getAmountOfQuestions(1);

       assertEquals(Optional.of(1).get(),amountOfQuestions);
    }

    @Test
    public void should_Return0AmountOfQuestions_WhenNoQuestionsExist() throws SQLException {
        String sql = "SELECT count(quiz_id) FROM QuestionBank WHERE quiz_id = ?";
        Integer quizId = 0;
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,quizId);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(0);

        Integer amountOfQuestions = quizService.getAmountOfQuestions(1);

        assertEquals(Optional.of(0).get(),amountOfQuestions);
    }

    @Test
    public void should_Return0AmountOfQuestions_WhenSQLError() throws SQLException {
        String sql = "SELECT count(quiz_id) FROM QuestionBank WHERE quiz_id = ?";

        when(connection.prepareStatement(sql)).thenThrow(SQLException.class);

        Integer amountOfQuestions = quizService.getAmountOfQuestions(1);

        assertEquals(Optional.of(0).get(),amountOfQuestions);
    }


    @Test
    public void should_GetOptionList_WhenValid() throws SQLException {
        String statement = "select * FROM `OptionBank` WHERE question_id=?";

        when(connection.prepareStatement(statement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("String");
        when(resultSet.getInt(3)).thenReturn(1);
        when(resultSet.getBoolean(4)).thenReturn(true);

        List<Option> optionList = quizService.getOptionList(1);

        assertThat(optionList.size(),is(1));
    }

    @Test
    public void shouldNotThenThrowSQLError_GetOptionList_WhenValid() throws SQLException {
        String statement = "select * FROM `OptionBank` WHERE question_id=?";

        when(connection.prepareStatement(statement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,23123121);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

        List<Option> optionList = quizService.getOptionList(23123121);

        assertThat(optionList.size(),is(0));
    }

    @Test
    public void shouldGetQuestionList_WhenValidQuizId() throws SQLException {
        String statement = "SELECT * FROM `QuestionBank` WHERE quiz_id=?";
        Integer quizId = 10;

        when(connection.prepareStatement(statement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,quizId);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Im A String    ");
        when(resultSet.getInt(3)).thenReturn(3);

        String secondStatement = "select * FROM `OptionBank` WHERE question_id=?";

        when(connection.prepareStatement(secondStatement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("String");
        when(resultSet.getInt(3)).thenReturn(1);
        when(resultSet.getBoolean(4)).thenReturn(true);

        List<Question> questionList = quizService.getQuestionList(1);

        assertThat(questionList.size(),is(1));
    }

    @Test
    public void shouldNotGetQuestionList_WhenInvalidQuizId() throws SQLException {
        String statement = "SELECT * FROM `QuestionBank` WHERE quiz_id=?";
        when(connection.prepareStatement(statement)).thenThrow(SQLException.class);

        List<Question> questionList = quizService.getQuestionList(1);

        assertThat(questionList.size(),is(0));
    }

    @Test
    public void should_ReturnOptionalWithQuiz_WhenQuizIdIsValid() throws SQLException {
        String SQLStatement = "select * from `QuizBank` WHERE id=?";
        Integer quizId = 1;
        when(connection.prepareStatement(SQLStatement)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,quizId);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("index2");
        when(resultSet.getString(3)).thenReturn("index3");
        when(resultSet.getDate(4)).thenReturn(Date.valueOf("1996-08-24"));
        when(resultSet.getInt(5)).thenReturn(5);

        String firstSQL = "SELECT count(quiz_id) FROM QuestionBank WHERE quiz_id = ?";
        when(connection.prepareStatement(firstSQL)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,quizId);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);

        String secondSQL = "SELECT * FROM `QuestionBank` WHERE quiz_id=?";
        when(connection.prepareStatement(secondSQL)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,quizId);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Im A String    ");
        when(resultSet.getInt(3)).thenReturn(3);

        String thirdSQL = "select * FROM `OptionBank` WHERE question_id=?";
        when(connection.prepareStatement(thirdSQL)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1,1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("String");
        when(resultSet.getInt(3)).thenReturn(1);
        when(resultSet.getBoolean(4)).thenReturn(true);

        Optional<Quiz> quizById = quizService.getQuizById(1);

        assertTrue(quizById.isPresent());
    }
}