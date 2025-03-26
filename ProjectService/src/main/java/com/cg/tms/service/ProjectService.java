package com.cg.tms.service;

import java.util.List;
import com.cg.tms.dto.ProjectDto;
import com.cg.tms.exception.UserNotFoundException;

public interface ProjectService {

    public ProjectDto addProject(ProjectDto projectDto);

    public ProjectDto getProjectById(int projectId);

    public List<ProjectDto> getAllProjects();

    public ProjectDto updateProject(int projectId, ProjectDto projectDto);

    public boolean deleteProject(int projectId);

    public List<ProjectDto> getProjectsByDateRange(String startDate, String endDate);

//	public List<String> getUserRolesByProjectId(int projectId);

//    public List<ProjectDto> getProjectsByStatus(String status);

	List<ProjectDto> getOngoingProjects();

	public List<ProjectDto> getProjectsByUserId(int userId) throws UserNotFoundException;

//    public List<ProjectDto> getHighPriorityTasks();
}
