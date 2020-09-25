package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Student;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface StudentDao extends PagingAndSortingRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    Optional<Student> findById(Integer id);

}
