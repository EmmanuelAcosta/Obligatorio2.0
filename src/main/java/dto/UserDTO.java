package dto;

import java.sql.Date;

public class UserDTO {
	private String id;
	private String name;
	private String lastName;
	private int phone;
	private String birthdate;
	private String location;
	private String email;
	
	public String getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getLastName() {
		return lastName;
	}
	public int getPhone() {
		return phone;
	}
	public String getBirthDate() {
		return birthdate;
	}
	public String getLocation() {
		return location;
	}
	public String getEmail() {
		return email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setID(String id) {
		this.id = id;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public void setBirthdate(String birth) {
		this.birthdate = birth;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserDTO() {}
	
}
