package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.CreateProjectRequestDto;
import com.bsa.springdata.project.dto.ProjectDto;
import com.bsa.springdata.project.dto.ProjectSummaryDto;
import com.bsa.springdata.team.Team;
import com.bsa.springdata.team.TeamRepository;
import com.bsa.springdata.team.Technology;
import com.bsa.springdata.team.TechnologyRepository;
import com.bsa.springdata.team.dto.TeamDto;
import com.bsa.springdata.team.dto.TechnologyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private TeamRepository teamRepository;

    public List<ProjectDto> findTop5ByTechnology(String technology) {
        // TODO: done
        //  Use single query to load data. Sort by number of developers in a project
        //  Hint: in order to limit the query you can either use native query with limit or Pageable interface
        return projectRepository
                .findTop5ByTechnology(technology, PageRequest.of(0, 5))
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDto> findTheBiggest() {
        // TODO: done
        //  Use single query to load data. Sort by teams, developers, project name
        //  Hint: in order to limit the query you can either use native query with limit or Pageable interface
        return Optional.of(projectRepository
                .findTheBiggest(PageRequest.of(0, 1))
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList())
                .get(0));
    }

    public List<ProjectSummaryDto> getSummary() {
        // TODO: done
        //  Try to use native query and projection first. If it fails try to make as few queries as possible
        return projectRepository.getSummary();

    }

    public int getCountWithRole(String role) {
        // TODO: Use a single query
        return projectRepository.getCountWithRole(role);
    }

    public UUID createWithTeamAndTechnology(CreateProjectRequestDto createProjectRequest) {
        // TODO: done
        //  Use common JPARepository methods. Build entities in memory and then persist them
        Technology technology = Technology
                .builder()
                .name(createProjectRequest.getTech())
                .description(createProjectRequest.getTechDescription())
                .link(createProjectRequest.getTechLink())
                .build();

        Project project = Project
                .builder()
                .name(createProjectRequest.getProjectName())
                .description(createProjectRequest.getProjectDescription())
                .build();

        Team team = Team
                .builder()
                .name(createProjectRequest.getTeamName())
                .area(createProjectRequest.getTeamArea())
                .room(createProjectRequest.getTeamRoom())
                .technology(technology)
                .project(project)
                .build();

        technologyRepository.save(technology);
        projectRepository.save(project);
        teamRepository.save(team);

        return project.getId();
    }
}
