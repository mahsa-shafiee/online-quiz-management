package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.Question;
import com.maktab.onlineQuizManagement.model.entity.Quiz;
import com.maktab.onlineQuizManagement.model.entity.User;
import com.maktab.onlineQuizManagement.model.entity.UserQuizzes;
import com.maktab.onlineQuizManagement.service.CourseService;
import com.maktab.onlineQuizManagement.service.QuizService;
import com.maktab.onlineQuizManagement.service.StudentService;
import com.maktab.onlineQuizManagement.service.UserQuizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/userPanel/quiz")
@Log4j2
public class QuizRestController {
    private final CourseService courseService;
    private final QuizService quizService;
    private final StudentService userService;
    private final UserQuizService userQuizService;

    public QuizRestController(CourseService courseService, QuizService quizService, StudentService userService, UserQuizService userQuizService) {
        this.courseService = courseService;
        this.quizService = quizService;
        this.userService = userService;
        this.userQuizService = userQuizService;
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
        modelAndView.setViewName("userPanel/teacher/updateQuiz");
        return modelAndView;
    }

    @GetMapping("/{id}/participants")
    public ModelAndView showQuizParticipants(@PathVariable("id") int id,
                                             ModelAndView modelAndView) {
        Quiz quiz = quizService.findById(id);
        modelAndView.addObject("participantsCount", quiz.getParticipants().size());
        modelAndView.addObject("quizId", id);
        modelAndView.setViewName("userPanel/teacher/quizParticipants");
        return modelAndView;
    }

    @GetMapping("/{quizId}/participants/get")
    public List<User> getParticipantsPage(@PathVariable int quizId,
                                          @RequestParam int page,
                                          @RequestParam String name,
                                          @RequestParam String family,
                                          @RequestParam String emailAddress) {
        return quizService.getParticipantsPage(quizId, page, name, family, emailAddress);
    }

    @GetMapping("/{quizId}/result")
    public ModelAndView showResult(@PathVariable int quizId, @RequestParam int userId, ModelAndView modelAndView) {
        modelAndView.addObject("quizId", quizId);
        modelAndView.addObject("userId", userId);
        modelAndView.setViewName("userPanel/teacher/quizResult");
        return modelAndView;
    }

    @GetMapping("/{quizId}/answer")
    public UserQuizzes getAnswer(@PathVariable int quizId, @RequestParam int userId) {
        return userService.getAnswer(quizId,userId);
    }

    @GetMapping("/{quizId}/{userId}/setScore")
    public UserQuizzes saveScore(@PathVariable int quizId, @PathVariable int userId, @RequestParam double score) {
        return userService.saveScore(quizId,userId,score);
    }

    @DeleteMapping("/{id}/delete")
    public Quiz deleteQuiz(@PathVariable("id") int id) {
        return quizService.delete(id);
    }

    @GetMapping("/{id}/stop")
    public Quiz stopQuiz(@PathVariable("id") int id) {
        return quizService.stop(id);
    }

    @GetMapping("/{id}/start")
    public ModelAndView startQuiz(@PathVariable("id") int quizId,
                                  @RequestParam int userId,
                                  @RequestParam int courseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("quizId", quizId);
        modelAndView.addObject("duration", quizService.findById(quizId).getDuration());
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("courseId", courseId);
        modelAndView.setViewName("userPanel/student/questions");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public Question showQuestion(@PathVariable("id") int quizId,
                                 @RequestParam int userId,
                                 @RequestParam int number) {
       return quizService.getQuizQuestion(quizId,userId,number);
    }

    @GetMapping("/{id}/save")
    public Question saveAnswer(@PathVariable("id") int quizId,
                               @RequestParam int userId,
                               @RequestParam int number,
                               @RequestParam String answer) {

        return quizService.saveAnswer(quizId,userId,number,answer);
    }
}
