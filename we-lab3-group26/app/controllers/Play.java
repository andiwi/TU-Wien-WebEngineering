package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import models.Member;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.User;
import at.ac.tuwien.big.we14.lab2.api.impl.PlayQuizFactory;

public class Play extends Controller {

	private static QuizGame game;
	private static Member user;
	private static QuizFactory factory;
	private static Round round;
	private static Question question;
	private static int questionCounter;
	
	//TODO das ganze in Sessions packen, evt. mit einem gamepool?
	
	public static Result startGame(Member member){ //neues Spiel mit neuem User
		user = member;
		factory = new PlayQuizFactory("conf/data.de.json", user);
		game = factory.createQuizGame();
		questionCounter = 0;
		
		return newRound();
	}
		
	public static Result newRound(){
		game.startNewRound(); // start new game/round
		round = game.getCurrentRound();// current round
		return nextQuestion();
	}
	
	public static Result nextQuestion(){
		question = round.getCurrentQuestion(user);
		
		List<String> answersOverviewUser = new ArrayList<String>();
		int i = 0;
		while(i < questionCounter) //Hole die Antworten der schon gegebenen fragen
		{
			if(round.getAnswer(i, user).isCorrect())
				answersOverviewUser.add("correct");
			else
				answersOverviewUser.add("incorrect");
			i++;
		}
		while(i < 3) //Die Fragen die noch nicht beantwortet wurden sind unknown.
		{
			answersOverviewUser.add("unknown");
			i++;
		}
		
		List<String> answersOverviewComputer = new ArrayList<String>();
		i = 0;
		while(i < questionCounter) //Hole die Antworten der schon gegebenen fragen
		{
			if(round.getAnswer(i, user).isCorrect())
				answersOverviewComputer.add("correct");
			else
				answersOverviewComputer.add("incorrect");
			i++;
		}
		while(i < 3) //Die Fragen die noch nicht beantwortet wurden sind unknown.
		{
			answersOverviewComputer.add("unknown");
			i++;
		}
		
		questionCounter++;
		return ok(quiz.render(question, answersOverviewUser, answersOverviewComputer));
	}
		
	public static Result answerQuestion(){
		
		List<Choice> allChoices = game.getCurrentRound().getCurrentQuestion(user).getAllChoices();
		
		DynamicForm requestData = Form.form().bindFromRequest();
		

		List<Choice> chosenChoices = new ArrayList<Choice>();
		for(int i = 0; i < allChoices.size(); i++) //Speichere die ausgewählten Fragen ab.
		{
			String option = requestData.get("option" + i);
			if(option != null)
			{
				int id = Integer.parseInt(option);
				
				for(Choice c : allChoices)
				{
					if(c.getId() == id)
						chosenChoices.add(c);
				}
			}
		}
				
		int time = Integer.parseInt(requestData.get("timeleftvalue"));
		
		round.answerCurrentQuestion(chosenChoices, time, user, factory);
		
		if(game.isRoundOver() || questionCounter == 3)
			return finish();
		
		return nextQuestion();
	}
	
	
	
	public static Result finish(){
		//TODO implement me!
		if(game.isGameOver())
			return ok(quizover.render());
		return ok(roundover.render());
	}
	
}