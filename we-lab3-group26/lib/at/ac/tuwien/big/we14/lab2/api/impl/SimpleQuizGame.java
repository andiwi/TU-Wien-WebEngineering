/**
 * <copyright>
 * 
 * Copyright (c) 2014 http://www.big.tuwien.ac.at All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * </copyright>
 */
package at.ac.tuwien.big.we14.lab2.api.impl;

import static java.lang.Math.random;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.User;

public class SimpleQuizGame implements QuizGame {

	private static int NUM_ROUNDS = 5;
	private static int NUM_QUESTIONS = 3;

	private QuizFactory factory;
	private List<Round> rounds;
	private QuestionDataProvider provider;

	private User human;
	private User computer;

	private List<Category> categories;

	/**
	 * list of already chosen categories during the game
	 */
	private List<Category> chosenCategories;

	public SimpleQuizGame() {
		factory = QuizFactory.INSTANCE;
		initializeQuizGame();
		initializePlayers();
	}

	public SimpleQuizGame(QuizFactory factory) {
		this.factory = factory;
		initializeQuizGame();
		initializePlayers();
	}

	/**
	 * Create a game with one {@code human} {@link User} against a computer
	 * user.
	 * 
	 * @param factory
	 *            factory to use.
	 * @param human
	 *            the human user.
	 */
	public SimpleQuizGame(QuizFactory factory, User human) {
		this.factory = factory;
		this.human = human;
		this.computer = createComputerPlayer();
		initializeQuizGame();
	}

	private void initializeQuizGame() {
		rounds = new ArrayList<Round>();
		provider = factory.createQuestionDataProvider();
		categories = provider.loadCategoryData();
		chosenCategories = new ArrayList<>();
	}

	private void initializePlayers() {
		human = createHumanPlayer();
		computer = createComputerPlayer();
	}

	private User createHumanPlayer() {
		User user = factory.createUser();
		user.setName("Spieler 1");
		return user;
	}

	private User createComputerPlayer() {
		User user = factory.createUser();
		user.setName("Spieler 2");
		return user;
	}

	@Override
	public Round getCurrentRound() {
		return rounds.get(rounds.size() - 1);
	}

	@Override
	public int getCurrentRoundCount() {
		return rounds.size();
	}

	@Override
	public void startNewRound() {
		Category category = chooseCategory();
		List<Question> questions = chooseQuestions(category);
		Round round = factory.createRound();
		round.initialize(getPlayers(), questions);
		rounds.add(round);
	}

	private Category chooseCategory() {
		List<Category> availableCategories = getAvailableCategories();
		int randomCategoryIndex = (int) round(random()
				* maxIndex(availableCategories));
		Category category = availableCategories.get(randomCategoryIndex);
		chosenCategories.add(category);
		return category;
	}

	private List<Category> getAvailableCategories() {
		if (chosenCategories.size() == categories.size())
			chosenCategories.clear();
		List<Category> availableCategories = new ArrayList<Category>();
		for (Category category : categories) {
			if (!chosenCategories.contains(category)) {
				availableCategories.add(category);
			}
		}
		return availableCategories;
	}

	private int maxIndex(List<?> list) {
		return list.size() - 1;
	}

	private List<Question> chooseQuestions(Category category) {
		List<Question> questions = new ArrayList<>();
		List<Question> availableQuestions = new ArrayList<>(
				category.getQuestions());
		for (int i = 0; i < Math.min(NUM_QUESTIONS, availableQuestions.size()); i++) {
			int randomQuestionIndex = (int) round(random()
					* maxIndex(availableQuestions));
			Question question = availableQuestions.get(randomQuestionIndex);
			questions.add(question);
			availableQuestions.remove(question);
		}
		return questions;
	}

	public void answerCurrentQuestion(User player, List<Choice> answers,
			long time) {
		getCurrentRound().answerCurrentQuestion(answers, time, player, factory);
		if (player == human)
			doAutomaticAnswerOfComputer();
	}

	private void doAutomaticAnswerOfComputer() {
		Round round = getCurrentRound();
		Question question = round.getCurrentQuestion(computer);
		long time = (long) random() * question.getMaxTime();
		List<Choice> answers = chooseComputerAnswers(question);
		round.answerCurrentQuestion(answers, time, computer, factory);
	}

	private List<Choice> chooseComputerAnswers(Question question) {
		if (random() < 0.5) {
			return Collections.unmodifiableList(question.getCorrectChoices());
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isGameOver() {
		Round currentRound = getCurrentRound();
		return rounds.size() >= NUM_ROUNDS
				&& currentRound.areAllQuestionsAnswered();
	}

	@Override
	public boolean isRoundOver() {
		return getCurrentRound().areAllQuestionsAnswered();
	}

	@Override
	public List<User> getPlayers() {
		return Arrays.asList(new User[] { human, computer });
	}

	@Override
	public int getWonRounds(User player) {
		int counter = 0;
		for (Round round : rounds) {
			if (round.getRoundWinner() != null && round.getRoundWinner().equals(player)) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public User getWinner() {
		if (isGameOver()) {

			ArrayList<User> bestUsers = new ArrayList<>();
			int bestCount = 0;

			for (User player : getPlayers()) {
				int count = 0;
				for (Round round : rounds) {
					if (round.getRoundWinner().equals(player)) {
						count++;
					}
				}
				if (count > bestCount) {
					bestUsers.clear();
					bestUsers.add(player);
					bestCount = count;
				} else if (count == bestCount) {
					bestUsers.add(player);
				}
			}

			if (bestUsers.size() == 1) {
				return bestUsers.get(0);
			}

		}
		return null;
	}

}
