package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;

/**
 * @author   Sarah & Mike
 */
public class Recipe implements Serializable {
	/**
	 * Recipe name
	 */
    private String name;
	/**
	 * Price of making one cup of coffee with this recipe
	 */
    private int price;
	/**
	 * Amount of coffee used in recipe
	 */
    private int amtCoffee;
	/**
	 * Amount of milk used in recipe
	 */
    private int amtMilk;
	/**
	 * Amount of sugar used in recipe
	 */
    private int amtSugar;
	/**
	 * Amount of chocolate used in recipe
	 */
    private int amtChocolate;


	/**
	 * Basic constructor. Creates empty recipe
	 */
    public Recipe() {
    	this.name = "";
    	this.price = 0;
    	this.amtCoffee = 0;
    	this.amtMilk = 0;
    	this.amtSugar = 0;
    	this.amtChocolate = 0;
    }
    
    /**
	 * Method for returning amount of chocolate in recipe
	 * @return   Returns the amtChocolate.
	 */
    public int getAmtChocolate() {
		return amtChocolate;
	}
    /**
	 * Method for assigning positive number to chocolate variable.
	 * Negative numbers will not be set
	 * @param amtChocolate   The amtChocolate to setValue.
	 */
    public void setAmtChocolate(int amtChocolate) {
		if (amtChocolate >= 0) {
			this.amtChocolate = amtChocolate;
		} 
	}
    /**
	 * Method for returning amount of coffee in recipe
	 * @return   Returns the amtCoffee.
	 */
    public int getAmtCoffee() {
		return amtCoffee;
	}

    /**
	 * Method for assigning positive number to coffee variable.
	 * Negative numbers will not be set
	 * @param amtCoffee   The amtCoffee to setValue.
	 */
    public void setAmtCoffee(int amtCoffee) {
		if (amtCoffee >= 0) {
			this.amtCoffee = amtCoffee;
		} 
	}
    /**
	 * Method for returning amount of milk in recipe
	 * @return   Returns the amtMilk.
	 */
    public int getAmtMilk() {
		return amtMilk;
	}
    /**
	 * Method for assigning positive number to milk variable.
	 * @param amtMilk   The amtMilk to setValue.
	 */
    public void setAmtMilk(int amtMilk) {
		if (amtMilk >= 0) {
			this.amtMilk = amtMilk;
		} 
	}
    /**
	 * Method for returning amount of sugar in recipe
	 * @return   Returns the amtSugar.
	 */
    public int getAmtSugar() {
		return amtSugar;
	}
    /**
	 * Method for assigning positive number to sugar variable.
	 * @param amtSugar   The amtSugar to setValue.
	 */
    public void setAmtSugar(int amtSugar) {
		if (amtSugar >= 0) {
			this.amtSugar = amtSugar;
		} 
	}
    /**
	 * Returns recipe name
	 * @return   Returns the name.
	 */
    public String getName() {
		return name;
	}
    /**
	 * Sets not null recipe name
	 * @param name   The name to setValue.
	 */
    public void setName(String name) {
    	if(name != null) {
    		this.name = name;
    	}
	}
    /**
	 * Returns recipe price
	 * @return   Returns the price.
	 */
    public int getPrice() {
		return price;
	}
    /**
	 * Sets new price
	 * @param price   The price to setValue.
	 */
    public void setPrice(int price) {
		if (price >= 0) {
			this.price = price;
		} 
	}
	/**
	 * Checks if recipes have the same name
	 * @param r Recipe to compare
	 * @return Return true if recipes' names are identical
	 * */
    public boolean equals(Recipe r) {
        if((this.name).equals(r.getName())) {
            return true;
        }
        return false;
    }
	/**
	 * Returns recipe's name
	 * @return Returns recipe's name
	 * */
    public String toString() {
    	return name;
    }
	/**
	 * Creating a recipe based on context and values inside
	 * @param context Incoming context
	 * @return Returns newly created recipe based on context content
	 * */
	static public Recipe getRecipe(Context context) throws ContextException {
		Recipe r = new Recipe();
		r.name = (String)context.getValue("name");
		r.price = (int)context.getValue("price");
		r.amtCoffee = (int)context.getValue("amtCoffee");
		r.amtMilk = (int)context.getValue("amtMilk");
		r.amtSugar = (int)context.getValue("amtSugar");
		r.amtChocolate = (int)context.getValue("amtChocolate");
		return r;
	}
	/**
	 * Creating a context based on recipe values
	 * @param recipe Recipe to be wrapped up in context
	 * @return Returns newly created context based on recipe content
	 * */
	static public Context getContext(Recipe recipe) throws ContextException {
		Context cxt = new ServiceContext();
		cxt.putValue("name", recipe.getName());
		cxt.putValue("price", recipe.getPrice());
		cxt.putValue("amtCoffee", recipe.getAmtCoffee());
		cxt.putValue("amtMilk", recipe.getAmtMilk());
		cxt.putValue("amtSugar", recipe.getAmtSugar());
		cxt.putValue("amtChocolate", recipe.getAmtChocolate());
		return cxt;
	}


}
