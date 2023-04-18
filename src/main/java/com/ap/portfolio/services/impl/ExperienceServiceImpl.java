package com.ap.portfolio.services.impl;

import com.ap.portfolio.models.Experience;
import com.ap.portfolio.repositories.ExperienceRepository;
import com.ap.portfolio.services.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public List<Experience> allExp() {
        return this.experienceRepository.findAll();
    }

    @Override
    public List<Experience> activeExps() {
        return this.experienceRepository.findByActiveTrue();
    }

    @Override
    public Optional<Experience> findById(int id) {
        return this.experienceRepository.findById(id);
    }

    @Override
    public Optional<Experience> findByName(String name) {
        return this.experienceRepository.findByExpName(name);
    }

    @Override
    public void save(Experience exp) {
        this.experienceRepository.save(exp);
    }

    @Override
    public void disableExp(Experience exp) {
        exp.setActive(false);
    }

    @Override
    public boolean existstById(int id) {
        return this.experienceRepository.existsById(id);
    }

    @Override
    public boolean existstByName(String name) {
        return this.experienceRepository.existsByExpName(name);
    }

}
