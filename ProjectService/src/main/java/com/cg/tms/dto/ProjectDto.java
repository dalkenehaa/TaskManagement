package com.cg.tms.dto;

import java.time.LocalDate;

public class ProjectDto {
	private int projectId;
	private String projectName;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int userId;

	public ProjectDto() {
	}

	public ProjectDto(int projectId, String projectName, String description, LocalDate startDate,
			LocalDate endDate) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ProjectDto{" + "projectId=" + projectId + ", projectName='" + projectName + '\'' + ", description='"
				+ description + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + '}';
	}
}