package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.DescriptiveQuestion;
import com.maktab.onlineQuizManagement.model.entity.MultipleChoiceQuestion;
import com.maktab.onlineQuizManagement.model.entity.Quiz;
import com.maktab.onlineQuizManagement.service.CourseClassificationService;
import com.maktab.onlineQuizManagement.service.QuestionService;
import com.maktab.onlineQuizManagement.service.QuizQuestionsService;
import com.maktab.onlineQuizManagement.service.QuizService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
public class WebController {

    private final QuizService quizService;
    private final QuestionService questionService;

    public WebController(QuizService quizService, QuestionService questionService, QuizQuestionsService quizQuestionsDao, CourseClassificationService classificationService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "home";
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    public String signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "forward:/";
    }

    @RequestMapping(value = "/addMultiple", method = RequestMethod.POST)
    public ModelAndView addNewMultiple(@ModelAttribute MultipleChoiceQuestion question, HttpServletRequest request, ModelAndView modelAndView) {
        question.setCorrectOption(request.getParameter("correct"));
        List<String> options = Arrays.asList(request.getParameter("options").split(","));
        question.setOptions(new HashSet<>(options));

        String add = request.getParameter("add-in-question-bank");
        question = (MultipleChoiceQuestion) questionService.save(question);
        quizService.addNewQuestion(question, Integer.parseInt(request.getParameter("id")), add);

        modelAndView.addObject("id", request.getParameter("id"));
        modelAndView.setViewName("userPanel/teacher/questions");
        return modelAndView;
    }

    @RequestMapping(value = "/addDesc", method = RequestMethod.POST)
    public ModelAndView addNewDesc(@ModelAttribute DescriptiveQuestion question, HttpServletRequest request, ModelAndView modelAndView) {
        String add = request.getParameter("add-in-question-bank");
        question = (DescriptiveQuestion) questionService.save(question);
        quizService.addNewQuestion(question, Integer.parseInt(request.getParameter("id")), add);
        modelAndView.addObject("id", request.getParameter("id"));
        modelAndView.setViewName("userPanel/teacher/questions");
        return modelAndView;
    }


    @RequestMapping(value = "/userPanel/updateQuiz", method = RequestMethod.POST)
    public ModelAndView processUpdateQuiz(ModelAndView modelAndView,
                                          @ModelAttribute("quiz") Quiz quiz,
                                          HttpServletRequest request) {
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        quiz.setStart(Timestamp.valueOf(start.replace('T', ' ')));
        quiz.setEnd(Timestamp.valueOf(end.replace('T', ' ')));
        if (quiz.getStart().before(quiz.getEnd())) {
            quizService.updateQuiz(quiz);
            modelAndView.addObject("confirmationMessage", "Quiz successfully updated.");
        } else {
            modelAndView.addObject("errorMessage", "Oops! Start date should be before end date.");
        }
        modelAndView.setViewName("userPanel/teacher/updateQuiz");
        return modelAndView;
    }
}
