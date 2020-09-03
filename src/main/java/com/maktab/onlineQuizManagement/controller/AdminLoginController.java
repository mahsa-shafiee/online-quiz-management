package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.Admin;
import com.maktab.onlineQuizManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminLoginController {
    private final AdminService adminService;

    public AdminLoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm() {
        return new ModelAndView("adminPanel/login", "admin", new Admin());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(@ModelAttribute("admin") Admin admin,
                                         HttpServletRequest request,
                                         ModelAndView modelAndView) {

        Admin found = adminService.findByUserNameAndPassword(admin);
        if (found != null) {
            HttpSession session = request.getSession();
            modelAndView.setViewName("redirect:panel");
        } else {
            modelAndView.addObject("errorMessage", "The information is not correct.");
            modelAndView.setViewName("adminPanel/login");
        }
        return modelAndView;
    }
}
