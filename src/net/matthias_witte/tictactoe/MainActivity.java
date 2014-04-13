package net.matthias_witte.tictactoe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
	
	/**
	 * dp size for the buttons
	 */
	private static final int buttonSize = 80;
	
	private static final String logTag = "TicTacToe";
	private static final int GAME_OVER = 1;
	
	protected int[] fields;
	protected Button[] buttons;

	/**
	 * Gets called on app creation
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		this.buttons = new Button[9];
		this.fields = new int[9];
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.tictactoe);
		
		int buttonIterator = 0;
		
		// iterate all children
		for( int i = 0; i < layout.getChildCount(); i++ ){
			View v = layout.getChildAt(i);
			
			// only if it's a button
			if(v instanceof Button) {
				Button btn = (Button) v;
				this.buttons[buttonIterator] = btn;
				this.setUpButton(btn);
				buttonIterator++;
			}
			
		}
		this.resetGame();
	}
	
	/**
	 * sets up a single button
	 */
	protected void setUpButton(Button btn) {
		
		btn.setOnClickListener(this);
		LayoutParams layoutParams = btn.getLayoutParams();
		layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, buttonSize, getResources().getDisplayMetrics());
		layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, buttonSize, getResources().getDisplayMetrics());
		btn.setTextSize(buttonSize / 2);
		btn.setEnabled(true);
	}
	
	public void resetAction(View view) {
		this.resetGame();
	}
	
	/**
	 * Resets the game
	 */
	protected void resetGame() {
		for(int i = 0; i < this.buttons.length; i++) {
			this.buttons[i].setEnabled(true);
			this.buttons[i].setText("");
			this.fields[i] = -1;
		}
		TextView resultLabel = (TextView) findViewById(R.id.result);
		resultLabel.setText("");
	}
	
	public void onClick(View view) {
		Button button = (Button) view;
		button.setText("X");
		button.setEnabled(false);
		// find the button index
		for(int i = 0; i < this.buttons.length; i++){
			if(button.equals(this.buttons[i])){
				this.fields[i] = 0;
			}
		}
		System.out.println(Arrays.toString(this.fields));
		if(this.checkGameComplete() == -1) {
			this.computerAction();
		}else{
			this.finalizeGame();
		}
	}
	
	protected void finalizeGame() {
		TextView resultLabel = (TextView) findViewById(R.id.result);
		String result;
		switch(this.checkGameComplete()) {
		case 0:
			Log.i(logTag, "User won");
			resultLabel.setText("You won!");
			result = "User won!";
			break;
		case 1:
			Log.i(logTag, "Computer won");
			resultLabel.setText("Computer won!");
			result = "Computer won!";
			break;
		default:
			Log.i(logTag, "Draw");
			resultLabel.setText("Draw!");
			result = "Draw!";
		}
		for(int i = 0; i < this.buttons.length; i++){
			this.buttons[i].setEnabled(false);
		}
		Intent i = new Intent(this,GameOverActivity.class);
		//i.putExtra("result", result);
		Bundle b = new Bundle();
		b.putCharSequence("result", result);
		i.putExtras(b);
		startActivityForResult(i, GAME_OVER);
	}
	
	protected void computerAction() {
		
		ArrayList<Integer> available = new ArrayList<Integer>();
		
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				available.add(i);
			}
		}
		
		if(available.size() > 0) {
			int idx = new Random().nextInt(available.size() - 1);
			this.fields[available.get(idx)] = 1;
			this.buttons[available.get(idx)].setEnabled(false);
			this.buttons[available.get(idx)].setText("O");
		}
		if(this.checkGameComplete() != -1) {
			this.finalizeGame();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Activity.RESULT_OK == resultCode && GAME_OVER == requestCode) {
			this.resetGame();
		}
	}
	
	/**
	 * Checks if the game is complete
	 * 
	 * -1: game is not complete
	 *  0: player 0 won
	 *  1: player 1 won
	 *  2: draw
	 * @return 
	 */
	protected int checkGameComplete() {
		
		// check the rows
		for(int i = 0; i < this.fields.length; i = i + 3) {
			// check if row is equal
			if(this.fields[i] == this.fields[i+1] && this.fields[i] == this.fields[i+2]){
				// only if anyone won
				if(this.fields[i] > -1) {
					return this.fields[i];
				}
			}
		}
		
		// check the columns
		for(int i = 0; i < this.fields.length / 3; i++) {
			// check if row is equal
			if(this.fields[i] == this.fields[i+3] && this.fields[i] == this.fields[i+6]){
				// only if anyone won
				if(this.fields[i] > -1) {
					return this.fields[i];
				}
			}
		}
		
		// check the dia left up to right down
		if(this.fields[0] == this.fields[4] && this.fields[0] == this.fields[8]){
			// only if anyone won
			if(this.fields[0] > -1) {
				return this.fields[0];
			}
		}
		
		// check the dia left down to right down
		if(this.fields[6] == this.fields[4] && this.fields[6] == this.fields[2]){
			// only if anyone won
			if(this.fields[6] > -1) {
				return this.fields[6];
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
