package com.infy.Question_Service.Service;

import com.infy.Question_Service.Entity.Question;
import com.infy.Question_Service.Entity.QuestionWrapper;
import com.infy.Question_Service.Entity.Response;
import com.infy.Question_Service.Repository.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestion() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(int id, String difficultylevel){
        try {
            Question originalQuestion = questionDao.findById(id).get();
            originalQuestion.setDifficultylevel(difficultylevel);
            questionDao.save(originalQuestion);
            return new ResponseEntity<>("updated",HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try{
            questionDao.deleteById(id);
            return new ResponseEntity<>("deleted",HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("deleted",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String category, int numQ) {
        List<Integer> questionList = questionDao.getRandomQuestion(category, numQ);
        return new ResponseEntity<>(questionList,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers=new ArrayList<>();
        List<Question> questions=new ArrayList<>();

        for(Integer q:questionIds)
        {
            Question question=questionDao.findById(q).get();
            questions.add(question);
        }
//System.out.println(questions);

        for(Question que:questions)
        {
            QuestionWrapper wrapper=new QuestionWrapper();
            wrapper.setId(que.getId());
            wrapper.setDifficultylevel(que.getDifficultylevel());
            wrapper.setQuestionTitle(que.getQuestionTitle());
            wrapper.setOption1(que.getOption1());
            wrapper.setOption2(que.getOption2());
            wrapper.setOption3(que.getOption3());
            wrapper.setOption4(que.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(List<Response> response) {

        int right=0;
        for(Response r: response)
        {
            Question question=questionDao.findById(r.getId()).get();
            if(r.getResponse() != null&& r.getResponse().equals(question.getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
