package co.uk.webbiskools.quizmanager.controller;

import co.uk.webbiskools.quizmanager.dao.Quiz;
import co.uk.webbiskools.quizmanager.service.QuizService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping(value = "/addQuestion")
    public ResponseEntity <HttpStatus> addNewQuestion(
            @RequestParam String question, @RequestParam String option1, @RequestParam String option2,
            @RequestParam String option3, @RequestParam(required = false) String option4,
            @RequestParam(required = false) String option5, @RequestParam String quizId) {
        if (quizService.addNewQuestion(question, quizId, option1, option2, option3, option4, option5)) {
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public String getPage() {
        return "quizPage.html";
    }

}