package data;

import java.util.ArrayList;
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
		List <Category> categories = new ArrayList<Category>();
		for(int i = 4; i > 0; i--){
			categories.add(getCategory());
		}
		//TODO DBpediaDaten!
		return null;
	}
	
	private static Category getCategory(){
		Category category = new Category();
		//TODO DBpediaDaten!
		return category;
	}
	
}
