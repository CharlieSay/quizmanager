package co.uk.webbiskools.quizmanager.dao;

import java.time.LocalDate;
import java.util.List;

public class Quiz {

    private Integer id;
    private String title;
    private String description;
    private String creationDate;
    private Integer creatorId;
    private Integer amountOfQuestions;
    private List<Question> questionList;

    public Quiz(Integer id, String title, String description, LocalDate creationDate, Integer creatorId, Integer amountOfQuestions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate.toString();
        this.creatorId = creatorId;
        this.amountOfQuestions = amountOfQuestions;
    }

    public Quiz(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public Integer getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    @Override
    public String toString() {
        return "QuizDataRetrieval{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", creatorId=" + creatorId +
                ", amountOfQuestions=" + amountOfQuestions +
                ", questionList=" + questionList +
                '}';
    }
}
