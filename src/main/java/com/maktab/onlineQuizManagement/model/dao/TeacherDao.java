package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface TeacherDao extends PagingAndSortingRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {

    Optional<Teacher> findById(Integer id);

}
