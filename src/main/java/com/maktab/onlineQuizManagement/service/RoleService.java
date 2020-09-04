package com.maktab.onlineQuizManagement.service;

import com.maktab.onlineQuizManagement.model.dao.RoleDao;
import com.maktab.onlineQuizManagement.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role getRole(String name) {
        return roleDao.findByName(name);
    }
}
