package com.bsa.springdata.team;

import com.bsa.springdata.user.User;
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

    @Query("select t.technology.id from Team t " +
            "where t.users.size < :devNumber")
    List<UUID> findTeamsWithLessUsers(int devNumber);

    @Modifying
    @Transactional
    @Query(value = "update teams as t " +
            "set name = concat(t.name, '_', p.name, '_', tech.name) " +
            "from projects as p, technologies as tech " +
            "where t.name = :teamName and p.id = t.project_id and t.technology_id = tech.id",
            nativeQuery = true)
    void normalizeName(@Param("teamName") String teamName);
}
