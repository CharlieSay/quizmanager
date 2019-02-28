package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Quiz;
import co.uk.webbiskools.quizmanager.service.QuizService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private QuizService quizService;

    private Gson gson;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
        gson = new Gson();
    }

    @GetMapping(value = "/get/byId", params = "quizId", produces = "application/json")
    public ResponseEntity <String> getQuizById(String quizId) {
            Optional<Quiz> quizById = quizService.getQuizById(Integer.valueOf(quizId));
            if (quizById.isPresent()){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(quizById));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity <String> getAllQuizzes() {
        Optional<List<Quiz>> quizList = quizService.getAllQuiz();
        if (quizList.isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(quizList.get()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/question/add")
    public ResponseEntity <HttpStatus> addNewQuestion(
            @RequestParam String question,@RequestParam String correctOption, @RequestParam String option1, @RequestParam String option2,
            @RequestParam String option3, @RequestParam(required = false) String option4,
            @RequestParam(required = false) String option5, @RequestParam String quizId) {
        if (quizService.addNewQuestion(question, quizId, correctOption, option1, option2, option3, option4, option5)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(value = "/add")
    public ResponseEntity <HttpStatus> addNewQuiz(
            @RequestParam String title, @RequestParam String description, @RequestParam String userId) {
        if (quizService.addNewQuiz(title, description,userId)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteQuiz(@RequestParam String quizId){
        if (quizService.deleteFrom("QuizBank",quizId)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/question/delete")
    public ResponseEntity<HttpStatus> deleteQuestion(@RequestParam String questionId){
        if (quizService.deleteFrom("QuestionBank",questionId)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/question/option/delete")
    public ResponseEntity<HttpStatus> deleteOption(@RequestParam String optionId){
        if (quizService.deleteFrom("OptionBank",optionId)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(value = "/question/option/add")
    public ResponseEntity<HttpStatus> addOption(@RequestParam String questionId, @RequestParam String optionText){
        if (quizService.addNewOption(questionId,optionText)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public String getPage() {
        return "quizPage.html";
    }

}