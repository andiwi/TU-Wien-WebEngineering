package model;

import static org.junit.Assert.assertNotNull;
import models.Category;

import org.junit.Test;

public class QuizDAOTest extends BaseTest{

    @Test
    public void persistCategory() {
        Category c = new Category();
        c.setNameDE("Webentwicklung");
        c.setNameEN("Web-Engineering");
        quizDao.persist(c);
        em.flush();
        assertNotNull(c.getId());
    }
	
}
