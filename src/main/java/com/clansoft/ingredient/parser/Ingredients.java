package com.clansoft.ingredient.parser;

import java.util.Set;
import java.util.TreeSet;

public class Ingredients {
	
	public Ingredients() {
		this.ingredients = new TreeSet<>();
		this.allergens = new TreeSet<>();
	}
	private Set<Ingredient> ingredients;
	private Set<Allergen> allergens;

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
	
	public void addAllergen(Allergen allergen) {
		allergens.add(allergen);
	}
	
	public Set<Allergen> getAllergens() {
		return allergens;
	}
	
}
