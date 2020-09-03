package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.CourseDao;
import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.User.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    private final CourseDao courseDao;
    private final static int PAGE_SIZE = 5;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public List<Course> getPage(int pageNumber) {
        if (pageNumber < 1)
            pageNumber = 1;

        PageRequest request = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");
        return courseDao.findAll(request).getContent();
    }

    public Course findById(int id) {
        Optional<Course> found = courseDao.findById(id);
        return found.orElse(null);
    }

    public void saveCourse(Course course) {
        courseDao.save(course);
    }

    public void updateCourse(Course course) {
        courseDao.update(course.getId(), course.getClassification().getId(), course.getName());
    }

    public List<User> getMembers(int id, int pageNumber) {
        List<User> members = findById(id).getMembers();

        members = members.stream()
                .sorted(Comparator.comparingInt(User::getId))
                .skip((pageNumber - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());

        return members;
    }

    public void delete(int id) {
        courseDao.deleteById(id);
    }
}
