package net.matthias_witte.tictactoe;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class TicTacToe {

	
	protected int userId;
	protected int computerId;
	
	protected String[] signs;
	
	protected int[] fields;
	
	public TicTacToe() {
		this.fields = new int[9];
		for(int i = 0; i < this.fields.length; i++) {
			this.fields[i] = -1;
		}
		
		this.signs = new String[2];
		this.signs[0] = "X";
		this.signs[1] = "O";
		
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
	
	public void setUserField(int index) {
		this.fields[index] = this.userId;
	}
	
	public boolean setField(int index, int userId){
		this.fields[index] = userId;
		
		return true;
	}
	
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
		
		ArrayList<Integer> available = new ArrayList<Integer>();
		
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				available.add(i);
			}
		}
		
		if(available.size() > 0) {
			int idx;
			if(available.size() == 1) {
				idx = 0;
			}else{
				idx = new Random().nextInt(available.size() - 1);
			}
			
			this.fields[available.get(idx)] = this.computerId;
			return available.get(idx);
		}
		return -1;
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
