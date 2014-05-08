package controllers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.Member;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.impl.PlayQuizFactory;

public class Play extends Controller {

	private static QuizGame game;
	private static Member user;
	private static QuizFactory factory;
	private static Round round;
	private static Question question;
	private static int currentQuestionNr;
	
	//TODO das ganze in Sessions packen, evt. mit einem gamepool?
	
	public static Result startGame(Member member){ //neues Spiel mit neuem User
		user = member;
		factory = new PlayQuizFactory("conf/data.de.json", user);
		game = factory.createQuizGame();
		
		return newRound();
	}
		
	public static Result newRound(){
		currentQuestionNr = 0;
		game.startNewRound(); // start new game/round
		round = game.getCurrentRound();// current round
		return nextQuestion();
	}
		
	public static Result anwerQuestion(){
		if(currentQuestionNr >= 3)
			return finish();
		
		List<Choice> choices = game.getCurrentRound().getCurrentQuestion(user).getAllChoices();
		
		/*Map<String, String[]> answers = request().body().asFormUrlEncoded(); //Map mit value von angekreuzelten Antowrtcheckboxen
		for(Entry<String, String[]> entry : answers.entrySet()){
			//TODO fragen beantworten und in game setzen!
		}*/
		List<Choice> answeredChoices = choices; //TODO nur zum testen, beantwortet alle Fragen!
		
		round.answerCurrentQuestion(answeredChoices, 30, user, factory);	//TODO wie lange hat der User wirklcih gebraucht?
		
		return nextQuestion();
	}
	
	public static Result nextQuestion(){
		question = round.getCurrentQuestion(user);
		//TODO checken: wurde die Frage schon gespielt?
		currentQuestionNr++;
		
		return ok(quiz.render(question));
	}
	
	public static Result finish(){
		if(game.isGameOver())
			return ok(quizover.render());
		return ok(roundover.render());
	}
	
}