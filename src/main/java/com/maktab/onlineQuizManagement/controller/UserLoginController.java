package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.User;
import com.maktab.onlineQuizManagement.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userPanel")
@Log4j2
public class UserLoginController {
    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm(@RequestParam(value = "error", required = false) String error, ModelAndView modelAndView) {
        if (error != null)
            modelAndView.addObject("errorMessage", "Incorrect information");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("userPanel/login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(@ModelAttribute("user") User user, ModelAndView modelAndView) {

        User found = userService.findByEmailAddressAndPassword(user);
        modelAndView.addObject("userId", found.getId());
        modelAndView.setViewName("userPanel/userPanelHome");
        return modelAndView;
    }
}
