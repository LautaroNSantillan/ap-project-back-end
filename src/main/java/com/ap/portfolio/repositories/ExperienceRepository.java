package com.ap.portfolio.repositories;

import com.ap.portfolio.models.Education;
import com.ap.portfolio.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Optional<Experience> findByExpName(String expName);
    boolean existsByExpName(String expName);
    List<Experience> findByActiveTrue();
    @Query("SELECT e FROM Experience e WHERE e.user.id = CAST(:userId AS int) AND e.active = true")
    List<Experience> findActiveEducationByUserId(@Param("userId") int userId);
}
