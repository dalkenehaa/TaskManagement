package com.cg.tms.util;

import java.util.ArrayList;
import java.util.List;
import com.cg.tms.dto.ProjectDto;
import com.cg.tms.entity.ProjectEntity;

public class ProjectUtil {

    //-------------- Convert Dto to Entity -----------------------
	
    public static ProjectEntity convertDtoToEntity(ProjectDto projectDTO) {
        if (projectDTO == null) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectDTO.getProjectId());
        projectEntity.setProjectName(projectDTO.getProjectName());
        projectEntity.setDescription(projectDTO.getDescription());
        projectEntity.setStartDate(projectDTO.getStartDate());
        projectEntity.setEndDate(projectDTO.getEndDate());
        projectEntity.setUserId(projectDTO.getUserId());


        return projectEntity;
    }

    // ---------------Convert Entity to Dto ----------------------------
    
    public static ProjectDto convertEntityToDto(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }

        ProjectDto projectDTO = new ProjectDto();
        projectDTO.setProjectId(projectEntity.getProjectId());
        projectDTO.setProjectName(projectEntity.getProjectName());
        projectDTO.setDescription(projectEntity.getDescription());
        projectDTO.setStartDate(projectEntity.getStartDate());
        projectDTO.setEndDate(projectEntity.getEndDate());
        projectDTO.setUserId(projectEntity.getUserId());

        return projectDTO;
    }

    //--------------- Convert a List of Dto to a List of Entity ---------------
    
    public static List<ProjectEntity> convertToEntityList(List<ProjectDto> projectDTOList) {
        if (projectDTOList == null) {
            return null;
        }

        List<ProjectEntity> projectEntityList = new ArrayList<>();
        for (ProjectDto dto : projectDTOList) {
            ProjectEntity entity = convertDtoToEntity(dto);
            projectEntityList.add(entity);
        }

        return projectEntityList;
    }

    //--------------- Convert a List of Entity to a List of Dto ---------------
    
    public static List<ProjectDto> convertToDtoList(List<ProjectEntity> projectEntityList) {
        if (projectEntityList == null) {
            return null;
        }

        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (ProjectEntity entity : projectEntityList) {
            ProjectDto dto = convertEntityToDto(entity);
            projectDtoList.add(dto);
        }
        
        return projectDtoList;
    }
}