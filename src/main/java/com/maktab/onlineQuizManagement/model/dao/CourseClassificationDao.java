package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.CourseClassification;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CourseClassificationDao extends Repository<CourseClassification, Integer> {
    Optional<CourseClassification> findByName(String name);

    CourseClassification save(CourseClassification courseClassification);
}
