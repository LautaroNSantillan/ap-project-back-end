package com.ap.portfolio.services.impl;

import com.ap.portfolio.models.Education;
import com.ap.portfolio.repositories.EducationRepository;
import com.ap.portfolio.services.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EducationServiceImpl implements EducationService {
    @Autowired
    private EducationRepository educationRepository;
    @Override
    public List<Education> eduList() {
        return this.educationRepository.findAll();
    }

    @Override
    public List<Education> activeEduList() {
        return this.educationRepository.findByActiveTrue();
    }

    @Override
    public Optional<Education> findById(int id) {
        return this.educationRepository.findById(id);
    }

    @Override
    public Optional<Education> findByName(String name) {
        return this.educationRepository.findByEduName(name);
    }

    @Override
    public boolean existsByName(String name) {
        return this.educationRepository.existsByEduName(name);
    }

    @Override
    public boolean existsById(int id) {
        return this.educationRepository.existsById(id);
    }

    @Override
    public void save(Education education) {
        this.educationRepository.save(education);
    }

    @Override
    public void disable(Education education) {
        education.setActive(false);
        this.educationRepository.save(education);
    }

    @Override
    public List<Education> findActiveEducationByUserId(int id) {
        return this.educationRepository.findActiveEducationByUserId(id);
    }

    @Override
    public List<Education> findMyActiveEducation() {
        return null;
    }
}
