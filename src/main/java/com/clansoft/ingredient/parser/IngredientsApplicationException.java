package com.clansoft.ingredient.parser;

import java.io.IOException;

public class IngredientsApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5976843631210424280L;

	public IngredientsApplicationException(IOException e) {
		super(e);
	}

}
