package com.knu.Team8Database.repository;
import com.knu.Team8Database.dto.FindsDTO;
import com.knu.Team8Database.entity.Finds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FindsRepository extends JpaRepository<Finds, String> {
    @Query("SELECT COUNT(distinct F.usersId) AS findCount, D.medicineId as medicineId, D.medicineName as medicineName " +
            "FROM Finds F " +
            "JOIN Detail_view D ON F.medicineId = D " +
            "GROUP BY D.medicineId, D.medicineName " +
            "ORDER BY findCount DESC")
    List<FindsDTO> findByTop5();



}