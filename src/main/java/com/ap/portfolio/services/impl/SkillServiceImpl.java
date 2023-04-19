package com.ap.portfolio.services.impl;

import com.ap.portfolio.models.Skill;
import com.ap.portfolio.repositories.SkillsRepository;
import com.ap.portfolio.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillsRepository skillsRepository;

    @Override
    public Optional<Skill> findBySkillName(String skillName) {
        return this.skillsRepository.findBySkillName(skillName);
    }

    @Override
    public Optional<Skill> findById(int id) {
        return this.skillsRepository.findById(id);
    }

    @Override
    public List<Skill> findByActiveTrue() {
        return this.skillsRepository.findByActiveTrue();
    }

    @Override
    public boolean existsBySkillName(String skillName) {
        return this.skillsRepository.existsBySkillName(skillName);
    }

    @Override
    public boolean existsById(int id) {
        return this.skillsRepository.existsById(id);
    }

    @Override
    public void save(Skill skill) {
        this.skillsRepository.save(skill);
    }

    @Override
    public void disable(int id) {
        Skill skill=this.skillsRepository.findById(id).get();
        skill.setActive(false);
        this.skillsRepository.save(skill);
    }
}
