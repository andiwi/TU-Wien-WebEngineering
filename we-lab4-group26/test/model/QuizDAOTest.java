package model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Choice;
import models.Question;

import org.junit.Test;

import play.db.jpa.JPA;
import play.libs.F;

public class QuizDAOTest extends BaseTest{

	@Test
    public void testPersistCategories() {

        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
            	List<Question> questions = new ArrayList<Question>();
            	Question q = new Question();
            	q.setTextDE("textDE");
            	q.setTextEN("textEN");
            	
            	List<Choice> choices = new ArrayList<Choice>();
            	for (int i = 0; i < 3; i++)
            	{
	            	Choice choice = new Choice();
	            	choice.setCorrectAnswer(true);
	            	choice.setTextDE("textDE"+i);
	            	choice.setTextEN("textEN"+i);
	            	choice.setQuestion(questions.get(0));
            	}
            	
            	q.setChoices(choices);
            	            	
                for (int i = 0; i < 3; i++) {
                    Category c = new Category();
                    c.setNameDE("nameDE"+i);
                    c.setNameEN("nameEN"+i);
                    c.setQuestions(questions);
                   
                    quizDao.merge(c);
                }

                List<Category> categories = quizDao.findEntities(Category.class);
                assertTrue(categories.size() == 3);

            }
        });

    }
}
