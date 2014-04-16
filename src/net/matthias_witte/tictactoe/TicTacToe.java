package net.matthias_witte.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {

	/**
	 * user's id
	 */
	protected int userId;
	
	/**
	 * computer's id
	 */
	protected int computerId;
	
	/**
	 * the signs for the players
	 */
	protected String[] signs;
	
	/**
	 * the fields which represents the 3x3 grid
	 */
	protected int[] fields;
	
	protected AI ai;
	
	/**
	 * @param difficulty the higher the smarter the cpu
	 */
	public TicTacToe(int difficulty) {
		
		this.fields = new int[9];
		for(int i = 0; i < this.fields.length; i++) {
			this.fields[i] = -1;
		}
		
		// set the signs
		this.signs = new String[2];
		this.signs[0] = "X";
		this.signs[1] = "O";
		
		// create the player ids randomized which means that the start player is random and the sign
		// I think the sign should not change randomized but the task definition for this project required this
		this.userId = new Random().nextInt(2);
		System.out.println(this.userId);
		if(this.userId == 0) {
			this.computerId = 1;
		}else{
			this.computerId = 0;
		}
		// create the ai
		this.ai = new AI(difficulty, this.userId, this.computerId);
	}
	
	/**
	 * Get the sign of the user
	 * @return
	 */
	public String getUserSign() {
		return this.signs[this.userId];
	}
	
	/**
	 * Get the sign of the cpu
	 * @return
	 */
	public String getComputerSign() {
		return this.signs[this.computerId];
	}
	
	/**
	 * Get the amount of interactions of the winner
	 * 
	 * @return
	 */
	public int getWinnerInteractions() {
		int winnerId;
		switch(this.checkGameComplete()){
			case 0:
				winnerId = this.userId;
				break;
			case 1:
				winnerId = this.computerId;
				break;
			default:
				return 0;
		}
		int count = 0;
		for(int i=0; i < this.fields.length; i++) {
			if(this.fields[i] == winnerId) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Set the action of the user
	 * @param index
	 */
	public void setUserField(int index) {
		this.fields[index] = this.userId;
	}
	
	/**
	 * Starts the game randomized depending on the player ids
	 * @return
	 */
	public int randomStart() {
		if(this.userId == 0) {
			return -1;
		}
		return this.setComputerField();
	}
	
	/**
	 * Find the available fields
	 * @return
	 */
	protected ArrayList<Integer> findAvailableFields() {
		ArrayList<Integer> available = new ArrayList<Integer>();
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				available.add(i);
			}
		}
		return available;
	}
	
	/**
	 * Returns the index of the generated action or -1 if something is wrong
	 * 
	 * @return
	 */
	public int setComputerField() {
		
		// collect possible options
		ArrayList<Integer> available = new ArrayList<Integer>();
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				available.add(i);
			}
		}
		
		int fieldIndex = this.ai.getTurn(this.fields, available);
		
		if(fieldIndex >= 0) {
			this.fields[fieldIndex] = this.computerId;
		}
		return fieldIndex;
	}
	
	public int checkGameComplete() {
		return TicTacToe.checkGameComplete(this.fields, this.userId);
	}
	
	/**
	 * Checks if the game is complete
	 * 
	 * -1: game is not complete
	 *  0: user won
	 *  1: computer won
	 *  2: draw
	 * @return 
	 */
	public static int checkGameComplete(int[] fields, int playerId) {
		
		// check the rows
		for(int i = 0; i < fields.length; i = i + 3) {
			// check if row is equal
			if(fields[i] == fields[i+1] && fields[i] == fields[i+2]){
				// only if anyone won
				if(fields[i] > -1) {
					if(fields[i] == playerId) {
						return 0;
					}else{
						return 1;
					}
				}
			}
		}
		
		// check the columns
		for(int i = 0; i < fields.length / 3; i++) {
			// check if row is equal
			if(fields[i] == fields[i+3] && fields[i] == fields[i+6]){
				// only if anyone won
				if(fields[i] > -1) {
					if(fields[i] == playerId) {
						return 0;
					}else{
						return 1;
					}
				}
			}
		}
		
		// check the dia left up to right down
		if(fields[0] == fields[4] && fields[0] == fields[8]){
			// only if anyone won
			if(fields[0] > -1) {
				if(fields[0] == playerId) {
					return 0;
				}else{
					return 1;
				}
			}
		}
		
		// check the dia left down to right down
		if(fields[6] == fields[4] && fields[6] == fields[2]){
			// only if anyone won
			if(fields[6] > -1) {
				if(fields[6] == playerId) {
					return 0;
				}else{
					return 1;
				}
			}
		}
		
		// check if there is still a playable field
		for(int i = 0; i < fields.length; i++) {
			if(fields[i] == -1) {
				return -1;
			}
		}
		
		// draw
		return 2;
	}
}
