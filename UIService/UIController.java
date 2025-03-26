package com.cg.tms.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cg.tms.dto.AttachmentDto;
import com.cg.tms.dto.CommentDto;
import com.cg.tms.dto.TaskDto;
import com.cg.tms.dto.UserDto;
import com.cg.tms.service.AdminUIService;

import jakarta.servlet.http.HttpSession;

//http://localhost:8085/                  ----Home Page
//http://localhost:8085/register          ----Register page
//http://localhost:8085/login             ----Login Page

@Controller
@RequestMapping("/")
public class UIController {
	
    @Autowired
    private RestTemplate restTemplate; 
    @Autowired
    AdminUIService adminService;

    private final String USER_SERVICE_URL_REGISTER = "http://localhost:8080/api/users/register"; 
    private final String USER_SERVICE_URL_LOGIN = "http://localhost:8080/api/users/login"; 

//    private final String ATTACHMENT_SERVICE_URL = "http://localhost:8083/api/attachments/all";
    
    @GetMapping("/")
    public String homePage(Model model) {
        return "home"; 
    }
    
 // Register Page (GET request)
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register"; 
    }

    // Login Page (GET request)
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userDto", new UserDto()); 
        return "login";
    }
    
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
//        try {
//
//            ResponseEntity<String> response = restTemplate.postForEntity(USER_SERVICE_URL_REGISTER, userDto, String.class);
//            return response; 
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
//        }
//    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto userDto, Model model) {
        try {
        	ResponseEntity<UserDto> response = restTemplate.postForEntity(USER_SERVICE_URL_REGISTER, userDto, UserDto.class);

            return "redirect:/login"; // Redirect to login page after successful registration
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "login"; 
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username != null) {
            model.addAttribute("username", username);
            return "dashboard";  
        } else {

            return "redirect:/login";
        }
    }
    

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserDto userDto, Model model, HttpSession session) {
        try {

            session.setAttribute("username", userDto.getUserName());

            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            return "login";
        }
    }  
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/"; 
    }
//------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------ATTACHMENT---------------------------------------------------------------------------------------------------------------
//-------------------------------list ALL attachment--------------------------------------------------------------------------------------------------------------------	  

    @GetMapping("/listAllAttachment")
	public ModelAndView allAttachment() {
		Set<AttachmentDto> attachmentInfoSet=adminService.getAllAttachment();
		System.out.println("----------"+attachmentInfoSet);
		return new ModelAndView("listAllAttachment","attInfoObj",attachmentInfoSet);
	}
	    
	  
	  public UIController(AdminUIService adminService) {
	        this.adminService = adminService;
	    }

	  //-----------------------------add attachment
	  @GetMapping("/addAttachment")
	    public String addAttachmentPage(Model model) {
	        List<TaskDto> tasks = adminService.getAllTasks();
	        model.addAttribute("tasks", tasks); 
	        return "addAttachment"; 
	    }

	    @GetMapping("/listAllTasks")
	    public String listAllTasks(Model model) {
	        List<TaskDto> tasks = adminService.getAllTasks();
	        model.addAttribute("tasks", tasks); 
	        return "listAllTasks";
	    }

	    @PostMapping("/addAttachment")
	    public String addAttachment(@RequestParam("taskId") int taskId,
	                                @RequestParam("fileName") String fileName,
	                                @RequestParam("file") MultipartFile file, Model model) {

	        try {
	            String filePath = adminService.uploadFile(file);  

	            AttachmentDto attachmentDto = new AttachmentDto();
	            attachmentDto.setTaskId(taskId);  
	            attachmentDto.setFileName(fileName); 
	            attachmentDto.setFilePath(filePath);  

	            adminService.addAttachment(attachmentDto);

	            return "redirect:/listAllAttachment";

	        } catch (Exception e) {
	            model.addAttribute("error", "Error while adding attachment: " + e.getMessage());
	            return "redirect:/listAllAttachment";
	        }}

