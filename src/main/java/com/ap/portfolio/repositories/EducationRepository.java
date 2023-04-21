package com.ap.portfolio.repositories;

import com.ap.portfolio.models.Education;
import com.ap.portfolio.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<Education> findByEduName(String eduName);
    List<Education> findByActiveTrue();
    boolean existsByEduName(String eduName);
    @Query("SELECT e FROM Education e WHERE e.user.id = CAST(:userId AS int) AND e.active = true")
    List<Education> findActiveEducationByUserId(@Param("userId") int userId);
}
