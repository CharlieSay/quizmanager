package co.uk.webbiskools.quizmanager.dao;

import java.util.List;

public class Question {

    private Integer id;
    private String questionText;
    private Integer quizId;

    private List<Option> optionList;

    public Question(Integer id, String questionText, Integer quizId, List<Option> optionList) {
        this.id = id;
        this.questionText = questionText;
        this.quizId = quizId;
        this.optionList = optionList;
    }

    public Integer getId() {
        return id;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Integer getQuizId() {
        return quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", quizId=" + quizId +
                '}';
    }
}
