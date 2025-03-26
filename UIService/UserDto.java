package com.cg.tms.dto;

public class UserDto {

	    private int userId;
	    private String userName;
	    private String fullName;  
	    private String email;     
	    private String password;  
	    //private String message;
	    //UserRoleDto roleId;

//	    public String getMessage() {
//			return message;
//		}

//		public UserRoleDto getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(UserRoleDto roleId) {
//			this.roleId = roleId;
//	}

//		public void setMessage(String message) {
//			this.message = message;
//		}

		public UserDto() {
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

		@Override
		public String toString() {
			return "UserDto [userId=" + userId + ", userName=" + userName + ", fullName=" + fullName + ", email="
					+ email + ", password=" + password + "]";
		}

	  
	}
