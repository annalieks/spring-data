package com.bsa.springdata.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TechnologyRepository technologyRepository;

    public void updateTechnology(int devsNumber, String oldTechnologyName, String newTechnologyName) {
        // TODO: You can use several queries here. Try to keep it as simple as possible
        var teams = teamRepository.findTeamsWithLessUsers(devsNumber);
        technologyRepository.updateTechnology(oldTechnologyName, newTechnologyName, teams);
    }

    public void normalizeName(String hipsters) {
        // TODO: Use a single query. You need to create a native query
        teamRepository.normalizeName(hipsters);
    }
}