//-------------------update.---------------------------------------------------------------------------------------------------------	


	    
	    @GetMapping(value = "/update")
	    public ModelAndView showUpdatePage(@RequestParam("attachmentId") Integer attachmentId) {
	        AttachmentDto attachment = adminService.getAttachmentById(attachmentId);
	        return new ModelAndView("updateAttachment", "attachment", attachment);
	    }

	    @PostMapping(value = "/update/{attachmentId}")
	    public String updateAttachment(@PathVariable int attachmentId, 
	                                   @ModelAttribute AttachmentDto attachmentDto,
	                                   @RequestParam(value = "file", required = false) MultipartFile file,
	                                   @RequestParam("oldFilePath") String oldFilePath) throws IOException {
	        if (!file.isEmpty()) {
	            String newFilePath = adminService.uploadFile(file);  
	            attachmentDto.setFilePath(newFilePath);  
	            attachmentDto.setFileName(file.getOriginalFilename());  
	        } else {
	            attachmentDto.setFilePath(oldFilePath);
	        }

	        adminService.updateAttachment(attachmentId, attachmentDto);
	        return "redirect:/listAllAttachment";  
	    }

//----------------------------------------------------------------------------------------------------------------------------	  
//-------------------delete------------------------------------	
//----------------------------------------------------------------------------------------------------------------------------	  
	  @PostMapping(value="/deleteAttachment")
	    public String deleteAttachmentDetails(@RequestParam("attachmentId") int attachmentId) {
	        boolean result = adminService.deleteAttachment(attachmentId);
	        System.out.println("Attachment deleted");
	        return "redirect:/listAllAttachment";
	    }
	  
//-------------------get by id.---------------------------------------------------------------------------------------------------------	

	  @GetMapping("/viewAttachment")
	    public String viewAttachment(@RequestParam("searchId") String attachmentId, Model model) {
	        AttachmentDto attachment = adminService.getAttachmentById(Integer.parseInt(attachmentId));
	        model.addAttribute("attachment", attachment);
	        return "viewAttachment";
	    }

	  
//----------------------------------------------------------------------------------------------------------------------------------------
 //-------------------------COMMENTS---------------------------------------------------------------------------------------------------------------
	  
	  @GetMapping("/allComments")
	  public ModelAndView allComments() {
	    Set<CommentDto> myCommentSet = adminService.getAllComments();
	    System.out.println("----------" + myCommentSet);
	    return new ModelAndView("allComments", "commentInfoObj", myCommentSet);
	  }


	  @GetMapping("/addComment")
	  public ModelAndView showAddCommentForm() {
	      ModelAndView modelAndView = new ModelAndView("addComment");
	      modelAndView.addObject("commentDto", new CommentDto());
	      return modelAndView;
	  }

	  @PostMapping("/addComment")
	  public ModelAndView addComment(@ModelAttribute CommentDto commentDto) {
	      ModelAndView modelAndView = new ModelAndView();
	      try {
	          // Call service method to save the comment
	          adminService.addComment(commentDto);  
	          modelAndView.setViewName("redirect:/allComments");  
	      } catch (Exception e) {
	          modelAndView.setViewName("addComment");  
	          modelAndView.addObject("error", "An error occurred while adding the comment: " + e.getMessage());
	          e.printStackTrace();  
	      }
	      return modelAndView;
	  }

	  @GetMapping(value="/updateComment")
	  public ModelAndView showCommentUpdatePage(@RequestParam("commentId") Integer commentId) {
	      CommentDto comment = adminService.getCommentById(commentId);
	      return new ModelAndView("updateComment", "comment", comment);
	  }

	  @PostMapping(value="/updateComment/{commentId}")
	  public String updateComment(@PathVariable int commentId, @ModelAttribute CommentDto commentDto) {
	      adminService.updateComment(commentId, commentDto); 
	      return "redirect:/allComments"; 
	  }


	  @PostMapping(value="/deleteComment")
	  public String deleteComment(@RequestParam("commentId") int commentId) {
	      boolean result = adminService.deleteComment(commentId);
	      System.out.println("Comment deleted");
	      return "redirect:/allComments";
	  }


	  @GetMapping("/comments/get/{commentId}")
	  public String getCommentById(@PathVariable int commentId, Model model) {
	      CommentDto comment = adminService.getCommentById(commentId);
	      model.addAttribute("comment", comment);
	      return "viewComment";  
	  }  
	  
	  
}

