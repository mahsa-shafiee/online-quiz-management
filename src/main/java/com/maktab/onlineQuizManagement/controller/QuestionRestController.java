package com.maktab.onlineQuizManagement.controller;

import com.maktab.onlineQuizManagement.model.entity.DescriptiveQuestion;
import com.maktab.onlineQuizManagement.model.entity.MultipleChoiceQuestion;
import com.maktab.onlineQuizManagement.model.entity.Question;
import com.maktab.onlineQuizManagement.model.entity.QuizQuestions;
import com.maktab.onlineQuizManagement.service.QuizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/userPanel/question")
@Log4j2
public class QuestionRestController {
    private final QuizService quizService;

    public QuestionRestController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{quizId}")
    public List<Question> getQuestionsPage(@PathVariable int quizId, @RequestParam int page) {
        return quizService.getQuestionsPage(quizId, page);
    }

    @GetMapping("/addFromBank")
    public ModelAndView getQuestionBank(@RequestParam int quizId, ModelAndView modelAndView) {
        modelAndView.addObject("id", quizId);
        modelAndView.setViewName("userPanel/questionBank");
        return modelAndView;
    }

    @GetMapping("/{quizId}/showBank")
    public List<Question> getQuestionBankPage(@PathVariable int quizId, @RequestParam int page) {
        return quizService.getQuestionBankList(quizId, page);
    }

    @GetMapping("/{quizId}/choose/{questionId}")
    public Question addQuestionFromBank(@PathVariable int quizId, @PathVariable int questionId) {
        return quizService.addNewQuestionFromBank(quizId, questionId);
    }

    @GetMapping("/addDescriptive")
    public ModelAndView showDescriptionQuestionForm(@RequestParam int quizId, ModelAndView modelAndView) {
        modelAndView.addObject("id", quizId);
        modelAndView.addObject("question", new DescriptiveQuestion());
        modelAndView.setViewName("userPanel/addNewDescriptiveQuestion");
        return modelAndView;
    }

    @GetMapping("/addMultipleChoice")
    public ModelAndView showMultipleChoiceQuestionForm(@RequestParam int quizId, ModelAndView modelAndView) {
        modelAndView.addObject("id", quizId);
        modelAndView.addObject("question", new MultipleChoiceQuestion());
        modelAndView.setViewName("userPanel/addNewMultipleChoiceQuestion");
        return modelAndView;
    }

    @GetMapping("/{questionId}/{quizId}/setScore")
    public QuizQuestions setScoreForQuestion(@PathVariable int questionId,
                                             @PathVariable int quizId,
                                             @RequestParam int score) {
        return quizService.setScoreForQuizQuestion(questionId, quizId, score);
    }
}
