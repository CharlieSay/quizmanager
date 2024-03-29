package co.uk.webbiskools.quizmanager.service;

import co.uk.webbiskools.quizmanager.dao.Option;
import co.uk.webbiskools.quizmanager.dao.Question;
import co.uk.webbiskools.quizmanager.dao.Quiz;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {

    Connection connection;

    @Autowired
    public QuizService(Connection connection) {
        this.connection = connection;
    }

    public Boolean deleteFrom(String typeBank, String id) {
        String SQLStatement = "DELETE FROM " + typeBank + " WHERE id = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
            preparedStatement.setString(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean addNewOption(String questionId, String optionText){
        String SQLStatement = "INSERT INTO OptionBank(option_text, question_id, correct_answer) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
            preparedStatement.setString(1,optionText);
            preparedStatement.setInt(2,Integer.parseInt(questionId));
            preparedStatement.setBoolean(3,false);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean addNewQuiz(String title, String description, String creatorId){
        String SQLStatement = "INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES (?,?,CURRENT_DATE,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,description);
            preparedStatement.setInt(3,Integer.parseInt(creatorId));
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean addNewQuestion(String question, String quizId, String correctOption, String... options){
        String SQLStatement = "INSERT INTO QuestionBank(question_text, quiz_id) VALUES (?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,question);
            preparedStatement.setString(2,quizId);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Long resultingId = null;
            if (generatedKeys.next()){
                resultingId = generatedKeys.getLong(1);
            }
            for (int i=0; i<options.length;i++){
                if (options[i] == options[Integer.parseInt(correctOption)-1]){
                    addNewOptionFromQuestion(options[i],resultingId,true);
                }else{
                    addNewOptionFromQuestion(options[i],resultingId,false);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Boolean addNewOptionFromQuestion(String option, long question_id_long, Boolean correct_answer){
        Integer question_id = Integer.parseInt(Long.toString(question_id_long));
        String SQLStatement  ="INSERT INTO OptionBank(option_text, question_id, correct_answer) VALUES (?,?,?);" ;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
            preparedStatement.setString(1,option);
            preparedStatement.setInt(2,question_id);
            preparedStatement.setBoolean(3,correct_answer);
            return preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Optional<List<Quiz>> getAllQuiz(){
        List<Quiz> quizList = new ArrayList<>();

        String SQLStatement = "select * from QuizBank";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                quizList.add(new Quiz(resultSet.getInt(1),
                        resultSet.getString(2)));
            }
            return Optional.of(quizList);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Quiz> getQuizById(Integer quizId){
        Quiz quiz = null;

        String SQLStatment = "select * from `QuizBank` WHERE id=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStatment);
            preparedStatement.setInt(1,quizId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                quiz = new Quiz(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getInt(5),
                        getAmountOfQuestions(quizId)
                );
                quiz.setQuestionList(getQuestionList(quiz.getId()));
            }
            return Optional.of(quiz);
        }catch (SQLException e){
            return Optional.empty();
        }
    }


    public Integer getAmountOfQuestions(Integer quizId){
        String sql = "SELECT count(quiz_id) FROM QuestionBank WHERE quiz_id = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,quizId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        }catch(SQLException e){
            return 0;
        }
    }

    public List<Question> getQuestionList(Integer quizId){
        String statement = "SELECT * FROM `QuestionBank` WHERE quiz_id=?";
        List<Question> questionList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1,quizId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                questionList.add(new Question(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        getOptionList(resultSet.getInt(1))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    public List<Option> getOptionList(Integer questionId){
        String statement = "select * FROM `OptionBank` WHERE question_id=?";
        List<Option> optionList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1,questionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                optionList.add(new Option(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getBoolean(4)
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionList;
    }
}
