package com.example.cs451_inventoryapp;

import com.example.cs451_inventorypackage.Location;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
/**
 * This activity allows the user to save a new location
 * by entering it's name. 
 */
public class NewLocationActivity extends ActionBarActivity {
	Button saveLoc;
	Button deleteLoc;
	CheckBox isSub;
	EditText locName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_location);
		saveLoc = (Button) findViewById(R.id.saveLoc);
		deleteLoc = (Button) findViewById(R.id.deleteLoc);
		isSub = (CheckBox) findViewById(R.id.chkBx);
		locName = (EditText) findViewById(R.id.locName);
		
		saveLoc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Location l = new Location();
				l.setName(locName.getText().toString());
				if(!isSub.isSelected()){
					l.isRoot = false;
				}
				
				Intent passItemBackIntent = new Intent(NewLocationActivity.this, MainActivity.class);
				passItemBackIntent.putExtra("loc", l);
				setResult(RESULT_OK, passItemBackIntent);
				finish();
			}
		});
		
		deleteLoc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
