package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.QuizDao;
import com.maktab.onlineQuizManagement.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final static int PAGE_SIZE = 5;

    private final QuizDao quizDao;
    private final CourseService courseService;
    private final UserService userService;
    private final QuestionService questionService;
    private final QuizQuestionsService quizQuestionsService;
    private final CourseClassificationService classificationService;
    private final UserQuizService userQuizService;
    private final StudentService studentService;

    public QuizService(QuizDao quizDao, CourseService courseService, UserService userService, QuestionService questionService, QuizQuestionsService quizQuestionsService, CourseClassificationService classificationService, UserQuizService userQuizService, StudentService studentService) {
        this.quizDao = quizDao;
        this.courseService = courseService;
        this.userService = userService;
        this.questionService = questionService;
        this.quizQuestionsService = quizQuestionsService;
        this.classificationService = classificationService;
        this.userQuizService = userQuizService;
        this.studentService = studentService;
    }

    public Quiz addNewQuiz(Quiz quiz) {
        Course course = courseService.findById(quiz.getCourse().getId());
        User creator = userService.findById(quiz.getCreator().getId());
        quiz.setCourse(course);
        quiz.setCreator(creator);
        return quizDao.save(quiz);
    }

    public Quiz findById(int id) {
        return quizDao.findById(id).orElse(null);
    }

    public void updateQuiz(@RequestBody Quiz quiz) {
        Quiz found = findById(quiz.getId());
        quiz.setCreator(found.getCreator());
        quiz.setCourse(found.getCourse());
        saveQuiz(quiz);
    }

    public void saveQuiz(Quiz quiz) {
        quizDao.save(quiz);
    }

    public Quiz delete(int id) {
        Quiz quiz = findById(id);
        quizDao.deleteById(id);
        return quiz;
    }

    public Quiz stop(int id) {
        Quiz quiz = findById(id);
        quiz.setEnabled(false);
        saveQuiz(quiz);
        return quiz;
    }

    public List<User> getParticipantsPage(int id, int page, String name, String family, String emailAddress) {
        Set<UserQuizzes> participants = findById(id).getParticipants();

        List<User> collect = participants.stream()
                .map(UserQuizzes::getPrimaryKey)
                .map(UserQuizId::getUser)
                .sorted(Comparator.comparingInt(User::getId))
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());

        if (!StringUtils.isEmpty(name) || name.length() != 0) {
            collect = collect.stream().filter(user -> user.getName().contains(name)).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(family) || family.length() != 0) {
            collect = collect.stream().filter(user -> user.getFamily().contains(family)).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(emailAddress) || emailAddress.length() != 0) {
            collect = collect.stream().filter(user -> user.getEmailAddress().contains(emailAddress)).collect(Collectors.toList());
        }
        return collect;
    }

    public List<Question> getQuestionsPage(int id, int page) {
        Set<QuizQuestions> quizQuestions = findById(id).getQuizQuestions();

        return quizQuestions.stream()
                .map(QuizQuestions::getPrimaryKey)
                .map(QuizQuestionId::getQuestion)
                .sorted(Comparator.comparingInt(Question::getId))
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());
    }

    public List<Question> getQuestionBankList(int quizId, int page) {
        Quiz quiz = findById(quizId);
        return quiz.getCourse().getClassification().getQuestionBank().stream()
                .sorted(Comparator.comparingInt(Question::getId))
                .skip((page - 1) * 5)
                .limit(5)
                .collect(Collectors.toList());
    }

    public Question addNewQuestionFromBank(int quizId, int questionId) {
        Quiz quiz = findById(quizId);
        Question question = questionService.getQuestion(questionId);
        QuizQuestions quizQuestions = new QuizQuestions();
        quizQuestions.getPrimaryKey().setQuestion(question);
        quizQuestions.getPrimaryKey().setQuiz(quiz);
        quizQuestions.setScore(0);
        quizQuestionsService.save(quizQuestions);
        return question;
    }

    public QuizQuestions setScoreForQuizQuestion(int questionId, int quizId, int score) {
        Question question = questionService.getQuestion(questionId);
        QuizQuestionId quizQuestionId = new QuizQuestionId();
        quizQuestionId.setQuestion(question);
        quizQuestionId.setQuiz(findById(quizId));
        QuizQuestions quizQuestions = quizQuestionsService.get(quizQuestionId);
        quizQuestions.setScore(score);
        return quizQuestionsService.save(quizQuestions);
    }

    public void addNewQuestion(Question question, int quizId, String add) {
        Quiz quiz = quizQuestionsService.addNew(question, findById(quizId));
        if (add.equals("true")) {
            CourseClassification classification = quiz.getCourse().getClassification();
            classification.getQuestionBank().add(question);
            classificationService.saveCourse(classification);
        }
    }

    public Question getQuizQuestion(int quizId, int userId, int number) {
        Quiz quiz = findById(quizId);
        Student student = studentService.findById(userId);
        if (userQuizService.findByPrimaryKey(userId, quizId) == null) {
            UserQuizId userQuizId = new UserQuizId();
            userQuizId.setQuiz(quiz);
            userQuizId.setUser(student);
            UserQuizzes userQuizzes = new UserQuizzes();
            userQuizzes.setPrimaryKey(userQuizId);
            student.getParticipatedQuizzes().add(userQuizzes);
            studentService.save(student);
        }
        List<Question> collect = getQuestionOfQuiz(quiz);
        return collect.get(number);
    }

    public Question saveAnswer(int quizId, int userId, int number, String answer) {
        UserQuizzes stdQuiz = userQuizService.findByPrimaryKey(userId, quizId);
        stdQuiz.getAnswersOfQuestions().put(number, answer);
        userQuizService.save(stdQuiz);

        Quiz quiz = findById(quizId);
        List<Question> collect = getQuestionOfQuiz(quiz);
        Question question = collect.get(number);
        if (question instanceof MultipleChoiceQuestion) {
            if (((MultipleChoiceQuestion) question).getCorrectOption().equals(answer)) {
                stdQuiz.setTotalScore(stdQuiz.getTotalScore() + 1);
                userQuizService.save(stdQuiz);
            }
        }
        return question;
    }

    private List<Question> getQuestionOfQuiz(Quiz quiz) {
        List<QuizQuestions> list = new ArrayList<>(quiz.getQuizQuestions());
        return list
                .stream()
                .map(QuizQuestions::getPrimaryKey)
                .map(QuizQuestionId::getQuestion)
                .sorted(Comparator.comparingInt(Question::getId))
                .collect(Collectors.toList());
    }
}
