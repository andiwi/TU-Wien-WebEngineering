package model;

import javax.persistence.EntityManager;

import models.IQuizDAO;
import models.QuizDAO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;

public abstract class BaseTest {

	protected static FakeApplication app;
    protected static EntityManager em;
    protected static IQuizDAO quizDao;
	
	/**
     * Setup the test
     */
    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);

        em = app.getWrappedApplication().plugin(JPAPlugin.class).get().em("default");
        JPA.bindForCurrentThread(em);

        quizDao = QuizDAO.INSTANCE;
    }

    @AfterClass
    public static void stopApp() {
        quizDao = null;

        JPA.bindForCurrentThread(null);
        em.close();
        em = null;

        Helpers.stop(app);
    }

    @Before
    public void setUp() {
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        em.getTransaction().rollback();
    }
}
