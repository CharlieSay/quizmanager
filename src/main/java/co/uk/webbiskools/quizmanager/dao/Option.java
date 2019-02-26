package co.uk.webbiskools.quizmanager.dao;

public class Option {

    private Integer id;
    private String optionText;
    private Integer questionId;
    private Boolean correct;

    public Option(Integer id, String optionText, Integer questionId, Boolean correct) {
        this.id = id;
        this.optionText = optionText;
        this.questionId = questionId;
        this.correct = correct;
    }

    public Integer getId() {
        return id;
    }

    public String getOptionText() {
        return optionText;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public Boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", optionText='" + optionText + '\'' +
                ", questionId=" + questionId +
                ", correct=" + correct +
                '}';
    }
}