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

/**
 * Represents a quiz game
 * 
 */
public interface QuizGame {

	/**
	 * 
	 * @return the current round
	 */
	public Round getCurrentRound();

	/**
	 * 
	 * @return the current round number
	 */
	public int getCurrentRoundCount();

	/**
	 * starts a new Round and initializes it properly
	 */
	public void startNewRound();

	/**
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver();

	/**
	 * 
	 * @return true if the round is over
	 */
	public boolean isRoundOver();

	/**
	 * 
	 * @return all players of this game
	 */
	public List<User> getPlayers();

	/**
	 * 
	 * @param player
	 *            the player
	 * @return the number of rounds the given player has won
	 */
	public int getWonRounds(User player);

	/**
	 * 
	 * @return the winner or null if no winner exists (yet)
	 */
	public User getWinner();

	/**
	 * Adds the {@code answers} of the specified {@code player} with the
	 * specified {@code time}.
	 * 
	 */
	public void answerCurrentQuestion(User player, List<Choice> answers,
			long time);
}
