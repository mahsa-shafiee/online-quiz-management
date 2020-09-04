package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserDao extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User save(User user);

    List<User> findAll();

    Optional<User> findByEmailAddress(String emailAddress);

    Optional<User> findByEmailAddressAndPassword(String emailAddress, String password);

    Optional<User> findByConfirmationToken(String token);

    Optional<User> findById(int id);

}
