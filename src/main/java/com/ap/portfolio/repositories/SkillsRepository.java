package com.ap.portfolio.repositories;

import com.ap.portfolio.models.Skill;
import com.ap.portfolio.models.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findBySkillName(String skillName);
    List<Skill> findByActiveTrue();
    boolean existsBySkillName(String skillName);
    @Query("SELECT e FROM Skill e WHERE e.user.id = CAST(:userId AS int) AND e.active = true")
    List<Skill> findActiveSkillByUserId(@Param("userId") int userId);
    Skill findByUserAndSkillName(WebUser user, String skillName);
}
