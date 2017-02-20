package com.hongclip.domain.users;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

	@NotEmpty @Size(min=4,max=12)
	private String userId;
	
	@NotEmpty @Size(min=4,max=12)
	private String password;
	
	@NotEmpty
	private String name;
	
	@Email
	private String email;

	public User(String userId, String password, String name, String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public User() {
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	public boolean matchPassword(Authenticate authenticate) {
		if(this.password == null){
			return false;
		}
		//return this.password.equals(authenticate.getPassword());
		return authenticate.matchPassword(this.password);
	}

	public boolean matchUserId(String session_userId) {
		if( session_userId == null) {
			return false;
		}
		System.out.println("현재 로그인 ID  :  "  + this.userId);
		System.out.println("INPUT ID  :  "  + session_userId);
		return this.userId.equals(session_userId);
	}

	public User update(User updateUser) {
		if(!matchUserId(updateUser.userId)){
			throw new IllegalArgumentException();
		}
		return new User(this.userId ,updateUser.password,updateUser.name,updateUser.email);
	}
}
