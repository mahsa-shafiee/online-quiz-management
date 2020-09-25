package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.UserDao;
import com.maktab.onlineQuizManagement.model.dao.UserSpecifications;
import com.maktab.onlineQuizManagement.model.entity.*;
import com.maktab.onlineQuizManagement.model.entity.enums.UserRegistrationStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private final PasswordEncoder passwordEncoder;
    private final static int PAGE_SIZE = 5;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService, StudentService studentService, TeacherService teacherService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void registerNewUser(User user, HttpServletRequest request) {
        Role role = roleService.getRole("ROLE_" + request.getParameter("role").toUpperCase());
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationStatus(UserRegistrationStatus.NOT_CONFIRMED);
        user.setConfirmationToken(UUID.randomUUID().toString());
        saveUser(user);
        if (role.getName().equals("ROLE_TEACHER")) {
            teacherService.save((Teacher) user);
        } else {
            studentService.save((Student) user);
        }
//        emailService.sendRegistrationEmail(user, request);
    }

    public void confirmUserRegistration(String token) {
        User user = findByConfirmationToken(token);
        user.setRegistrationStatus(UserRegistrationStatus.WAITING_FOR_CONFIRMATION);
        saveUser(user);
    }

    public User findByEmailAddress(String emailAddress) {
        Optional<User> found = userDao.findByEmailAddress(emailAddress);
        return found.orElse(null);
    }

    public User findByEmailAddressAndPassword(User user) {
        Optional<User> found = userDao.findByEmailAddressAndPassword(user.getEmailAddress(), (user.getPassword()));
        return found.orElse(null);
    }

    public User findByConfirmationToken(String token) {
        Optional<User> found = userDao.findByConfirmationToken(token);
        return found.orElse(null);
    }

    public User findById(int id) {
        Optional<User> found = userDao.findById(id);
        return found.orElse(null);
    }

    public List<User> getPage(int pageNumber) {
        if (pageNumber < 1)
            pageNumber = 1;

        PageRequest request = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");
        List<User> users = userDao.findAll(request).getContent();
        return users.isEmpty() ? getPage(--pageNumber) : users;
    }

    public List<User> search(String name,
                             String family,
                             String emailAddress,
                             String role,
                             String registrationStatus,
                             int pageNumber) {
        if (pageNumber < 1)
            pageNumber = 1;

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");

        List<User> users = userDao.findAll(UserSpecifications.findMaxMatch
                (name, family, emailAddress, role, registrationStatus), pageRequest)
                .getContent();

        return users;
    }

    public User getUser(int id) {
        return userDao.findById(id).orElse(null);
    }

    public void updateUser(@RequestBody User user) {
        User found = getUser(user.getId());
        user.setRegistrationStatus(found.getRegistrationStatus());
        user.setConfirmationToken(found.getConfirmationToken());
        userDao.save(user);
    }

    public List<Course> getCoursePage(int id, int page) {
        List<Course> courses = findById(id).getCourses();

        courses = courses.stream()
                .sorted(Comparator.comparingInt(Course::getId))
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());

        return courses;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByEmailAddress(emailAddress);
        log.info(user.toString());
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmailAddress(),
                user.get().getPassword(),
                mapRolesToAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public Object confirmUserRegistration(int id) {
        User user = getUser(id);
        if (user.getRegistrationStatus().equals(UserRegistrationStatus.CONFIRMED)) {
            return null;
        } else {
            user.setRegistrationStatus(UserRegistrationStatus.CONFIRMED);
            updateUser(user);
            return user;
        }
    }
}
