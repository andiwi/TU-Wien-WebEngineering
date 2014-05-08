package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import at.ac.tuwien.big.we14.lab2.api.*;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

@Entity
public class Member implements User{ 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firstName;
	private String lastName;
	private String birthDate;
	private String gender;
	private String userName;
	private String password;
	
	
	public Member(String firstName, String lastName, String birthDate,
			String gender, String userName, String password) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setBirthDate(birthDate);
		this.setGender(gender);
		this.setUserName(userName);
		this.setPassword(password);
	}
	
	public Member(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public List<ValidationError> validate() {
		List<ValidationError> errors = null;
		errors = new ArrayList<ValidationError>();
		if(birthDateError())
			errors.add(new ValidationError("birthDate", birthDate + "Verwenden Sie bitte folgendes Datumsformat: dd.mm.yyyy (z.B. 24.12.2012)."));
		if(userNameError("length"))
			errors.add(new ValidationError("userNameLength", "Der Benutzername muss mindestens 4 Zeichen und darf maximal 8 Zeichen enthalten."));
		if(userNameError("used"))
			errors.add(new ValidationError("userNameUsed", "Der Benutzername ist leider bereits vorhanden"));
		if(passwordError())
			errors.add(new ValidationError("password", "Das Passwort muss mindestens 4 Zeichen und darf maximal 8 Zeichen enthalten."));
		return errors;
	}
	
	public boolean birthDateError(){
		if(birthDate == null || birthDate == "")
			return false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date testDate = null;
		try{
			testDate = sdf.parse(birthDate);
		} catch (ParseException e){
			return true;
	    }
		return false;
	}
	
	public boolean userNameError(String type){
		if(type == "length"){
			if(userName.length() < 4 || userName.length() > 8)
				return true;
			return false;
		} else{
	    	EntityManager em = play.db.jpa.JPA.em();
			String queryString = "SELECT u FROM Member u WHERE u.userName = :userName";
	    	TypedQuery<Member> query = em.createQuery(queryString, Member.class).setParameter("userName", userName);
	    	return (query.getResultList().size() > 0);
		}
	}
	
	public boolean passwordError(){
		if(password.length() < 4 || password.length() > 8)
			return true;
		return false;
	}
	
	public Member(){
		//Default Constructor
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return getUserName();
	}

	public void setName(String name) {
		setUserName(name);
	}
	
}