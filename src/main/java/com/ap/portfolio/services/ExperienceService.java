package com.ap.portfolio.services;

import com.ap.portfolio.models.Experience;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    List<Experience> allExp();
    List<Experience> activeExps();
    Optional<Experience> findById(int id);
    Optional<Experience> findByName(String name);
    void save (Experience exp);
    void disableExp(Experience exp);
    boolean existstById(int id);
    boolean existstByName(String name);
}
