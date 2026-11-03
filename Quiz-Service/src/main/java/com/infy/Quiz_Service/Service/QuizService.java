package com.infy.Quiz_Service.Service;


import com.infy.Quiz_Service.Entity.QuestionWrapper;
import com.infy.Quiz_Service.Entity.Quiz;
import com.infy.Quiz_Service.Entity.Response;
import com.infy.Quiz_Service.Repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.infy.Quiz_Service.Feign.QuizInterface;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, String title, int numQ) {
        List<Integer> questionList =quizInterface.getQuestionForQuiz(category,numQ).getBody();

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);
        quizDao.save(quiz);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Integer> questionsFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser=quizInterface.getQuestionsFromQuizId(questionsFromDB).getBody();

        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> response) {
        Integer result=quizInterface.getResult(response).getBody();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
