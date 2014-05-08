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

public interface Answer {

	/**
	 * inidcates if the answer was correct or not
	 * 
	 * @return true if the answer was correct
	 */
	public boolean isCorrect();

	/**
	 * sets the time the user needed to give the answer
	 * 
	 * @param time
	 *            the time to set in seconds
	 */
	public void setTime(long time);

	/**
	 * returns the time the user needed to give the answer
	 * 
	 * @return the time in seconds
	 */
	public long getTime();

	/**
	 * sets the user
	 * 
	 * @param user
	 *            the user
	 */
	public void setPlayer(User user);

	/**
	 * returns the user
	 * 
	 * @return the user
	 */
	public User getPlayer();

	/**
	 * sets the choices of the user
	 * 
	 * @param choices
	 *            the choices
	 */
	public void setChoices(List<Choice> choices);

	/**
	 * returns the choices of the user
	 * 
	 * @return the choices
	 */
	public List<Choice> getChoices();

	/**
	 * sets the corresponding round
	 * 
	 * @param round
	 *            the round
	 */
	public void setRound(Round round);

	/**
	 * 
	 * @return the corresponding round
	 */
	public Round getRound();

	/**
	 * sets the corresponding question
	 * 
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(Question question);

	/**
	 * returns the corresponding question
	 * 
	 * @return the question
	 */
	public Question getQuestion();
}
