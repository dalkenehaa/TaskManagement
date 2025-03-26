package com.cg.tms.dto;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
 
 
public class TaskDto {
 
	private int taskId;
	private String taskName;
	private String description;
	private LocalDateTime dueDate;
	private String priority;
	private String status;
	public TaskDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskDto(int taskId, String taskName, String description, LocalDateTime dueDate, String priority, String status) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TaskDto [taskId=" + taskId + ", taskName=" + taskName + ", description=" + description + ", dueDate="
				+ dueDate + ", priority=" + priority + ", status=" + status + "]";
	}
	
	
	
}