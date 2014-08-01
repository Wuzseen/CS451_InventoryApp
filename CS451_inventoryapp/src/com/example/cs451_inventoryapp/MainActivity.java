package com.example.cs451_inventoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
		Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button newBut = (Button) findViewById(R.id.newBut);
        Button editBut = (Button) findViewById(R.id.editBut);
        Button inventoryBut = (Button) findViewById(R.id.inventoryBut);
        Button locateBut = (Button) findViewById(R.id.locateBut);
        
        // Add the fragment to the 'fragment_container' FrameLayout        
        newBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.fragment = new NewEditFragment();
		        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_container, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
        
        editBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.fragment = new NewEditFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_container, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
        
        inventoryBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.fragment = new InventoryFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_container, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
        
        locateBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO MainActivity.this.fragment = new LocateFragment();
				// FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				// transaction.replace(R.id.fragment_container, fragment);
				// transaction.addToBackStack(null);
				// transaction.commit();
			}
		});     
    }
}
