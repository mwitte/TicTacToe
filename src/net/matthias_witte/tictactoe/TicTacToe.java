package net.matthias_witte.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.util.Log;

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
	
	protected int level = 1;
	
	public TicTacToe() {
		this.fields = new int[9];
		for(int i = 0; i < this.fields.length; i++) {
			this.fields[i] = -1;
		}
		
		// set the signs
		this.signs = new String[2];
		this.signs[0] = "X";
		this.signs[1] = "O";
		
		// create the player ids randomized
		this.userId = new Random().nextInt(2);
		System.out.println(this.userId);
		if(this.userId == 0) {
			this.computerId = 1;
		}else{
			this.computerId = 0;
		}
	}
	
	public String getUserSign() {
		return this.signs[this.userId];
	}
	
	public String getComputerSign() {
		return this.signs[this.computerId];
	}
	
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
		
		// there are no options
		if(available.size() == 0) {
			return -1;
		}
		
		// there is only one option left, use it
		if(available.size() == 1) {
			this.fields[available.get(0)] = this.computerId;
			return available.get(0);
		}
		// check if one of the available options will win the game for the computer
		for(int i = 0; i < available.size(); i++) {
			this.fields[available.get(i)] = this.computerId;
			// if this will result in a win for the computer
			if(this.checkGameComplete() == 1) {
				return available.get(i);
			}
			// unchange
			this.fields[available.get(i)] = -1;
		}
		
		// check if one of the available options will win the game for the user
		for(int i = 0; i < available.size(); i++) {
			this.fields[available.get(i)] = this.userId;
			// if this will result in a win for the user
			if(this.checkGameComplete() == 0) {
				this.fields[available.get(i)] = this.computerId;
				return available.get(i);
			}
			// unchange
			this.fields[available.get(i)] = -1;
		}
		
		// otherwise use a random field
		int index = new Random().nextInt(available.size());
		this.fields[available.get(index)] = this.computerId;
		return available.get(index);
		
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
	public int checkGameComplete() {
		
		// check the rows
		for(int i = 0; i < this.fields.length; i = i + 3) {
			// check if row is equal
			if(this.fields[i] == this.fields[i+1] && this.fields[i] == this.fields[i+2]){
				// only if anyone won
				if(this.fields[i] > -1) {
					if(this.fields[i] == this.userId) {
						return 0;
					}else{
						return 1;
					}
				}
			}
		}
		
		// check the columns
		for(int i = 0; i < this.fields.length / 3; i++) {
			// check if row is equal
			if(this.fields[i] == this.fields[i+3] && this.fields[i] == this.fields[i+6]){
				// only if anyone won
				if(this.fields[i] > -1) {
					if(this.fields[i] == this.userId) {
						return 0;
					}else{
						return 1;
					}
				}
			}
		}
		
		// check the dia left up to right down
		if(this.fields[0] == this.fields[4] && this.fields[0] == this.fields[8]){
			// only if anyone won
			if(this.fields[0] > -1) {
				if(this.fields[0] == this.userId) {
					return 0;
				}else{
					return 1;
				}
			}
		}
		
		// check the dia left down to right down
		if(this.fields[6] == this.fields[4] && this.fields[6] == this.fields[2]){
			// only if anyone won
			if(this.fields[6] > -1) {
				if(this.fields[6] == this.userId) {
					return 0;
				}else{
					return 1;
				}
			}
		}
		
		// check if there is still a playable field
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				return -1;
			}
		}
		
		// draw
		return 2;
	}
}
