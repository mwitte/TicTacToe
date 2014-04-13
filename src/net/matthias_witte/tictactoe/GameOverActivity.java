package net.matthias_witte.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.gameover);
		
		Bundle b = getIntent().getExtras();
		
		int result = b.getInt("result");
		int interactions = b.getInt("interactions");
		int userWon = b.getInt("userWon");
		int cpuWon = b.getInt("cpuWon");
		
		CharSequence playerWonLabel;
		CharSequence interactionsText;
		
		switch(result) {
			case 0:
				playerWonLabel = "You won :-)";
				interactionsText = "with " + interactions + " interactions";
				break;
			case 1:
				playerWonLabel = "CPU won :-(";
				interactionsText = "with " + interactions + " interactions";
				break;
			default:
				playerWonLabel = "Draw";
				interactionsText = "";
		}
		
		TextView resultLabel = (TextView) findViewById(R.id.result);
		resultLabel.setText(playerWonLabel);
		
		TextView interactionsLabel = (TextView) findViewById(R.id.interactions);
		interactionsLabel.setText(interactionsText);
		
		TextView statsLabel = (TextView) findViewById(R.id.stats);
		statsLabel.setText(userWon + " - " + cpuWon);
	}
	
	public void backAction (View v) {
		Intent i = new Intent();
		setResult(Activity.RESULT_CANCELED, i);
		finish();
	}
	
	public void newGameAction (View v) {
		Intent i = new Intent();
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
}
