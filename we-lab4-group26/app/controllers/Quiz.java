package controllers;

import highscore.at.ac.tuwien.big.we.highscore.Failure;
import highscore.at.ac.tuwien.big.we.highscore.PublishHighScoreEndpoint;
import highscore.at.ac.tuwien.big.we.highscore.PublishHighScoreService;
import highscore.at.ac.tuwien.big.we.highscore.data.HighScoreRequestType;
import highscore.generated.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import models.Category;
import models.Choice;
import models.Question;
import models.QuizDAO;
import models.QuizGame;
import models.QuizUser;
import play.Logger;
import play.Play;
import play.api.Application;
import play.api.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.Option;
import twitter.TwitterClient;
import twitter.TwitterStatusMessage;
import views.html.quiz.index;
import views.html.quiz.quiz;
import views.html.quiz.quizover;
import views.html.quiz.roundover;

@Security.Authenticated(Secured.class)
public class Quiz extends Controller {

	public static Result index() {
		return ok(index.render());
	}

	@play.db.jpa.Transactional(readOnly = true)
	public static Result newGame() {
		createNewGame();
		return redirect(routes.Quiz.question());
	}

	@play.db.jpa.Transactional(readOnly = true)
	private static QuizGame createNewGame() {
		List<Category> allCategories = QuizDAO.INSTANCE.findEntities(Category.class);
		
		QuizUser human = user();
		
		QuizGame game = null;
		
		if(human != null)
		{
			Logger.info("Start game with " + allCategories.size() + " categories and User: " + human.getUserName());
			game = new QuizGame(allCategories, human);
		}else
		{
			Logger.info("Start game with " + allCategories.size() + " categories");
			game = new QuizGame(allCategories);
		}
		
		//Generate computer
		QuizUser computer = game.getPlayers().get(1);
		computer.setFirstName("Wolfram");
		computer.setLastName("Alpha");
		computer.setGender(QuizUser.Gender.female);
		computer.setPassword("");
		computer.setUserName("computer"); 
		computer.setBirthDate(new Date(2000, 01, 01));
		

		game.startNewRound();
		cacheGame(game);
		return game;
	}

	private static String dataFilePath() {
		return Play.application().configuration().getString("questions.filePath");
	}

	private static QuizUser user() {
		String userId = Secured.getAuthentication(session());
		return QuizDAO.INSTANCE.findById(Long.valueOf(userId));
	}

	@play.db.jpa.Transactional(readOnly = true)
	public static Result question() {
		QuizGame game = cachedGame();
		if (currentQuestion(game) != null) {
			return ok(quiz.render(game));
		} else {
			return badRequest(Messages.get("quiz.no-current-question"));
		}
	}

	@Transactional(readOnly = true)
	private static Question currentQuestion(QuizGame game) {
		if (game != null && game.getCurrentRound() != null) {
			QuizUser user = game.getPlayers().get(0);
			return game.getCurrentRound().getCurrentQuestion(user);
		} else {
			return null;
		}
	}

	@play.db.jpa.Transactional(readOnly = true)
	public static Result addAnswer() {
		QuizGame game = cachedGame();
		Question question = currentQuestion(game);
		if (question != null) {
			processAnswerIfSent(game);
			return redirectAccordingToGameState(game);
		} else {
			return badRequest(Messages.get("quiz.no-current-question"));
		}
	}

	@Transactional
	private static void processAnswerIfSent(QuizGame game) {
		DynamicForm form = Form.form().bindFromRequest();
		QuizUser user = game.getPlayers().get(0);
		Question question = game.getCurrentRound().getCurrentQuestion(user);
		int sentQuestionId = Integer.valueOf(form.data().get("questionid"));
		if (question.getId() == sentQuestionId) {
			List<Choice> choices = obtainSelectedChoices(form, question);
			long time = Long.valueOf(form.get("timeleft"));
			game.answerCurrentQuestion(user, choices, time);
		}
	}

	@Transactional
	private static List<Choice> obtainSelectedChoices(DynamicForm form,
			Question question) {
		Map<String, String> formData = form.data();
		List<Choice> choices = new ArrayList<Choice>();
		int i = 0;
		String chosenId = null;
		while ((chosenId = formData.get("choices[" + i + "]")) != null) {
			Choice choice = getChoiceById(Integer.valueOf(chosenId), question);
			if (choice != null) {
				choices.add(choice);
			}
			i++;
		}
		return choices;
	}

	private static Choice getChoiceById(int id, Question question) {
		for (Choice choice : question.getChoices())
			if (id == choice.getId())
				return choice;
		return null;
	}

	private static Result redirectAccordingToGameState(QuizGame game) {
		if (isRoundOver(game)) {
			return redirect(routes.Quiz.roundResult());
		} else if (isGameOver(game)) {
			return redirect(routes.Quiz.endResult());
		} else {
			return redirect(routes.Quiz.question());
		}
	}

	private static boolean isGameOver(QuizGame game) {
		return game.isRoundOver() && game.isGameOver();
	}

