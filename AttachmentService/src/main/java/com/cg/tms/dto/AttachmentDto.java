package com.cg.tms.dto;

import com.cg.tms.entity.AttachmentEntity;

public class AttachmentDto {

    private Integer attachmentId;
    private Integer taskId;  // Task ID as reference
    private String fileName;
    private String filePath;

    // Getters and Setters
    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public AttachmentDto(int taskId, String filePath, String fileName) {
        this.taskId = taskId;
        this.filePath = filePath;
        this.fileName = fileName;
    }

	public AttachmentDto(AttachmentEntity savedAttachment) {
		// TODO Auto-generated constructor stub
	}

	public AttachmentDto() {
		// TODO Auto-generated constructor stub
	}

}