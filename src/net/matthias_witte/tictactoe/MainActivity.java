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
	
	protected int userId = 0;
	protected int computerId = 1;
	
	protected TicTacToe game;
	
	
	protected Button[] buttons;

	/**
	 * Gets called on app creation
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		this.buttons = new Button[9];
		
		
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
		}
		TextView resultLabel = (TextView) findViewById(R.id.result);
		resultLabel.setText("");
		this.game = new TicTacToe();
		int index = this.game.randomStart();
		if(index != -1) {
			this.buttons[index].setEnabled(false);
			this.buttons[index].setText(this.game.getComputerSign());
		}
	}
	
	public void onClick(View view) {
		Button button = (Button) view;
		button.setText(this.game.getUserSign());
		button.setEnabled(false);
		// find the button index
		for(int i = 0; i < this.buttons.length; i++){
			if(button.equals(this.buttons[i])){
				this.game.setUserField(i);
			}
		}
		
		if(this.game.checkGameComplete() == -1) {
			int index = this.game.setComputerField();
			if(index >= 0) {
				this.buttons[index].setEnabled(false);
				this.buttons[index].setText(this.game.getComputerSign());
			}
		}
		if(this.game.checkGameComplete() != -1) {
			this.finalizeGame();
		}
	}
	
	protected void finalizeGame() {
		TextView resultLabel = (TextView) findViewById(R.id.result);
		String result;
		switch(this.game.checkGameComplete()) {
		case 0:
			resultLabel.setText("You won!");
			result = "User won!";
			break;
		case 1:
			resultLabel.setText("Computer won!");
			result = "Computer won!";
			break;
		default:
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
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Activity.RESULT_OK == resultCode && GAME_OVER == requestCode) {
			this.resetGame();
		}
	}
	
	
}
