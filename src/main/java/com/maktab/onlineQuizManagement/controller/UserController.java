package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.User;
import com.maktab.onlineQuizManagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/userPanel")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView showUserPanel(@PathVariable int userId, ModelAndView modelAndView) {
        modelAndView.addObject("userId", userId);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/{userId}/quizzes")
    public ModelAndView showQuizzesPage(@PathVariable int userId, @RequestParam int courseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("userId", userId);
        User found = userService.findById(userId);
        if (found.getRoles().get(0).getName().equals("ROLE_TEACHER"))
            modelAndView.setViewName("userPanel/teacher/quizzes");
        else {

            modelAndView.addObject("user", found);
            modelAndView.setViewName("userPanel/student/quizzes");
        }
        return modelAndView;
    }

    @GetMapping("/questions")
    public ModelAndView showQuizQuestions(@RequestParam int quizId, ModelAndView modelAndView) {
        modelAndView.addObject("id", quizId);
        modelAndView.setViewName("userPanel/teacher/questions");
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/courses/{id}")
    public List<Course> getCoursesPage(@PathVariable int id, @RequestParam int page) {
        return userService.getCoursePage(id, page);
    }
}
