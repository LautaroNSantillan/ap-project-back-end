package com.ap.portfolio.repositories;

import com.ap.portfolio.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
