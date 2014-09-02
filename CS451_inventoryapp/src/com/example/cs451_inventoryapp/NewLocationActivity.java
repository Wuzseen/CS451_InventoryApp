package com.example.cs451_inventoryapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/**
 * This activity allows the user to save a new location
 * by entering it's name. 
 */
public class NewLocationActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_location);
	}
}
