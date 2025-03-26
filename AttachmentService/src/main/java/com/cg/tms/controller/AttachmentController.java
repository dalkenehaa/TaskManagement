package com.cg.tms.controller;

import com.cg.tms.dto.AttachmentDto;
import com.cg.tms.entity.AttachmentEntity;
import com.cg.tms.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Create operation: Add a new attachment
//    @PostMapping("/add")
//    public ResponseEntity<AttachmentDto> addAttachment(@RequestBody AttachmentDto attachmentDto) {
//        try {
//            AttachmentDto savedAttachment = attachmentService.addAttachment(attachmentDto);
//            return new ResponseEntity<>(savedAttachment, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
    @PostMapping("/addAttachment")
    public ResponseEntity<AttachmentDto> addAttachment(@RequestBody AttachmentDto attachmentDto) {
        try {
            // Log the received attachment data
            System.out.println("Received attachment: " + attachmentDto);

            // Convert the DTO to entity and save it to the database
            AttachmentEntity attachmentEntity = new AttachmentEntity();
            attachmentEntity.setTaskId(attachmentDto.getTaskId());
            attachmentEntity.setFileName(attachmentDto.getFileName());
            attachmentEntity.setFilePath(attachmentDto.getFilePath());

            // Save to the database
            attachmentEntity = attachmentRepository.save(attachmentEntity);

            // Convert the saved entity back to DTO to return
            AttachmentDto savedDto = new AttachmentDto();
            savedDto.setAttachmentId(attachmentEntity.getAttachmentId());
            savedDto.setTaskId(attachmentEntity.getTaskId());
            savedDto.setFileName(attachmentEntity.getFileName());
            savedDto.setFilePath(attachmentEntity.getFilePath());

            // Return the saved DTO along with a CREATED status
            return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
            
        } catch (Exception e) {
            // Log the exception and return a BAD REQUEST status if something goes wrong
            System.out.println("Error while saving attachment: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // Read operation: Get all attachments
    @GetMapping("/list")  // Use a specific endpoint for listing all attachments
    public ResponseEntity<List<AttachmentDto>> getAllAttachments() {
        try {
            List<AttachmentDto> attachments = attachmentService.getAllAttachments();
            return new ResponseEntity<>(attachments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read operation: Get an attachment by ID
    @GetMapping("/{attachmentId}")  // Path parameter for getting a single attachment by ID
    public ResponseEntity<AttachmentDto> getAttachmentById(@PathVariable Integer attachmentId) {
        try {
            AttachmentDto attachment = attachmentService.getAttachmentById(attachmentId);
            return new ResponseEntity<>(attachment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update operation: Update an existing attachment
    @PutMapping("/update/{attachmentId}")
    public ResponseEntity<AttachmentDto> updateAttachment(
            @PathVariable Integer attachmentId,
            @RequestBody AttachmentDto attachmentDto) {
        try {
            AttachmentDto updatedAttachment = attachmentService.updateAttachment(attachmentId, attachmentDto);
            return new ResponseEntity<>(updatedAttachment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete operation: Delete an attachment
    @DeleteMapping("/delete/{attachmentId}")
    public ResponseEntity<HttpStatus> deleteAttachment(@PathVariable Integer attachmentId) {
        try {
            attachmentService.deleteAttachment(attachmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
