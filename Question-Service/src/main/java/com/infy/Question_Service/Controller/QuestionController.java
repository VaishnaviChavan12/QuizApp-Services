package com.infy.Question_Service.Controller;

import com.infy.Question_Service.Entity.Question;
import com.infy.Question_Service.Entity.QuestionWrapper;
import com.infy.Question_Service.Entity.Response;
import com.infy.Question_Service.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> allQuestion()
    {
       return questionService.getAllQuestion();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
    {
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable int id, @RequestParam String difficultylevel)
    {
        return questionService.updateQuestion(id, difficultylevel);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id)
    {

        return questionService.deleteQuestion(id);
    }

    //generate a quiz
    //return the question for quiz
    //provide the result
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(@RequestParam String category, @RequestParam int NumQ)
    {
        return questionService.getQuestionForQuiz(category,NumQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromQuizId(@RequestBody List<Integer> questionIds)
    {
        return questionService.getQuizQuestions(questionIds);
    }

    @PostMapping("getresult")
    public ResponseEntity<Integer> getResult(@RequestBody List<Response> response)
    {
        return questionService.calculateResult(response);
    }

}