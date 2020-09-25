package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.UserQuizDao;
import com.maktab.onlineQuizManagement.model.entity.UserQuizzes;
import org.springframework.stereotype.Service;

@Service
public class UserQuizService {
    private final UserQuizDao userQuizDao;

    public UserQuizService(UserQuizDao userQuizDao) {
        this.userQuizDao = userQuizDao;
    }

    public UserQuizzes findByPrimaryKey(int userId, int quizId) {
        return userQuizDao.findByPrimaryKey(userId, quizId);
    }

    public UserQuizzes save(UserQuizzes userQuizzes) {
        return userQuizDao.save(userQuizzes);
    }
}
