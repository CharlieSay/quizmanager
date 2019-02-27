package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Quiz;
import co.uk.webbiskools.quizmanager.service.QuizService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity <String> getQuizInfo(@RequestParam String quizId) {
        Quiz quizById = quizService.getQuizById(Integer.parseInt(quizId));
        return ResponseEntity.ok(gson.toJson(quizById));
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity <String> getAllQuizzes() {
        return ResponseEntity.ok(gson.toJson(quizService.getAllQuiz()));
    }

    @PostMapping(value = "/question/add")
    public ResponseEntity <HttpStatus> addNewQuestion(
            @RequestParam String question, @RequestParam String option1, @RequestParam String option2,
            @RequestParam String option3, @RequestParam(required = false) String option4,
            @RequestParam(required = false) String option5, @RequestParam String quizId) {
        if (quizService.addNewQuestion(question, quizId, option1, option2, option3, option4, option5)) {
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/add")
    public ResponseEntity <HttpStatus> addNewQuiz(
            @RequestParam String title, @RequestParam String description, @RequestParam String userId) {
        if (quizService.addNewQuiz(title, description,userId)) {
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteQuiz(@RequestParam String quizId){
        if (quizService.deleteFrom("QuizBank",quizId)){
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/question/delete")
    public ResponseEntity<HttpStatus> deleteQuestion(@RequestParam String questionId){
        if (quizService.deleteFrom("QuestionBank",questionId)){
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/question/option/delete")
    public ResponseEntity<HttpStatus> deleteOption(@RequestParam String optionId){
        if (quizService.deleteFrom("OptionBank",optionId)){
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/question/option/add")
    public ResponseEntity<HttpStatus> addOption(@RequestParam String questionId, @RequestParam String optionText){
        if (quizService.addNewOption(questionId,optionText)){
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public String getPage() {
        return "quizPage.html";
    }

}