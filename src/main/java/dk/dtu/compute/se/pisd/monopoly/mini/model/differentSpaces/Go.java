package dk.dtu.compute.se.pisd.monopoly.mini.model.differentSpaces;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;

/**
 * Represents the start/go field from the Matador gameboard.
 * @author Jacob Jørgensen
 *
 */
public class Go extends Space {
	
	private String text;
	
	/**
	 * Sets the field's text.
	 * @param text the field's text.
	 */
	public void setText(String text ) {
		this.text=text;
	}
	
	/**
	 * Returns the field's text.
	 * @return the field's text as String.
	 */
	public String getText() {
		return this.text;
	}

}
