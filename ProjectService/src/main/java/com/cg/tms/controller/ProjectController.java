package com.cg.tms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cg.tms.dto.ProjectDto;
import com.cg.tms.exception.UserNotFoundException;
import com.cg.tms.service.ProjectService;

import ch.qos.logback.core.model.Model;

import java.util.List;

@RestController  
@RequestMapping("/api/projects")
public class ProjectController {

	//http://localhost:8081/api/projects
	
    @Autowired
    private ProjectService projectService;
    
    //------------------- Get all projects -------------------
    @GetMapping("/")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        try {
            List<ProjectDto> projects = projectService.getAllProjects();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    //------------------- Add new project -------------------(13/03)

    @PostMapping("/add")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        try {
            ProjectDto createdProject = projectService.addProject(projectDto);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------- Get a specific project by ID -------------------
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("projectId") int projectId) {
        try {
            ProjectDto projectDto = projectService.getProjectById(projectId);
            return projectDto != null
                    ? new ResponseEntity<>(projectDto, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------- Update a project (PUT request) -------------------
    @PutMapping("/update/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable("projectId") int projectId, @RequestBody ProjectDto projectDto) {
        try {
            ProjectDto updatedProject = projectService.updateProject(projectId, projectDto);
            return updatedProject != null
                    ? new ResponseEntity<>(updatedProject, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //------------------- Delete a project (DELETE request) -------------------
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") int projectId) {
        try {
            boolean isDeleted = projectService.deleteProject(projectId);
            return isDeleted
                    ? new ResponseEntity<>("Success", HttpStatus.OK)
                    : new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Id does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //------------------- Get ongoing projects -------------------(13/03)

    @GetMapping("/ongoing")
    public ResponseEntity<List<ProjectDto>> getOngoingProjects() {
        List<ProjectDto> ongoingProjects = projectService.getOngoingProjects();
        return new ResponseEntity<>(ongoingProjects, HttpStatus.OK);
    }

  //------------------- Get projects within a date range -------------------(13/03)
    @GetMapping("/date-range")
    public ResponseEntity<List<ProjectDto>> getProjectsByDateRange(
            @RequestParam String startDate, @RequestParam String endDate) {
        try {
            List<ProjectDto> projects = projectService.getProjectsByDateRange(startDate, endDate);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------- Get projects by user id -------------------(14/03)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable int userId)
    								throws UserNotFoundException
    {
        List<ProjectDto> projects = projectService.getProjectsByUserId(userId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    //------------------- Get projects by status -------------------
//    @GetMapping("/status/{status}")
//    public ResponseEntity<List<ProjectDto>> getProjectsByStatus(@PathVariable("status") String status) {
//        try {
//            List<ProjectDto> projects = projectService.getProjectsByStatus(status);
//            return new ResponseEntity<>(projects, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //------------------- Get high priority tasks -------------------
//    @GetMapping("/high-priority-tasks")
//    public ResponseEntity<List<ProjectDto>> getHighPriorityTasks() {
//        try {
//            List<ProjectDto> projects = projectService.getHighPriorityTasks();
//            return new ResponseEntity<>(projects, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}



