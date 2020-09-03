package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.UserDao;
import com.maktab.onlineQuizManagement.model.dao.UserSpecifications;
import com.maktab.onlineQuizManagement.model.entity.User.User;
import com.maktab.onlineQuizManagement.model.entity.User.UserRegistrationStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {
    private final UserDao userDao;
    private final static int PAGE_SIZE = 5;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void registerNewUser(User user) {
        user.setRegistrationStatus(UserRegistrationStatus.NOT_CONFIRMED);
        user.setConfirmationToken(UUID.randomUUID().toString());
        saveUser(user);
    }

    public User findByEmailAddress(String emailAddress) {
        Optional<User> found = userDao.findByEmailAddress(emailAddress);
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
                             String password,
                             String role,
                             String registrationStatus,
                             int pageNumber) {
        if (pageNumber < 1)
            pageNumber = 1;

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");

        List<User> users = userDao.findAll(UserSpecifications.findMaxMatch
                (name, family, emailAddress, password, role, registrationStatus), pageRequest)
                .getContent();

        return users.isEmpty() ? search
                (name, family, emailAddress, password, role, registrationStatus, --pageNumber)
                : users;
    }

    public User getUser(int id) {
        return userDao.findById(id).orElse(null);
    }

    public void deleteUser(@PathVariable("id") int id) {
        userDao.deleteById(id);
    }

    public void updateUser(@RequestBody User user) {
        User found = getUser(user.getId());
        user.setRegistrationStatus(found.getRegistrationStatus());
        user.setConfirmationToken(found.getConfirmationToken());
        userDao.save(user);
    }

    public void addUser(@RequestBody User user) {
        userDao.save(user);
    }
}
