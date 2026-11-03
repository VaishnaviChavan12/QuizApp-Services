package com.infy.Question_Service.Repository;

import com.infy.Question_Service.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "select q.id from Question as q where category=:category order by RAND() Limit :numQ", nativeQuery = true )
    List<Integer> getRandomQuestion(String category, int numQ);

}
