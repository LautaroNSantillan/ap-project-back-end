package com.ap.portfolio.repositories;

import com.ap.portfolio.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findBySkillName(String skillName);
    List<Skill> findByActiveTrue();
    boolean existsBySkillName(String skillName);
}
