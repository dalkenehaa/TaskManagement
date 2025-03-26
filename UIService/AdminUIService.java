package com.cg.tms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.cg.tms.dto.AttachmentDto;
import com.cg.tms.dto.CommentDto;
import com.cg.tms.dto.TaskDto;
import com.cg.tms.dto.UserDto;

import jakarta.servlet.http.HttpSession;


@Service
public class AdminUIService {

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL_REGISTER = "http://localhost:8080/api/users/register"; 
    private final String USER_SERVICE_URL_LOGIN = "http://localhost:8080/api/users/login"; 
    private final String attachmentUrl = "http://localhost:8083/api/attachments/all";
//    private final String attachmentUrl = "http://ATTACHMENTSERVICE/api/attachments/all";

    // Register User
    public ResponseEntity<UserDto> registerUser(UserDto userDto) {
        try {
            return restTemplate.postForEntity(USER_SERVICE_URL_REGISTER, userDto, UserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    // Login User
    public ResponseEntity<UserDto> loginUser(UserDto userDto) {
        try {
            return restTemplate.postForEntity(USER_SERVICE_URL_LOGIN, userDto, UserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/profile")
    public String getUserProfile(Model model, HttpSession session) {
        // Retrieve the UserDto from the session
        UserDto userDto = (UserDto) session.getAttribute("userDto");

        // Check if the UserDto exists
        if (userDto != null) {
            model.addAttribute("user", userDto);  // Add the userDto to the model
        } else {
            // Handle the case when the userDto is not found (user might not be logged in)
            return "redirect:/login";  // Redirect to the login page if the user is not logged in
        }

        return "profile";  // Return the profile page
    }
  //------------------------------------------------------------------------------------------ 
  //------------------------------ATTACHMENT-----------------------------------------------------------------------------------
  //------------------------------ATTACHMENT-----------------------------------------------------------------------------------
      
    public Set<AttachmentDto> getAllAttachment() {
        ResponseEntity<Set<AttachmentDto>> response = restTemplate.exchange(
            attachmentUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Set<AttachmentDto>>() {}
        );

        Set<AttachmentDto> attachmentInfoSet = response.getBody();
        System.out.println("...................." + attachmentInfoSet);
        List<AttachmentDto> sortedList = new ArrayList<>(attachmentInfoSet);
        sortedList.sort(Comparator.comparingInt(AttachmentDto::getAttachmentId));
        Set<AttachmentDto> sortedSet = new LinkedHashSet<>(sortedList);

        return sortedSet;
    }
  //----------add------------------------------------------------------add---------------

    private final String taskServiceUrl = "http://localhost:8082/api/tasks/getall";
    private final String attachmentUrlForAdd = "http://localhost:8083/api/attachments/attachFile";

    public List<TaskDto> getAllTasks() {
        // Call the Task Microservice API to get all tasks
        TaskDto[] tasks = restTemplate.getForObject(taskServiceUrl, TaskDto[].class);
        return List.of(tasks); // Convert array to List
    }

    public String uploadFile(MultipartFile file) throws IOException {
        // Define the upload directory
        Path uploadDirectory = Paths.get("uploads");

        // Create directory if it doesn't exist
        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        // Get the original file name and prepare the path
        String fileName = file.getOriginalFilename();
        Path filePath = uploadDirectory.resolve(fileName);

        // Copy the file to the upload directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the file path for saving in the database
        return filePath.toString();
    }

    public void addAttachment(AttachmentDto attachmentDto) {
        // Send a POST request to the backend API to add an attachment
        String url = attachmentUrlForAdd;
        HttpEntity<AttachmentDto> entity = new HttpEntity<>(attachmentDto);
        
        // Use RestTemplate to send the POST request
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
    

    
    

//------------getById-------------------------------------------------------------------------------------------------
    // Get attachment by ID

    private final String attachmentUrlForGetById = "http://localhost:8083/api/attachments/{attachmentId}";

    public AttachmentDto getAttachmentById(int attachmentId) {
        try {
            ResponseEntity<AttachmentDto> attRes = restTemplate.exchange(attachmentUrlForGetById, HttpMethod.GET, null,
                    AttachmentDto.class, attachmentId);
            return attRes.getBody();
        } catch (Exception e) {
            System.out.println("Error fetching attachment by ID: " + e.getMessage());
            return null;
        }
    }

    
    //------------update-------------------------------------------------------------------------------------------------------------

    private final String attachmentUrlForUpdate = "http://localhost:8083/api/attachments/update/{attachmentId}";

    public AttachmentDto updateAttachment(int attachmentId, AttachmentDto attachmentDto) {
        try {
            // Send the updated attachment DTO to the backend API for updating
            HttpEntity<AttachmentDto> requestEntity = new HttpEntity<>(attachmentDto);
            ResponseEntity<AttachmentDto> updateRes = restTemplate.exchange(attachmentUrlForUpdate, HttpMethod.PUT,
                    requestEntity, AttachmentDto.class, attachmentId);
            return updateRes.getBody();
        } catch (Exception e) {
            System.out.println("Error updating attachment: " + e.getMessage());
            return null;
        }
    }

//    public String uploadFile(MultipartFile file) throws IOException {
//        // Define the upload directory
//        Path uploadDirectory = Paths.get("uploads");
//
//        // Create directory if it doesn't exist
//        if (!Files.exists(uploadDirectory)) {
//            Files.createDirectories(uploadDirectory);
//        }
//
//        // Get the original file name and prepare the path
//        String fileName = file.getOriginalFilename();
//        Path filePath = uploadDirectory.resolve(fileName);
//
//        // Copy the file to the upload directory
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        // Return the file path for saving in the database
//        return filePath.toString();
//    }

    
//--------------delete- attachment--------------------------------------------------------------
    
    private final String attachmentUrlForDelete = "http://localhost:8083/api/attachments/delete/{attachmentId}";

    public boolean deleteAttachment(int attachmentId) {
        String url = UriComponentsBuilder.fromUriString(attachmentUrlForDelete).buildAndExpand(attachmentId).toUriString();
        ResponseEntity<String> deleteAttachment = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        String responseBody = deleteAttachment.getBody();
        boolean result = "Success".equalsIgnoreCase(responseBody);
        return result;
    }


    
 //------------------------------------------------------------------------------------------ 
//------------------------------comment-----------------------------------------------------------------------------------
//------------------------------comment-----------------------------------------------------------------------------------
    
    private final String commentsBaseUrl = "http://localhost:8082/api/comments/";

  //----------gte all comment-----------------------------------------
   
   public Set<CommentDto> getAllComments() {
  	    String url = "http://localhost:8082/api/comments/comments";
  	    ResponseEntity<Set<CommentDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Set<CommentDto>>() {});
  	    Set<CommentDto> comments = responseEntity.getBody();
  	    List<CommentDto> sortedList = new ArrayList<>(comments);
  	    sortedList.sort(Comparator.comparingInt(CommentDto::getCommentId));
  	  Set<CommentDto> sortedSet = new LinkedHashSet<>(sortedList);
  	  return sortedSet;
  	}

  //----add comment--------------------------------------------
   private final String commentUrlForAdding = "http://localhost:8082/api/comments/addComment";

   public ResponseEntity<CommentDto> addComment(CommentDto commentDto) {
       HttpEntity<CommentDto> entity = new HttpEntity<>(commentDto);
       return restTemplate.exchange(commentUrlForAdding, HttpMethod.POST, entity, CommentDto.class);
   }


   private final String  commentUrlForGetById = "http://localhost:8082/api/comments/{commentId}";

   public CommentDto getCommentById(int commentId) {
       try {
           ResponseEntity<CommentDto> commentRes = restTemplate.exchange(commentUrlForGetById, HttpMethod.GET, null,
                   CommentDto.class, commentId);
           return commentRes.getBody();
       } catch (Exception e) {
           System.out.println("Error fetching comment by ID: " + e.getMessage());
           return null;
       }
   }
  //------------------update comment------------
   private final String commentUrlForUpdates = "http://localhost:8082/api/comments/update/{commentId}";

   public CommentDto updateComment(int commentId, CommentDto commentDto) {
       try {
           HttpEntity<CommentDto> commentRequestEntity = new HttpEntity<>(commentDto);
           ResponseEntity<CommentDto> updatedComment = restTemplate.exchange(commentUrlForUpdates, HttpMethod.PUT,
                   commentRequestEntity, CommentDto.class, commentId);
           return updatedComment.getBody();
       } catch (Exception e) {
           System.out.println("Error updating comment: " + e.getMessage());
           return null;
       }
   }
   //----------delete comment---------------
   
   private final String commentUrlForDelete = "http://localhost:8082/api/comments/delete/{commentId}";

   public boolean deleteComment(int commentId) {
       String url = UriComponentsBuilder.fromUriString(commentUrlForDelete).buildAndExpand(commentId).toUriString();
       ResponseEntity<String> deleteComment = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
       String responseBody = deleteComment.getBody();
       boolean result = "Success".equalsIgnoreCase(responseBody);
       return result;
   }
    
    
}   
    
    
    
    
    
    
    
    
    
    
