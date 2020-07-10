package com.bsa.springdata.project.dto;

// TODO: Use this interface when you make a projection from native query.
//  If you don't use native query replace this interface with a simple POJO
public interface ProjectSummaryDto {
    String getName();
    Long getTeamsNumber();
    Long getDevelopersNumber();
    String getTechnologies();
}