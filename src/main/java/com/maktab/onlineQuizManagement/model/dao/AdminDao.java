package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Admin;
import org.springframework.data.repository.Repository;

import java.util.Optional;


@org.springframework.stereotype.Repository
public interface AdminDao extends Repository<Admin, Integer> {
    Optional<Admin> findByUserNameAndPassword(String userName, String password);
}
