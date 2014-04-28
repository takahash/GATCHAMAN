package com.example.hackumap;

import com.example.hackumap.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void loginButton_onClick(View view) {
		EditText IDeditText = (EditText) findViewById(id.IDeditText);
		EditText PassEditText = (EditText) findViewById(id.PassEditText);
		Log.v("ID:",IDeditText.getText().toString() );
		Log.v("Pass:",PassEditText.getText().toString());
		Intent i = new Intent(this,Main.class);
		startActivity(i);
	}
}
