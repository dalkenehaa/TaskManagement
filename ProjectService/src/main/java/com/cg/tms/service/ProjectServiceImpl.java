package com.cg.tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.tms.dto.ProjectDto;
import com.cg.tms.entity.ProjectEntity;
import com.cg.tms.exception.ProjectNotFoundException;
import com.cg.tms.exception.UserNotFoundException;
import com.cg.tms.repo.ProjectRepository;
import com.cg.tms.util.ProjectUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // ------------------- Get a project by ID -------------------
    @Override
    public ProjectDto getProjectById(int projectId) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if (projectEntity.isPresent()) {
            return ProjectUtil.convertEntityToDto(projectEntity.get());
        } else {
            throw new ProjectNotFoundException("Project not found for ID: " + projectId);
        }
    }

    // ------------------- Get all projects -------------------
    @Override
    public List<ProjectDto> getAllProjects() {
        List<ProjectEntity> projectEntities = projectRepository.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();

        for (ProjectEntity entity : projectEntities) {
            projectDtos.add(ProjectUtil.convertEntityToDto(entity));
        }
        return projectDtos;
    }

    // ------------------- Update a project -------------------
    
    @Override
    public ProjectDto updateProject(int projectId, ProjectDto projectDto) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if (projectEntity.isPresent()) {
            ProjectEntity entityToUpdate = projectEntity.get();
            entityToUpdate.setProjectName(projectDto.getProjectName());
            entityToUpdate.setDescription(projectDto.getDescription());
            entityToUpdate.setStartDate(projectDto.getStartDate());
            entityToUpdate.setEndDate(projectDto.getEndDate());

            ProjectEntity updatedEntity = projectRepository.save(entityToUpdate);
            return ProjectUtil.convertEntityToDto(updatedEntity);
        } else {
            throw new ProjectNotFoundException("Project not found for ID: " + projectId);
        }
    }

    // ------------------- Delete a project -------------------
    @Override
    public boolean deleteProject(int projectId) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if (projectEntity.isPresent()) {
            projectRepository.delete(projectEntity.get());
            return true;
        } else {
            throw new ProjectNotFoundException("Project not found for ID: " + projectId);
        }
    }
    
    //----------------ongoing projects-------------------------------
    @Override
    public List<ProjectDto> getOngoingProjects() {
        List<ProjectEntity> projectEntities = projectRepository.findOngoingProjects();
        
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (ProjectEntity entity : projectEntities) {
            projectDtos.add(ProjectUtil.convertEntityToDto(entity));
        }
        
        return projectDtos;
    }
    
 // ------------------- Get projects within a date range -------------------
    @Override
    public List<ProjectDto> getProjectsByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        List<ProjectEntity> projectEntities = projectRepository.
        		findByStartDateGreaterThanEqualAndEndDateLessThanEqual(start, end);
        
        // Convert entities to DTOs
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (ProjectEntity entity : projectEntities) {
            projectDtos.add(ProjectUtil.convertEntityToDto(entity));
        }
        return projectDtos;
    }
    
    //---------------------------------REST API------------------------------------------
    
    @Autowired
    private RestTemplate restTemplate;

    // ------------------- Add new project --------------------
    @Override
    public ProjectDto addProject(ProjectDto projectDto) {
        String projectServiceUrl = "http://localhost:8080/api/users/exist/" + projectDto.getUserId();
        
        Boolean UserExists = restTemplate.getForObject(projectServiceUrl, Boolean.class);
        if (!UserExists) {
            throw new ProjectNotFoundException("Project not found for User ID: " + projectDto.getUserId());
        }
        
        ProjectEntity projectEntity = ProjectUtil.convertDtoToEntity(projectDto);
        ProjectEntity savedEntity = projectRepository.save(projectEntity);
        return ProjectUtil.convertEntityToDto(savedEntity);
    }
    
 // ------------------- Get projects by user id -------------------
 
    @Override
    public List<ProjectDto> getProjectsByUserId(int userId) throws UserNotFoundException {
        String userServiceUrl = "http://localhost:8080/api/users/exist/" + userId;
        Boolean userExists = restTemplate.getForObject(userServiceUrl, Boolean.class);   
        if (!userExists) {
            throw new UserNotFoundException("User not found for ID: " + userId);
        }
        List<ProjectEntity> projectEntities = projectRepository.findByUserId(userId);
        if (projectEntities.isEmpty()) {
            throw new ProjectNotFoundException("No projects found for User ID: " + userId);
        }
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (ProjectEntity projectEntity : projectEntities) {
            projectDtos.add(ProjectUtil.convertEntityToDto(projectEntity));
        }
        return projectDtos;
    }
    
    
    // ------------------- Get projects by status -------------------
//    @Override
//    public List<ProjectDto> getProjectsByStatus(String status) {
//        // Implement the logic to fetch projects by status
//        // This is a placeholder implementation
//        List<ProjectEntity> projectEntities = projectRepository.findById(null);
//        List<ProjectDto> projectDtos = new ArrayList<>();
//        for (ProjectEntity entity : projectEntities) {
//            projectDtos.add(ProjectUtil.convertEntityToDto(entity));
//        }
//        return projectDtos;
//    }

    // ------------------- Get high priority tasks -------------------
//    @Override
//    public List<ProjectDto> getHighPriorityTasks() {
//        // Implement the logic to fetch high priority tasks
//        // This is a placeholder implementation
//        List<ProjectEntity> projectEntities = projectRepository.findHighPriorityTasks();
//        List<ProjectDto> projectDtos = new ArrayList<>();
//        for (ProjectEntity entity : projectEntities) {
//            projectDtos.add(ProjectUtil.convertEntityToDto(entity));
//        }
//        return projectDtos;
//    }
}