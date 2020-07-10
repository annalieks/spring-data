package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.ProjectSummaryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT p FROM Project p " +
            "INNER JOIN p.teams t " +
            "ON t.technology.name LIKE :technology " +
            "ORDER BY SIZE(t.users)")
    List<Project> findTop5ByTechnology(@Param("technology") String technology, Pageable pageable);

    @Query("SELECT p FROM Project p " +
            "INNER JOIN p.teams t " +
            "INNER JOIN t.users u " +
            "ORDER BY SIZE(t) DESC, " +
            "SIZE(u) DESC," +
            "p.name DESC")
    List<Project> findTheBiggest(Pageable pageable);

    @Query(value = "SELECT p.name AS name, " +
            "COUNT(team) AS teamsNumber, " +
            "SUM(usersnum.userscount) AS developersNumber, " +
            "STRING_AGG(tech.name, ',') AS technologies " +
            "FROM projects AS p " +
            "INNER JOIN teams AS team ON (team.project_id = p.id) " +
            "INNER JOIN technologies AS tech ON (tech.id = team.technology_id) " +
            "INNER JOIN (SELECT t.id AS id, COUNT(u) AS userscount FROM teams AS t " +
            "INNER JOIN users AS u ON (u.team_id = t.id) " +
            "GROUP BY t.id) AS usersnum ON (usersnum.id = team.id) " +
            "GROUP BY p.name " +
            "ORDER BY p.name",
            nativeQuery = true)
    List<ProjectSummaryDto> getSummary();

    @Query("SELECT COUNT(DISTINCT p) FROM Project p " +
            "INNER JOIN p.teams t " +
            "INNER JOIN t.users u " +
            "INNER JOIN u.roles r ON r.name LIKE :role ")
    int getCountWithRole(@Param("role") String role);
}