package com.bsa.springdata.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByName(String teamName);

    int countByTechnologyName(String technologyName);

    @Query("SELECT t.technology.id FROM Team t " +
            "WHERE SIZE(t.users) < :devNumber")
    List<UUID> findTeamsWithLessUsers(int devNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teams AS t " +
            "SET name = CONCAT(t.name, '_', p.name, '_', tech.name) " +
            "FROM projects AS p, technologies AS tech " +
            "WHERE t.name = :teamName AND p.id = t.project_id AND t.technology_id = tech.id",
            nativeQuery = true)
    void normalizeName(@Param("teamName") String teamName);
}
