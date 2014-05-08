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

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.Answer;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.User;

public class SimpleAnswer implements Answer {
	
	private long time;
	
	private User user;
	
	private List<Choice> choices;
	
	private Round round;
	
	private Question question;
	
	public SimpleAnswer() {
		choices = new ArrayList<>();
	}
	
	@Override
	public boolean isCorrect() {
		if(!choices.isEmpty()){
			List<Choice> correctChoices = question.getCorrectChoices();
			if(choices.size() == correctChoices.size()){
				return correctChoices.containsAll(choices);
			}
		}
		return false;
	}

	@Override
	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public void setPlayer(User user) {
		this.user = user;
	}

	@Override
	public User getPlayer() {
		return user;
	}

	@Override
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	@Override
	public List<Choice> getChoices() {
		return choices;
	}

	@Override
	public void setRound(Round round) {
		this.round = round;
	}

	@Override
	public Round getRound() {
		return round;
	}

	@Override
	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public Question getQuestion() {
		return question;
	}

}
