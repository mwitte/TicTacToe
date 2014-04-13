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
		CharSequence result = b.getCharSequence("result");
		
		TextView resultLabel = (TextView) findViewById(R.id.result);
		resultLabel.setText(result);
	}
	
	
	public void newGameAction (View v) {
		Intent i = new Intent();
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
}
