package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.QuizQuestionsDao;
import com.maktab.onlineQuizManagement.model.entity.Question;
import com.maktab.onlineQuizManagement.model.entity.Quiz;
import com.maktab.onlineQuizManagement.model.entity.QuizQuestionId;
import com.maktab.onlineQuizManagement.model.entity.QuizQuestions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuizQuestionsService {
    private final QuizQuestionsDao quizQuestionsDao;

    public QuizQuestionsService(QuizQuestionsDao quizQuestionsDao) {
        this.quizQuestionsDao = quizQuestionsDao;
    }

    public QuizQuestions save(QuizQuestions quizQuestions) {
        return quizQuestionsDao.save(quizQuestions);
    }

    public QuizQuestions get(QuizQuestionId primaryKey) {
        return quizQuestionsDao.findByPrimaryKey(primaryKey);
    }

    public Quiz addNew(Question question, Quiz quiz) {
        QuizQuestions quizQuestions = new QuizQuestions();
        quizQuestions.getPrimaryKey().setQuestion(question);
        quizQuestions.getPrimaryKey().setQuiz(quiz);
        quizQuestions.setScore(0);
        quizQuestionsDao.save(quizQuestions);
        return quiz;
    }

}
