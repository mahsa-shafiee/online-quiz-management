package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.AdminDao;
import com.maktab.onlineQuizManagement.model.entity.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdminService {
    private final AdminDao adminDao;

    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public Admin findByUserNameAndPassword(Admin admin) {
        Optional<Admin> found = adminDao.findByUserNameAndPassword(admin.getUserName(), admin.getPassword());
        return found.orElse(null);
    }

}
