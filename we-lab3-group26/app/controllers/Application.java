package controllers;
import static play.data.Form.form;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Member;
import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;


public class Application extends Controller {
	
	private static Form<Member> loginForm = form(Member.class);
	
	public static Result index() {
        return ok(authentication.render(loginForm,null));
    }
	
	@play.db.jpa.Transactional
	public static Result login() {    	
		
		loginForm = Form.form(Member.class).bindFromRequest();
		
		String userName = loginForm.data().get("userName");
    	String password = loginForm.data().get("password");
    	
    	boolean loginsuccess = false;
    	EntityManager em = play.db.jpa.JPA.em();
		String queryString = "SELECT u FROM Member u WHERE u.userName = :userName";
    	TypedQuery<Member> query = em.createQuery(queryString, Member.class).setParameter("userName", userName);
    	List<Member> result = query.getResultList();
    	if(!result.isEmpty()){
    		if(result.get(0).getPassword().equals(password)){
    			Member user = result.get(0);
    			return Play.startGame(user);
    			//return ok(index.render()); //TODO zuerst auf Index Seite navigieren
    		}
    	}
    	    	
    	return badRequest(authentication.render(loginForm.fill(new Member(userName, password)),false));
		
	}

}
