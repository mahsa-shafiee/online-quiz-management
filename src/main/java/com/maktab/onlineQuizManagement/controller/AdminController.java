package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.exception.DuplicateEmailException;
import com.maktab.onlineQuizManagement.model.entity.Course;
import com.maktab.onlineQuizManagement.model.entity.CourseClassification;
import com.maktab.onlineQuizManagement.model.entity.User;
import com.maktab.onlineQuizManagement.service.CourseClassificationService;
import com.maktab.onlineQuizManagement.service.CourseService;
import com.maktab.onlineQuizManagement.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/adminPanel")
@Log4j2
public class AdminController {

    private final UserService userService;
    private final CourseClassificationService classificationService;
    private final CourseService courseService;

    public AdminController(UserService userService, CourseClassificationService classificationService, CourseService courseService) {
        this.userService = userService;
        this.classificationService = classificationService;
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showPanelPage() {
        return "adminPanel/adminPanelHome";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String showUsersPage() {
        return "adminPanel/usersTable";
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String showCoursesPage() {
        return "adminPanel/coursesTable";
    }

    @RequestMapping(value = "/course/members/{id}")
    public String showCourseMembersPage(@PathVariable int id) {
        return "adminPanel/courseMembersTable";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ModelAndView processUpdateUser(ModelAndView modelAndView,
                                          @ModelAttribute("user") User user,
                                          HttpServletRequest request) {
        try {
            User found = userService.findByEmailAddress(user.getEmailAddress());

            if (found != null && user.getId() != found.getId())
                throw new DuplicateEmailException("There is already a user registered with the email provided.");

            user.setId(Integer.parseInt(request.getParameter("id")));
            userService.updateUser(user);
            modelAndView.addObject("confirmationMessage",
                    "User successfully updated.");
        } catch (DuplicateEmailException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        modelAndView.setViewName("adminPanel/updateUser");
        return modelAndView;
    }

    @RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
    public ModelAndView processUpdateCourse(ModelAndView modelAndView,
                                            @ModelAttribute("course") Course course) {
        CourseClassification found = classificationService.getByName(course.getClassification().getName());
        if (found != null) {
            course.setClassification(found);
            courseService.updateCourse(course);
            modelAndView.addObject("confirmationMessage", "Course successfully updated.");
        } else {
            modelAndView.addObject("errorMessage", "Oops! There is no such course classification.");
        }
        modelAndView.setViewName("adminPanel/updateCourse");
        return modelAndView;
    }
}
