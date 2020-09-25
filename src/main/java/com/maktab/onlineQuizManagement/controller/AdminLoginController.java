package com.maktab.onlineQuizManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adminPanel")
public class AdminLoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm() {
        return new ModelAndView("adminPanel/login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(String error, ModelAndView modelAndView) {
        if (error == null) {
            modelAndView.setViewName("redirect:/adminPanel");
        } else {
            modelAndView.addObject("errorMessage", "The information is not correct.");
            modelAndView.setViewName("adminPanel/login");
        }
        return modelAndView;
    }
}
