package org.ironhack.coursequest.repository;

import org.ironhack.coursequest.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    boolean existsByEmail(String email);
}
