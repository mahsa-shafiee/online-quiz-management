package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.TeacherDao;
import com.maktab.onlineQuizManagement.model.entity.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherDao teacherDao;

    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher findById(Integer id) {
        return teacherDao.findById(id).orElse(null);
    }

    public void save(Teacher teacher) {
        teacherDao.save(teacher);
    }
}
