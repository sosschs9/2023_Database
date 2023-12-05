package com.knu.Team8Database.repository;

import com.knu.Team8Database.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsersId(String userId);
    @Query("SELECT U.usersId AS usersId "+
            "FROM Users AS U")
    List<String> getUsersId();
}
