package data;

import java.util.List;

import models.Category;
import models.QuizDAO;


public class DBpediaDataInserter {
	
	public static void insertData(){
		List<Category> jsonCategories = getData();
		for(Category category : jsonCategories)
			QuizDAO.INSTANCE.persist(category);
	}
	
	private static List<Category> getData(){
		//TODO DBpediaDaten!
		return null;
	}
	
}
