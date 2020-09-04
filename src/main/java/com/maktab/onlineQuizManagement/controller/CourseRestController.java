package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.CourseClassification;
import com.maktab.onlineQuizManagement.service.CourseClassificationService;
import com.maktab.onlineQuizManagement.service.CourseService;
import com.maktab.onlineQuizManagement.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PreRemove;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseRestController {

    private final CourseService courseService;
    private final CourseClassificationService classificationService;
    private final UserService userService;

    public CourseRestController(CourseService courseService, CourseClassificationService classificationService, UserService userService) {
        this.courseService = courseService;
        this.classificationService = classificationService;
        this.userService = userService;
    }

    @GetMapping("/show")
    public Collection<Course> getCourses(@RequestParam(value = "page", defaultValue = "1", required = false) int pageNumber,
                                         HttpServletRequest request) {
        return courseService.getPage(pageNumber);
    }

    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course,
                            HttpServletRequest request) {
        String enteredClassification = course.getClassification().getName();
        CourseClassification found = classificationService.getByName(enteredClassification);
        if (found != null) {
            course.setClassification(found);
            courseService.saveCourse(course);
            return course;
        }
        return null;
    }

    @DeleteMapping("/delete")
    @PreRemove
    public Course deleteCourse(@RequestParam int id,
                               HttpServletRequest request) {
        Course course = courseService.findById(id);
        courseService.delete(id);
        return course;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateCourse(@PathVariable("id") int id,
                                     ModelAndView modelAndView,
                                     HttpServletRequest request) {
        Course course = courseService.findById(id);
        modelAndView.addObject("course", course);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("adminPanel/updateCourse");
        return modelAndView;
    }

    @GetMapping("/members/{id}")
    public List<User> getMembers(@PathVariable("id") int id,
                                 @RequestParam int page,
                                 HttpServletRequest request) {
        return courseService.getMembers(id, page);
    }

    @GetMapping("members/{id}/add")
    public Object addMember(@PathVariable("id") int id,
                            @RequestParam int userId,
                            HttpServletRequest request) {
        User found = userService.findById(userId);
        if (found != null) {
            Course course = courseService.findById(id);
            if (course.getMembers() != null) {
                if (course.getMembers().contains(found))
                    return "This user added before!";
                course.getMembers().add(found);
            } else {
                course.setMembers(Collections.singletonList(found));
            }
            courseService.saveCourse(course);
            return found;
        }
        return "There is no such user!";
    }

    @DeleteMapping("members/{id}/delete")
    public User deleteMember(@PathVariable("id") int id,
                             @RequestParam int userId,
                             HttpServletRequest request) {
        User found = userService.findById(userId);
        Course course = courseService.findById(id);
        course.getMembers().remove(found);
        courseService.saveCourse(course);
        return found;
    }

}
