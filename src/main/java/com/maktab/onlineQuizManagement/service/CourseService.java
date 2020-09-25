package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.CourseDao;
import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.CourseClassification;
import com.maktab.onlineQuizManagement.model.entity.Quiz;
import com.maktab.onlineQuizManagement.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    private final CourseDao courseDao;
    private final CourseClassificationService classificationService;
    private final UserService userService;
    private final static int PAGE_SIZE = 5;

    public CourseService(CourseDao courseDao, CourseClassificationService classificationService, UserService userService) {
        this.courseDao = courseDao;
        this.classificationService = classificationService;
        this.userService = userService;
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


    public Course delete(int id) {
        Course course = findById(id);
        courseDao.deleteById(id);
        return course;
    }

    public User deleteMember(int id, int userId) {
        User found = userService.findById(userId);
        Course course = findById(id);
        course.getMembers().remove(found);
        saveCourse(course);
        return found;
    }

    public Object addNewMember(int id, int userId) {
        User found = userService.findById(userId);
        if (found != null) {
            Course course = findById(id);
            if (course.getMembers() != null) {
                if (course.getMembers().contains(found))
                    return "This user added before!";
                course.getMembers().add(found);
            } else {
                course.setMembers(Collections.singletonList(found));
            }
            saveCourse(course);
            return found;
        }
        return "There is no such user!";
    }

    public List<Quiz> getQuizzesPage(int id, int page) {
        List<Quiz> quizzes = findById(id).getQuizzes();

        quizzes = quizzes.stream()
                .sorted(Comparator.comparingInt(Quiz::getId))
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());

        return quizzes;
    }

    public Course addNewCourse(Course course) {
        String enteredClassification = course.getClassification().getName();
        CourseClassification found = classificationService.getByName(enteredClassification);
        if (found != null) {
            course.setClassification(found);
            saveCourse(course);
            return course;
        }
        return null;
    }
}
