package com.maktab.onlineQuizManagement.model.dao;

import com.maktab.onlineQuizManagement.model.entity.UserQuizId;
import com.maktab.onlineQuizManagement.model.entity.UserQuizzes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface UserQuizDao extends PagingAndSortingRepository<UserQuizzes, UserQuizId> {

    @Query(value = "select * from User_Quiz where user_id=:user_id and quiz_id=:quiz_id", nativeQuery = true)
    UserQuizzes findByPrimaryKey(@Param("user_id") int user_id, @Param("quiz_id") int quiz_id);

}