	private static boolean isRoundOver(QuizGame game) {
		return game.isRoundOver() && !game.isGameOver();
	}

	private static void cacheGame(QuizGame game) {
		Cache.set(gameId(), game, 3600, application());
	}

	@play.db.jpa.Transactional(readOnly = true)
	public static Result roundResult() {
		QuizGame game = cachedGame();
		if (game != null && isRoundOver(game)) {
			return ok(roundover.render(game));
		} else {
			return badRequest(Messages.get("quiz.no-round-result"));
		}
	}

	@play.db.jpa.Transactional(readOnly = true)
	public static Result endResult() {
		QuizGame game = cachedGame();
		if (game != null && isGameOver(game)) {
			
			publishHighscore();
			return ok(quizover.render(game));
		} else {
			return badRequest(Messages.get("quiz.no-end-result"));
		}
	}
	
	@play.db.jpa.Transactional(readOnly = true)
	public static Result newRound() {
		QuizGame game = cachedGame();
		if (game != null && isRoundOver(game)) {
			game.startNewRound();
			return redirect(routes.Quiz.question());
		} else {
			return badRequest(Messages.get("quiz.no-round-ended"));
		}
	}

	private static QuizGame cachedGame() {
		Option<Object> option = Cache.get(gameId(), application());
		if (option.isDefined() && option.get() instanceof QuizGame) {
			return (QuizGame) option.get();
		} else {
			return createNewGame();
		}
	}

	private static String gameId() {
		return "game." + uuid();
	}

	private static String uuid() {
		String uuid = session("uuid");
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
			session("uuid", uuid);
		}
		return uuid;
	}

	private static Application application() {
		return Play.application().getWrappedApplication();
	}
	
	private static void publishHighscore()
	{
		//Get userdata from game
		QuizGame game = cachedGame();
				
		QuizUser quizUser = game.getPlayers().get(0);
		QuizUser computerUser = game.getPlayers().get(1);
		
		//set user data
		highscore.generated.ObjectFactory gameFactory = new highscore.generated.ObjectFactory();
		highscore.generated.User user = gameFactory.createUser();
		
		user.setFirstname(quizUser.getFirstName());
		user.setLastname(quizUser.getLastName());
		
		if(quizUser.getGender() != null)
		{
			user.setGender(Gender.fromValue(quizUser.getGender().name()));
		}
		user.setPassword("");
		
		XMLGregorianCalendar birthdate = null;
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			if(quizUser.getBirthDate() != null)
				calendar.setTime(quizUser.getBirthDate());
			birthdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e1) {
			play.Logger.error(e1.toString());
		}
		user.setBirthdate(birthdate);
		
		//set computer data
		highscore.generated.User computer = gameFactory.createUser();
		computer.setFirstname(computerUser.getFirstName());
		computer.setLastname(computerUser.getLastName());
		computer.setGender(Gender.fromValue(computerUser.getGender().name()));
		computer.setPassword("");
		
		XMLGregorianCalendar birthdateComputer = null;
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			if(computerUser.getBirthDate() != null)
				calendar.setTime(computerUser.getBirthDate());
			birthdateComputer = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e1) {
			play.Logger.error(e1.toString());
		}
		computer.setBirthdate(birthdateComputer);
		
		if(game.getWinner().equals(user))
		{
			user.setName("winner");
			computer.setName("loser");
		}else
		{
			user.setName("loser");
			computer.setName("winner");
		}
		
		highscore.generated.Users users = gameFactory.createUsers();
		users.getUser().add(user);
		users.getUser().add(computer);
		
		highscore.generated.Quiz quiz = gameFactory.createQuiz();
		quiz.setUsers(users);
		
		// create requestType
		highscore.at.ac.tuwien.big.we.highscore.data.ObjectFactory highscoreDataFactory = new highscore.at.ac.tuwien.big.we.highscore.data.ObjectFactory();
		HighScoreRequestType requestType = highscoreDataFactory.createHighScoreRequestType();
		requestType.setQuiz(quiz);
		requestType.setUserKey("rkf4394dwqp49x");
		
		// send request to HighScoreService
        String uuid = null;
		try
		{
			PublishHighScoreService service = new PublishHighScoreService();
			PublishHighScoreEndpoint endpoint = service.getPublishHighScorePort();

            uuid = endpoint.publishHighScore(requestType);
			play.Logger.info(uuid);
		}catch (Failure e)
		{
			play.Logger.error(e.toString());
		}catch (Exception e)
		{
			play.Logger.error(e.toString());
		}
        if(uuid != null){
        	TwitterStatusMessage message = new TwitterStatusMessage(game.getWinner().getUserName(), uuid, new Date());
            TwitterClient client = new TwitterClient();
            try {
                client.publishUuid(message);
                play.Logger.info("Es wurde auf Twitter gepostet. uuid: " + uuid);
            } catch (Exception e) {
                play.Logger.error(e.toString());
            }
        }
	}

}
