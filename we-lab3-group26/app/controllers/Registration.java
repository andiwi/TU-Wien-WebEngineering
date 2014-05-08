package controllers;
import java.util.*;

import models.*;
import models.Member;

import javax.persistence.*;
import javax.transaction.Transactional;

/*import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;*/
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;


public class Registration extends Controller {
	
	private static Form<Member> signupForm = form(Member.class);
	
	public static Result index(){
		return ok(registration.render("start", signupForm));
	}
	
	@play.db.jpa.Transactional
    public static Result signup(){
    	EntityManager em = play.db.jpa.JPA.em();
    	
    	signupForm = Form.form(Member.class).bindFromRequest();

    	String userName = signupForm.data().get("userName");
    	String firstName = signupForm.data().get("firstName");
    	String lastName = signupForm.data().get("lastName");
    	String birthDate = signupForm.data().get("birthDate");
    	String gender = signupForm.data().get("gender");
    	String password = signupForm.data().get("password");
    	Member newUser = new Member(firstName, lastName, birthDate, gender, userName, password);
    	
    	if (signupForm.hasErrors())
    		return badRequest(registration.render("some error", signupForm));
    	
    	em.merge(newUser);
    	
    	return ok(authentication.render(signupForm.fill(newUser),null));
    }

}