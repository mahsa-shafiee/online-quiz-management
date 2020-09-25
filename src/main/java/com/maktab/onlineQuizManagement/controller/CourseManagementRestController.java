package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.User;
import com.maktab.onlineQuizManagement.service.CourseClassificationService;
import com.maktab.onlineQuizManagement.service.CourseService;
import com.maktab.onlineQuizManagement.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PreRemove;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/adminPanel/courses")
public class CourseManagementRestController {

    private final CourseService courseService;

    public CourseManagementRestController(CourseService courseService, CourseClassificationService classificationService, UserService userService) {
        this.courseService = courseService;
    }

    @GetMapping("/show")
    public Collection<Course> getCourses(@RequestParam(value = "page", defaultValue = "1", required = false) int pageNumber) {
        return courseService.getPage(pageNumber);
    }

    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addNewCourse(course);
    }

    @DeleteMapping("/delete")
    @PreRemove
    public Course deleteCourse(@RequestParam int id) {
        return courseService.delete(id);
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateCourse(@PathVariable("id") int id,
                                     ModelAndView modelAndView) {
        Course course = courseService.findById(id);
        modelAndView.addObject("course", course);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("adminPanel/updateCourse");
        return modelAndView;
    }

    @GetMapping("/members/{id}")
    public List<User> getMembers(@PathVariable("id") int id,
                                 @RequestParam int page) {
        return courseService.getMembers(id, page);
    }

    @GetMapping("/members/{id}/add")
    public Object addMember(@PathVariable("id") int id,
                            @RequestParam int userId) {
        return courseService.addNewMember(id, userId);
    }

    @DeleteMapping("/members/{id}/delete")
    public User deleteMember(@PathVariable("id") int id,
                             @RequestParam int userId) {
        return courseService.deleteMember(id, userId);
    }

}
