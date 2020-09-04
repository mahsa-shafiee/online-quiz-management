package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.enums.UserRegistrationStatus;
import com.maktab.onlineQuizManagement.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserRestController {

    public final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show")
    public Collection<User> getUsers(@RequestParam(required = false) int page, HttpServletRequest request) {
        return userService.getPage(page);
    }

    @GetMapping("/search/{page}")
    public Collection<User> searchUser(@PathVariable(required = false) int page,
                                       @RequestParam String name,
                                       @RequestParam String family,
                                       @RequestParam String emailAddress,
                                       @RequestParam String password,
                                       @RequestParam String role,
                                       @RequestParam String registrationStatus,
                                       HttpServletRequest request) {
        return userService.search(name, family, emailAddress, password,
                role, registrationStatus, page);
    }

    @GetMapping("/confirm/{id}")
    public Object confirmUser(@PathVariable("id") int id, HttpServletRequest request) {
        User user = userService.getUser(id);
        if (user.getRegistrationStatus().equals(UserRegistrationStatus.CONFIRMED)) {
            return null;
        } else {
            user.setRegistrationStatus(UserRegistrationStatus.CONFIRMED);
            userService.updateUser(user);
            return user;
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") int id,
                                   ModelAndView modelAndView,
                                   HttpServletRequest request) {
        User user = userService.getUser(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("adminPanel/updateUser");
        return modelAndView;
    }

}
