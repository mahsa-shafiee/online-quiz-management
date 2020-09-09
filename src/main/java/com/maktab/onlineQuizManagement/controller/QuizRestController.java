package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.Quiz;
import com.maktab.onlineQuizManagement.service.CourseService;
import com.maktab.onlineQuizManagement.service.QuizService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/userPanel/quiz")
public class QuizRestController {
    private final CourseService courseService;
    private final QuizService quizService;

    public QuizRestController(CourseService courseService, QuizService quizService) {
        this.courseService = courseService;
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getCourseQuizzesPage(@RequestParam int courseId, @RequestParam int page) {
        return courseService.getQuizzesPage(courseId, page);
    }

    @PostMapping("/add")
    public Quiz addNewQuiz(@RequestBody Quiz quiz) {
        return quizService.addNewQuiz(quiz);
    }

    @GetMapping("/{id}/update")
    public ModelAndView updateQuiz(@PathVariable("id") int id,
                                   ModelAndView modelAndView) {
        Quiz quiz = quizService.findById(id);
        modelAndView.addObject("quiz", quiz);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("userPanel/updateQuiz");
        return modelAndView;
    }

    @DeleteMapping("/{id}/delete")
    public Quiz deleteQuiz(@PathVariable("id") int id) {
        return quizService.delete(id);
    }

    @GetMapping("/{id}/stop")
    public Quiz stopQuiz(@PathVariable("id") int id) {
        return quizService.stop(id);
    }

}
