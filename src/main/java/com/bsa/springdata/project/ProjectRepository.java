package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.ProjectSummaryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("select p from Project p " +
            "inner join p.teams t " +
            "on t.technology.name like :technology " +
            "order by t.users.size asc")
    List<Project> findTop5ByTechnology(@Param("technology") String technology, Pageable pageable);

    @Query("select p from Project p " +
            "inner join p.teams t " +
            "inner join t.users u " +
            "order by t.size desc, " +
            "u.size desc," +
            "p.name desc")
    List<Project> findTheBiggest(Pageable pageable);

    @Query(value = "select p.name as name, " +
            "count(team) as teamsNumber, " +
            "sum(usersnum.count) as developersNumber, " +
            "string_agg(tech.name, ',') as technologies " +
            "from projects as p " +
            "inner join teams as team on (team.project_id = p.id) " +
            "inner join technologies as tech on (tech.id = team.technology_id) " +
            "inner join (select t.id as id, count(u) as count from teams as t " +
            "inner join users as u on (u.team_id = t.id) " +
            "group by t.id) as usersnum on (usersnum.id = team.id) " +
            "group by p.name " +
            "order by p.name",
            nativeQuery = true)
    List<ProjectSummaryDto> getSummary();

    @Query("select count(distinct p) from Project p " +
            "inner join p.teams t " +
            "inner join t.users u " +
            "inner join u.roles r where r.name like :role " +
            "group by r.name")
    int getCountWithRole(@Param("role") String role);
}