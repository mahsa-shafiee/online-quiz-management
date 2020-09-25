package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.StudentDao;
import com.maktab.onlineQuizManagement.model.entity.Student;
import com.maktab.onlineQuizManagement.model.entity.UserQuizzes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentDao studentDao;
    private final UserQuizService userQuizService;

    public StudentService(StudentDao studentDao, UserQuizService userQuizService) {
        this.studentDao = studentDao;
        this.userQuizService = userQuizService;
    }

    public Student findById(Integer id) {
        return studentDao.findById(id).orElse(null);
    }

    public void save(Student student) {
        studentDao.save(student);
    }

    public UserQuizzes getAnswer(int quizId, int userId) {
        Student student = findById(userId);
        for (UserQuizzes stdQuiz : student.getParticipatedQuizzes()) {
            if (stdQuiz.getPrimaryKey().getUser().getId() == userId
                    && stdQuiz.getPrimaryKey().getQuiz().getId() == quizId) {
                return stdQuiz;
            }
        }
        return null;
    }

    public UserQuizzes saveScore(int quizId, int userId, double score) {
        Student student = findById(userId);
        for (UserQuizzes stdQuiz : student.getParticipatedQuizzes()) {
            if (stdQuiz.getPrimaryKey().getUser().getId() == userId
                    && stdQuiz.getPrimaryKey().getQuiz().getId() == quizId) {
                stdQuiz.setTotalScore(stdQuiz.getTotalScore() + score);
                userQuizService.save(stdQuiz);
                return stdQuiz;
            }
        }
        return null;
    }
}
