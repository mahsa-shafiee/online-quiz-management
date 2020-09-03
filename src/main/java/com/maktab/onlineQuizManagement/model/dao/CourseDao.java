package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CourseDao extends PagingAndSortingRepository<Course, Integer> {

    Course save(Course course);

    List<Course> findAll();

    Optional<Course> findById(int id);

    void deleteById(int id);

    @Modifying
    @Query(value = "UPDATE Course SET name=:name,classification_id=:c_id WHERE id=:id", nativeQuery = true)
    void update(@Param("id") int id, @Param("c_id") int c_id, @Param("name") String name);
}
