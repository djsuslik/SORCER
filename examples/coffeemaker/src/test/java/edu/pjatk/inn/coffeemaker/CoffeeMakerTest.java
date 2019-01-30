package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.service.ContextException;
import sorcer.service.Exertion;

import static org.junit.Assert.*;
import static sorcer.eo.operator.*;

/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class CoffeeMakerTest {
	private final static Logger logger = LoggerFactory.getLogger(CoffeeMakerTest.class);

	private CoffeeMaker coffeeMaker;
	private Inventory inventory;
	private Recipe espresso, mocha, macchiato, americano;

	@Before
	public void setUp() throws ContextException {
		coffeeMaker = new CoffeeMaker();
		inventory = coffeeMaker.checkInventory();

		espresso = new Recipe();
		espresso.setName("espresso");
		espresso.setPrice(50);
		espresso.setAmtCoffee(6);
		espresso.setAmtMilk(1);
		espresso.setAmtSugar(1);
		espresso.setAmtChocolate(0);

		mocha = new Recipe();
		mocha.setName("mocha");
		mocha.setPrice(100);
		mocha.setAmtCoffee(8);
		mocha.setAmtMilk(1);
		mocha.setAmtSugar(1);
		mocha.setAmtChocolate(2);

		macchiato = new Recipe();
		macchiato.setName("macchiato");
		macchiato.setPrice(40);
		macchiato.setAmtCoffee(7);
		macchiato.setAmtMilk(1);
		macchiato.setAmtSugar(2);
		macchiato.setAmtChocolate(0);

		americano = new Recipe();
		americano.setName("americano");
		americano.setPrice(40);
		americano.setAmtCoffee(7);
		americano.setAmtMilk(1);
		americano.setAmtSugar(2);
		americano.setAmtChocolate(0);


	}

	@Test
	public void testAddRecipe() {
		assertTrue(coffeeMaker.addRecipe(espresso));
	}

	@Test
	public void testContextCofee() throws ContextException {
		assertTrue(espresso.getAmtCoffee() == 6);
	}

	@Test
	public void testContextMilk() throws ContextException {
		assertTrue(espresso.getAmtMilk() == 1);
	}

	@Test
	public void addRecepie() throws Exception {
		coffeeMaker.addRecipe(mocha);
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addContextRecepie() throws Exception {
		coffeeMaker.addRecipe(Recipe.getContext(mocha));
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addServiceRecepie() throws Exception {
		Exertion cmt = task(sig("addRecipe", coffeeMaker),
						context(types(Recipe.class), args(espresso),
							result("recipe/added")));

		logger.info("isAdded: " + eval(cmt));
		assertEquals(coffeeMaker.getRecipeForName("espresso").getName(), "espresso");
	}

	@Test
	public void testAddRecipes() throws Exception {
		coffeeMaker.addRecipe(mocha);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);
		//We shouldn't be able to add a fourth recipe
		assertFalse(coffeeMaker.addRecipe(espresso));

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");

	}

	@Test
	public void makeCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.makeCoffee(espresso, 200), 150);
	}

	@Test
	public void testDeleteRecipe() throws Exception {
		assertFalse(coffeeMaker.deleteRecipe(espresso));
		coffeeMaker.addRecipe(mocha);
		assertTrue(coffeeMaker.deleteRecipe(mocha));
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);
		Recipe[] allRecipes = coffeeMaker.getRecipes();
		assertFalse(coffeeMaker.deleteRecipe(allRecipes[0]));
	}

	@Test
	public void testEditRecipe() throws Exception {
		coffeeMaker.addRecipe(espresso);
		coffeeMaker.addRecipe(americano);
		coffeeMaker.addRecipe(mocha);
		//we should be able to edit recipe to be completely different than our current ones
		assertEquals(true, coffeeMaker.editRecipe(mocha, macchiato));
		//we shouldn't be able to edit recipe to have a name the same as another existing recipe
		assertFalse(coffeeMaker.editRecipe(mocha, espresso));

	}

	@Test
	public void testAddInventory() throws Exception {
		assertTrue(coffeeMaker.addInventory(0,0,0,0));
		assertFalse(coffeeMaker.addInventory(-1,0,0,0));
		assertFalse(coffeeMaker.addInventory(0,-1,0,0));
		assertFalse(coffeeMaker.addInventory(0,0,-1,0));
		assertFalse(coffeeMaker.addInventory(0,0,0,-1));
	}

	@Test
	public void testCheckInventory() throws Exception {
		assertEquals(coffeeMaker.checkInventory().getChocolate(), 15);
		assertEquals(coffeeMaker.checkInventory().getMilk(), 15);
		assertEquals(coffeeMaker.checkInventory().getCoffee(), 15);
		assertEquals(coffeeMaker.checkInventory().getSugar(), 15);
	}

	@Test
	public void testPurchaseBeverage() throws Exception {
		Recipe testRecipe = new Recipe();
		testRecipe.setName("test");
		testRecipe.setPrice(50);
		testRecipe.setAmtChocolate(15);
		testRecipe.setAmtCoffee(15);
		testRecipe.setAmtMilk(15);
		testRecipe.setAmtSugar(15);
		coffeeMaker.makeCoffee(testRecipe,50);
		//initially all are 15, after test recipe all should be 0
		assertEquals(coffeeMaker.checkInventory().getSugar(), 0);
		assertEquals(coffeeMaker.checkInventory().getCoffee(), 0);
		assertEquals(coffeeMaker.checkInventory().getChocolate(), 0);
		assertEquals(coffeeMaker.checkInventory().getMilk(),0);
		//extra change returned
		assertEquals(coffeeMaker.makeCoffee(americano, 50), 10);
		//return everything if not enough ingredients
		assertEquals(coffeeMaker.makeCoffee(testRecipe, 50), 50);

	}
}

