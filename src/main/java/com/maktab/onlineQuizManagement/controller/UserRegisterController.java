package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.exception.DisabledTokenException;
import com.maktab.onlineQuizManagement.exception.DuplicateEmailException;
import com.maktab.onlineQuizManagement.exception.IncorrectTokenException;
import com.maktab.onlineQuizManagement.model.entity.enums.UserRegistrationStatus;
import com.maktab.onlineQuizManagement.service.EmailService;
import com.maktab.onlineQuizManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserRegisterController {

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public UserRegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm(User user) {
        return new ModelAndView("userPanel/register", "user", user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView,
                                                @ModelAttribute("user") User user,
                                                HttpServletRequest request) {
        try {
            User found = userService.findByEmailAddress(user.getEmailAddress());
            if (found != null)
                throw new DuplicateEmailException("There is already a user registered with the email provided.");
            userService.registerNewUser(user);
            emailService.sendRegistrationEmail(user, request);
            modelAndView.addObject("confirmationMessage",
                    "A confirmation e-mail has been successfully sent to " + user.getEmailAddress());
        } catch (DuplicateEmailException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        modelAndView.setViewName("userPanel/register");
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationForm(ModelAndView modelAndView,
                                             @RequestParam("token") String token) {
        try {
            User found = userService.findByConfirmationToken(token);

            if (found == null)
                throw new IncorrectTokenException("Oops! This is an invalid confirmation link.");
            else if (found.getRegistrationStatus().equals(UserRegistrationStatus.NOT_CONFIRMED))
                modelAndView.addObject("confirmationToken", found.getConfirmationToken());
            else
                throw new DisabledTokenException("You used this confirmation link.");

        } catch (IncorrectTokenException | DisabledTokenException e) {
            modelAndView.addObject("invalidToken", e.getMessage());
        }
        modelAndView.setViewName("userPanel/confirmRegistration");
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView,
                                                @RequestParam String token) {
        User user = userService.findByConfirmationToken(token);
        user.setRegistrationStatus(UserRegistrationStatus.WAITING_FOR_CONFIRMATION);
        userService.saveUser(user);
        modelAndView.addObject("successMessage",
                "You registered successfully.");
        modelAndView.setViewName("userPanel/confirmRegistration");
        return modelAndView;
    }

}
