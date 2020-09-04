package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface QuizDao extends PagingAndSortingRepository<Quiz, Integer> {

    Quiz save(Quiz quiz);

    List<Quiz> findAll();

    Optional<Quiz> findById(int id);

    void deleteById(int id);

}
