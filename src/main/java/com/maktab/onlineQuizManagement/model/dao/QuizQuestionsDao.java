package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.QuizQuestionId;
import com.maktab.onlineQuizManagement.model.entity.QuizQuestions;
import org.springframework.data.repository.PagingAndSortingRepository;

@org.springframework.stereotype.Repository
public interface QuizQuestionsDao extends PagingAndSortingRepository<QuizQuestions, QuizQuestionId> {

    QuizQuestions save(QuizQuestions QuizQuestions);

    QuizQuestions findByPrimaryKey(QuizQuestionId primaryKey);

}
