package com.cg.tms.dto;

public class AttachmentDto {
		
				private int attachmentId;
				
				private String fileName;
				
				private String filePath;
				
				private int taskId;
				
				public AttachmentDto() {}

				public AttachmentDto(int attachmentId, String fileName, String filePath) {
					
					this.attachmentId = attachmentId;
					this.fileName = fileName;
					this.filePath = filePath;
					
				}

				public int getAttachmentId() {
					return attachmentId;
				}

				public void setAttachmentId(int attachmentId) {
					this.attachmentId = attachmentId;
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

				public int getTaskId() {
					return taskId;
				}

				public void setTaskId(int taskId) {
					this.taskId = taskId;
				
			}

				@Override
				public String toString() {
					return "AttachmentDto [attachmentId=" + attachmentId + ", fileName=" + fileName + ", filePath="
							+ filePath + ", taskId=" + taskId + "]";
				}
				

}
