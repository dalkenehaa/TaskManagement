




package com.cg.tms.service;

import com.cg.tms.dto.AttachmentDto;
import com.cg.tms.entity.AttachmentEntity;
import com.cg.tms.repo.AttachmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    private static final String TASK_SERVICE_URL = "http://localhost:8082/api/tasks";  // URL of the Task Microservice

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private RestTemplate restTemplate;  // Used to communicate with Task microservice

    // Create operation: Add a new attachment
//    public AttachmentDto addAttachment(AttachmentDto attachmentDto) {
//        // Validate if task exists by making a REST call
//        String taskUrl = TASK_SERVICE_URL + "/" + attachmentDto.getTaskId();
//        String taskResponse = restTemplate.getForObject(taskUrl, String.class);  // Assuming response is a string
//        
//        if (taskResponse == null || taskResponse.isEmpty()) {
//            throw new RuntimeException("Task not found for ID: " + attachmentDto.getTaskId());
//        }
//
//        // Create and save the attachment
//        AttachmentEntity attachment = new AttachmentEntity();
//        attachment.setTaskId(attachmentDto.getTaskId());
//        attachment.setFileName(attachmentDto.getFileName());
//        attachment.setFilePath(attachmentDto.getFilePath());
//
//        attachment = attachmentRepository.save(attachment);
//
//        // Convert saved entity to DTO
//        AttachmentDto savedDto = new AttachmentDto();
//        savedDto.setAttachmentId(attachment.getAttachmentId());
//        savedDto.setTaskId(attachment.getTaskId());
//        savedDto.setFileName(attachment.getFileName());
//        savedDto.setFilePath(attachment.getFilePath());
//
//        return savedDto;
//    }
    public AttachmentEntity addAttachment(AttachmentDto attachmentDto) {
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setTaskId(attachmentDto.getTaskId());
        attachment.setFileName(attachmentDto.getFileName());
        attachment.setFilePath(attachmentDto.getFilePath());

        return attachmentRepository.save(attachment);
    }

    // Read operation: Get all attachments
    public List<AttachmentDto> getAllAttachments() {
        List<AttachmentEntity> attachments = attachmentRepository.findAll();
        return attachments.stream()
                .map(attachment -> {
                    AttachmentDto dto = new AttachmentDto();
                    dto.setAttachmentId(attachment.getAttachmentId());
                    dto.setTaskId(attachment.getTaskId());
                    dto.setFileName(attachment.getFileName());
                    dto.setFilePath(attachment.getFilePath());
                    return dto;
                }).toList();
    }

    // Read operation: Get an attachment by ID
    public AttachmentDto getAttachmentById(Integer attachmentId) {
        Optional<AttachmentEntity> attachmentOpt = attachmentRepository.findById(attachmentId);
        if (attachmentOpt.isEmpty()) {
            throw new RuntimeException("Attachment not found for ID: " + attachmentId);
        }
        AttachmentEntity attachment = attachmentOpt.get();
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setAttachmentId(attachment.getAttachmentId());
        attachmentDto.setTaskId(attachment.getTaskId());
        attachmentDto.setFileName(attachment.getFileName());
        attachmentDto.setFilePath(attachment.getFilePath());
        return attachmentDto;
    }

    // Update operation: Update an existing attachment
    public AttachmentDto updateAttachment(Integer attachmentId, AttachmentDto attachmentDto) {
        Optional<AttachmentEntity> attachmentOpt = attachmentRepository.findById(attachmentId);
        if (attachmentOpt.isEmpty()) {
            throw new RuntimeException("Attachment not found for ID: " + attachmentId);
        }

        // Validate if the task exists
        String taskUrl = TASK_SERVICE_URL + "/" + attachmentDto.getTaskId();
        String taskResponse = restTemplate.getForObject(taskUrl, String.class);
        if (taskResponse == null || taskResponse.isEmpty()) {
            throw new RuntimeException("Task not found for ID: " + attachmentDto.getTaskId());
        }

        // Update attachment details
        AttachmentEntity attachment = attachmentOpt.get();
        attachment.setFileName(attachmentDto.getFileName());
        attachment.setFilePath(attachmentDto.getFilePath());
        attachment.setTaskId(attachmentDto.getTaskId());

        attachment = attachmentRepository.save(attachment);

        // Convert saved entity to DTO
        AttachmentDto updatedDto = new AttachmentDto();
        updatedDto.setAttachmentId(attachment.getAttachmentId());
        updatedDto.setTaskId(attachment.getTaskId());
        updatedDto.setFileName(attachment.getFileName());
        updatedDto.setFilePath(attachment.getFilePath());

        return updatedDto;
    }

    // Delete operation: Delete an attachment
    public void deleteAttachment(Integer attachmentId) {
        Optional<AttachmentEntity> attachmentOpt = attachmentRepository.findById(attachmentId);
        if (attachmentOpt.isEmpty()) {
            throw new RuntimeException("Attachment not found for ID: " + attachmentId);
        }
        attachmentRepository.deleteById(attachmentId);
    }
}






















//package com.cg.tms.service;
//
//import com.cg.tms.dto.AttachmentDto;
//import com.cg.tms.exception.AttachmentNotFoundException;
//
//import java.util.List;
//import java.util.Set;
//
//public interface AttachmentService {
////    Set<AttachmentDto> getAllAttachment() throws AttachmentNotFoundException;
////    AttachmentDto getAttachmentById(int attachmentId) throws AttachmentNotFoundException;
////    AttachmentDto attachFile(AttachmentDto attachmentDto) throws AttachmentNotFoundException;
////    AttachmentDto updateAttachmentById(int attachmentId, AttachmentDto aDto) throws AttachmentNotFoundException;
////    boolean deleteAttachmentById(int attachmentId) throws AttachmentNotFoundException;
//
//    public void deleteAttachment(Integer attachmentId) ;
//    public AttachmentDto updateAttachment(Integer attachmentId, AttachmentDto attachmentDto) ;
//    public AttachmentDto getAttachmentById(Integer attachmentId) ;
//    public List<AttachmentDto> getAllAttachments() ;
//    public AttachmentDto addAttachment(AttachmentDto attachmentDto) ;
//}
