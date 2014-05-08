package at.ac.tuwien.big.we14.lab2.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.User;
import at.ac.tuwien.big.we14.lab2.api.impl.JSONQuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.impl.SimpleQuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.SimpleQuizGame;

import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class PlayQuizFactory extends SimpleQuizFactory {
	
	private String questionDataFilePath;
	private User humanUser;

	/**
	 * Creates a new PlayQuizFactory with the specified data input file for the
	 * questions (e.g., "conf/data.de.json" or 
	 * Play.application().configuration().getString("<specified-key-in-conf>")
	 * if the application configuration file is used) and the human user of the
	 * game. 
	 * @param questionDataFilePath file path to question data
	 * @param user Human user
	 */
	public PlayQuizFactory(String questionDataFilePath, User user) {
		this.questionDataFilePath = questionDataFilePath;
		this.humanUser = user;
	}

	@Override
	public QuizGame createQuizGame() {
		return new SimpleQuizGame(this, humanUser);
	}

	@Override
	public QuestionDataProvider createQuestionDataProvider() {
		try {
			FileInputStream inputStream = getQuestionDataInputStream();
			return new JSONQuestionDataProvider(inputStream, this);
		} catch (IOException e) {
			// fall back to empty list
			return new QuestionDataProvider() {
				@Override
				public List<Category> loadCategoryData() {
					return Collections.emptyList();
				}
			};
		}
	}

	private FileInputStream getQuestionDataInputStream() throws IOException {
		File file = new File(questionDataFilePath);
		InputSupplier<FileInputStream> inputStreamSupplier = Files
				.newInputStreamSupplier(file);
		FileInputStream inputStream = inputStreamSupplier.getInput();
		return inputStream;
	}

}
