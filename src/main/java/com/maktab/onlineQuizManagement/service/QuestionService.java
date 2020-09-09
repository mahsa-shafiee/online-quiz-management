package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.QuestionDao;
import com.maktab.onlineQuizManagement.model.entity.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final static int PAGE_SIZE = 5;
    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public List<Question> getPage(int pageNumber) {
        if (pageNumber < 1)
            pageNumber = 1;

        PageRequest request = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");
        return questionDao.findAll(request).getContent();
    }

    public Question getQuestion(int id) {
        return questionDao.findById(id);
    }

    public Question save(Question question) {
        return questionDao.save(question);
    }
}
