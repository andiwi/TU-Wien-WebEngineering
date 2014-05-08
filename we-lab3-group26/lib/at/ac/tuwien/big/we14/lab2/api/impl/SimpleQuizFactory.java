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

import at.ac.tuwien.big.we14.lab2.api.Answer;
import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.User;

public abstract class SimpleQuizFactory extends QuizFactory {

	@Override
	public Category createCategory() {
		return new SimpleCategory();
	}

	@Override
	public Question createQuestion() {
		return new SimpleQuestion();
	}

	@Override
	public Choice createChoice() {
		return new SimpleChoice();
	}

	@Override
	public Answer createAnswer() {
		return new SimpleAnswer();
	}

	@Override
	public Round createRound() {
		return new SimpleRound();
	}

	@Override
	public QuizGame createQuizGame() {
		return new SimpleQuizGame(this);
	}
	
	@Override
	public User createUser() {
		return new SimpleUser();
	}

}
