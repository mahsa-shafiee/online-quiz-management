package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

@org.springframework.stereotype.Repository
public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {

    Role findByName(String name);
}
