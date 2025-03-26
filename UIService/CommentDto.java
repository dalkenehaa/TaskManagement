package com.cg.tms.dto;

import java.time.LocalDate;


public class CommentDto {

	private int taskId;
//	private TaskDto taskId;
	private int userId;
	private int commentId;
	private String text;
	private LocalDate createdAt;
	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentDto(int taskId, int userId, int commentId, String text, LocalDate createdAt) {
//	public CommentDto(TaskDto taskId, int userId, int commentId, String text, LocalDate createdAt) {
	
	super();
		this.taskId = taskId;
		this.userId = userId;
		this.commentId = commentId;
		this.text = text;
		this.createdAt = createdAt;
	}
	public int getTaskId() {
//		public TaskDto getTaskId() {

		return taskId;
	}
	public void setTaskId(int taskId) {
//		public void setTaskId(TaskDto taskId) {
		this.taskId = taskId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "CommentEntity [projectId=" + taskId + ", userId=" + userId + ", commentId=" + commentId + ", text="
				+ text + ", createdAt=" + createdAt + "]";
	}
	
	
}

