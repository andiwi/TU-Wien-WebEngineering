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
package at.ac.tuwien.big.we14.lab2.api;

import java.util.List;

public interface Round {

	/**
	 * initializes the round
	 * 
	 * @param players
	 * @param questions
	 */
	public void initialize(List<User> players, List<Question> questions);

	/**
	 * returns the answer of a player to a specific question of this round
	 * 
	 * @param questionNumber
	 *            the number of the question (e.g. 2nd question,...)
	 * @param player
	 *            the player
	 * @return the specified answer or null if no answer exists to the given
	 *         parameters
	 */
	public Answer getAnswer(int questionNumber, User player);

	/**
	 * creates the answer to the current question
	 * 
	 * @param choice
	 *            the user's choices
	 * @param time
	 *            the time taken to answer this question
	 * @oaram player the player
	 * @param factory
	 *            the factory used to create the answer
	 */
	public void answerCurrentQuestion(List<Choice> choices, long time,
			User player, QuizFactory factory);

	/**
	 * returns the question identified by the given number
	 * 
	 * @param questionNumber
	 *            the number of the question (e.g. 2nd question,...)
	 * @return the question or null if the question number exceeds the number of
	 *         questions
	 */
	public Question getQuestion(int questionNumber);

	/**
	 * returns the current question for a specific user
	 * 
	 * @param user
	 *            the user
	 * @return the question or null if all questions have been answered
	 */
	public Question getCurrentQuestion(User user);

	/**
	 * 
	 * @return the winner of this round or null if no winner exists
	 */
	public User getRoundWinner();

	/**
	 * 
	 * @return true if all questions have been answered by all users
	 */
	public boolean areAllQuestionsAnswered();

	/**
	 * 
	 * @return all questions of this round
	 */
	public List<Question> getQuestions();

}
