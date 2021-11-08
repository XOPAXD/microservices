package com.jhone.auth.repository;

import com.jhone.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u from User  u WHERE u.userName =:userName")
    User FindByUserName(@Param("userName") String userName);
}
