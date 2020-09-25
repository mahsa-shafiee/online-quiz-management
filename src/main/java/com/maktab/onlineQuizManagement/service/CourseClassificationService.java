package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.CourseClassificationDao;
import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.CourseClassification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CourseClassificationService {
    private final CourseClassificationDao courseClassificationDao;

    public CourseClassificationService(CourseClassificationDao courseClassificationDao) {
        this.courseClassificationDao = courseClassificationDao;
    }

    public void saveCourse(CourseClassification courseClassification) {
        courseClassificationDao.save(courseClassification);
    }

    public CourseClassification getByName(String name) {
        return courseClassificationDao.findByName(name).orElse(null);
    }
}
