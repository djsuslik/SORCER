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
	private Recipe espresso, mocha, macchiato, americano, espresso2;

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

	/**
	 * Test1 - try to add 4 coffees - first bug found
	 * */
	@Test
	public void testAddRecipe4Coffees() {
		assertTrue(coffeeMaker.addRecipe(mocha));
		assertTrue(coffeeMaker.addRecipe(macchiato));
		assertTrue(coffeeMaker.addRecipe(americano));
		//We shouldn't be able to add a fourth recipe
		assertFalse(coffeeMaker.addRecipe(espresso));

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");

	}

	/**
	 * Test2 - try to simply remove recipe
	 * */
	@Test
	public void testDeleteRecipe() {
		assertTrue(coffeeMaker.addRecipe(espresso));
		assertTrue(coffeeMaker.deleteRecipe(espresso));
		assertTrue(coffeeMaker.getRecipeForName(espresso.getName())==null);
	}

	/**
	 * Test3 - recipe with the same name as the one we are not changing, cannot be added
	 * */
	@Test
	public void testEditRecipe() {
		assertTrue(coffeeMaker.addRecipe(macchiato));
		assertTrue(coffeeMaker.addRecipe(espresso));
		assertFalse(coffeeMaker.editRecipe(espresso,macchiato));
	}
	/**
	 * Test4 - sugar should be positiva - not negative
	 * */
	@Test
	public void testAddInventory() throws ContextException {
		assertTrue(coffeeMaker.addInventory(0,0,1,0));
	}

	/**
	 * Test5 - NO ISSUES. Just a quick check that Inventory works as expected
	 * */
	@Test
	public void testCheckInventory() throws ContextException {
		assertEquals(coffeeMaker.checkInventory().getChocolate(), 15);
		assertEquals(coffeeMaker.checkInventory().getMilk(), 15);
		assertEquals(coffeeMaker.checkInventory().getCoffee(), 15);
		assertEquals(coffeeMaker.checkInventory().getSugar(), 15);
	}

	/**
	 * Test6 - coffee amount should decrease but *NOT* increase
	 * */
	@Test
	public void testMakeCoffee() throws ContextException {
		coffeeMaker.addRecipe(espresso);
		int amtCoffeeConsumed = espresso.getAmtCoffee();
		int amtCoffeeBefore = coffeeMaker.checkInventory().getCoffee();
		int expectedRest = amtCoffeeBefore-amtCoffeeConsumed;

		//purchasing coffee
		coffeeMaker.makeCoffee(espresso,120);
		int amtCoffeeAfter = coffeeMaker.checkInventory().getCoffee();
		assertEquals(expectedRest,amtCoffeeAfter);
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
	public void addRecipes() throws Exception {
		coffeeMaker.addRecipe(mocha);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");
	}

	@Test
	public void makeCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.makeCoffee(espresso, 200), 150);
	}

}

