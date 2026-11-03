package com.infy.Quiz_Service.Feign;


import com.infy.Quiz_Service.Entity.QuestionWrapper;
import com.infy.Quiz_Service.Entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(@RequestParam String category, @RequestParam int NumQ);

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromQuizId(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getresult")
    public ResponseEntity<Integer> getResult(@RequestBody List<Response> response);
}
