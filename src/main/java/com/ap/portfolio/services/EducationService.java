package com.ap.portfolio.services;

import com.ap.portfolio.models.Education;

import java.util.List;
import java.util.Optional;

public interface EducationService {
    List<Education> eduList();
    List<Education> activeEduList();
    Optional<Education> findById(int id);
    Optional<Education> findByName(String name);
    boolean existsByName(String name);
    boolean existsById(int id);
    void save(Education education);
    void disable(Education education);
    List<Education> findActiveEducationByUserId(int id);
    List<Education> findMyActiveEducation();
}
