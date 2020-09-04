package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

@org.springframework.stereotype.Repository
public interface QuestionDao extends PagingAndSortingRepository<Question, Integer> {
    Question findById(int id);

    Question save(Question question);
}
