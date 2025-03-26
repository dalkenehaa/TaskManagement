package com.cg.tms.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cg.tms.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

//	List<ProjectEntity> findProjectsByStatus(String status);
	

//	List<ProjectEntity> findHighPriorityTasks();

//    List<ProjectEntity> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
//    List<ProjectEntity> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
	
	List<ProjectEntity> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

	@Query("SELECT p FROM ProjectEntity p WHERE p.startDate <= CURRENT_DATE AND p.endDate >= CURRENT_DATE")
	List<ProjectEntity> findOngoingProjects();

	List<ProjectEntity> findByUserId(int userId);
}
