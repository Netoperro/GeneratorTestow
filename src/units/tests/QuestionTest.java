package units.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import units.classes.Question;
import units.enums.Type;

public class QuestionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldBeCorrect() {
		
		// setup a question
		Question q = new Question(Type.CLOSE, "what colour is grass?");
		q.AddAnswer("green", true);
		q.AddAnswer("red", false);
		q.AddAnswer("blue", false);
		q.AddAnswer("yellow", false);
		
		assertEquals('a', q.GetCorrectAnswer());
		
	}
	
	@Test
	public void shouldFail() {
		
		// setup a question
		Question q = new Question(Type.CLOSE, "which animal have wings?");
		q.AddAnswer("elephant", false);
		q.AddAnswer("fox", false);
		q.AddAnswer("bird", true);
		q.AddAnswer("hamster", false);
		
		assertNotEquals('b', q.GetCorrectAnswer());
		
	}
	
}