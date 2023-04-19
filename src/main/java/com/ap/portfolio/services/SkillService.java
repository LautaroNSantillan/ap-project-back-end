package com.ap.portfolio.services;

import com.ap.portfolio.models.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    Optional<Skill> findBySkillName(String skillName);
    Optional<Skill> findById(int id);
    List<Skill> findByActiveTrue();
    boolean existsBySkillName(String skillName);
    boolean existsById(int id);
    void save(Skill skill);
    void disable(int id);
}
