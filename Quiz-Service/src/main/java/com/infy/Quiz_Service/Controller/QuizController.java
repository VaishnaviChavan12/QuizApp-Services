package com.infy.Quiz_Service.Controller;


import com.infy.Quiz_Service.Entity.QuestionWrapper;
import com.infy.Quiz_Service.Entity.QuizDto;
import com.infy.Quiz_Service.Entity.Response;
import com.infy.Quiz_Service.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto)
    {
        return quizService.createQuiz(quizDto.getCategory(),quizDto.getTitle(), quizDto.getNumQ());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id)
    {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> response)
    {
        return quizService.calculateResult(id,response);
    }
}
