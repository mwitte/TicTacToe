package net.matthias_witte.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class AI {
	
	/**
	 * AI level, the higher the smarter the ai
	 */
	protected int level = 0;
	
	/**
	 * All fields of the game
	 */
	protected int[] fields;
	
	/**
	 * All available fiels for current game state
	 */
	protected ArrayList<Integer> available;
	
	/**
	 * Player's id
	 */
	protected int playerId;
	
	/**
	 * AI's id
	 */
	protected int aiId;
	
	/**
	 * Creates an ai
	 * 
	 * @param level the higher the smarter the ai, current implemented: 0, 1, 2
	 * @param playerId the player's id
	 * @param aiId the ai's id
	 */
	public AI(int level, int playerId, int aiId) {
		this.level = level;
		this.playerId = playerId;
		this.aiId = aiId;
	}
	
	/**
	 * Gets the turn by the ai depending on the current game state and level
	 * 
	 * @param fields
	 * @param available
	 * @return
	 */
	public int getTurn(int[] fields, ArrayList<Integer> available) {
		this.fields = fields;
		this.available = available;
		
		// there are no options
		if(available.size() == 0) {
			return -1;
		}
		
		// there is only one option left, use it
		if(available.size() == 1) {
			return available.get(0);
		}
		
		// for level one and above
		if(this.level >= 1) {
			int winTurn = this.winTurn();
			if(winTurn >= 0) {
				return winTurn;
			}
		}
		
		// for level two and above
		if(this.level >= 2) {
			int preventUserWinTurn = this.preventUserWinTurn();
			if(preventUserWinTurn >= 0) {
				return preventUserWinTurn;
			}
		}
		
		// do a random turn if nothing above fitted
		return this.randomTurn();
	}
	
	/**
	 * A simple random turn for the available fields
	 * @return
	 */
	protected int randomTurn() {
		int index = new Random().nextInt(this.available.size());
		return this.available.get(index);
	}
	
	/**
	 * Checks if there is a win turn possible
	 * 
	 * @return
	 */
	protected int winTurn() {
		for(int i = 0; i < available.size(); i++) {
			this.fields[available.get(i)] = this.aiId;
			// if this will result in a win for the computer
			if(TicTacToe.checkGameComplete(this.fields, this.playerId) == 1) {
				// @TODO this should probably only happen with a specific chance
				return available.get(i);
			}
			// unchange
			this.fields[available.get(i)] = -1;
		}
		return -1;
	}
	
	/**
	 * Checks if there is a turn which prevents that the user wins
	 * @return
	 */
	protected int preventUserWinTurn() {
		for(int i = 0; i < available.size(); i++) {
			this.fields[available.get(i)] = this.playerId;
			// if this will result in a win for the user
			if(TicTacToe.checkGameComplete(this.fields, this.playerId) == 0) {
				this.fields[available.get(i)] = this.aiId;
				// @TODO this should probably only happen with a specific chance
				return available.get(i);
			}
			// unchange
			this.fields[available.get(i)] = -1;
		}
		return -1;
	}
}
