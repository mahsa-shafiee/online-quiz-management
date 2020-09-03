package com.maktab.onlineQuizManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class WebController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (Objects.nonNull(session)) {
            session.invalidate();
        }
        return "home";
    }

}
