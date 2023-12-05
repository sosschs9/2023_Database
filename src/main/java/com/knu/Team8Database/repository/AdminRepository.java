package com.knu.Team8Database.repository;

import com.knu.Team8Database.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByAdminId(String adminId);
    @Query("SELECT A.adminId AS adminID "+
            "FROM Admin AS A")
    List<String> getAdminId();
}
