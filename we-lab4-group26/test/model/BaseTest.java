package model;

import static org.junit.Assert.fail;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.libs.F;
import play.test.FakeApplication;
import models.QuizDAO;

public abstract class BaseTest {

	final protected QuizDAO quizDao = QuizDAO.INSTANCE;
	protected EntityManager em;
	
	
	/**
     * Setup the test
     */
    @Before
    public void setupTest() {
        FakeApplication app = fakeApplication();
        start(app);
        em = app.getWrappedApplication().plugin(JPAPlugin.class).get().em("default");
        JPA.bindForCurrentThread(em);


        emptyDatabase();


    }

    @After
    public void tearDownIntegrationTest() {
        JPA.bindForCurrentThread(null);
        em.close();
    }


    /**
     * Since we use a file-based database and not an in-memory database for this showcase we must
     * remove any old entries.
     */
    @Before
    public void emptyDatabase() {

        try  {
            JPA.withTransaction(new F.Callback0() {
                @Override
                public void invoke() throws Throwable {

                    em.getTransaction().begin();

                    Logger.info("Emptying database");
                    int count = em.createQuery("DELETE FROM Category").executeUpdate();
                    Logger.info("Deleted {} Categorys", count);

                    count = em.createQuery("DELETE FROM Choice").executeUpdate();
                    Logger.info("Deleted {} Choices", count);

                    count = em.createQuery("DELETE FROM Question").executeUpdate();
                    Logger.info("Deleted {} questions", count);

                    count = em.createQuery("DELETE FROM Quizuser").executeUpdate();
                    Logger.info("Deleted {} Quizusers", count);

                    em.getTransaction().commit();

                }
            });

        }
        catch (Exception e) {
            Logger.error("Test failed", e);
            fail();
        }

    }
}
