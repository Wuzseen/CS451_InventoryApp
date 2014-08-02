package com.example.cs451_inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
		Fragment fragment;
		String barcode;
		EditText barcode_entry;
		static final int SCAN_BARCODE_REQUEST = 1; // request code to send to BarcodeScannerActivity
		static final int RESULT_OK = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        barcode_entry = (EditText) findViewById(R.id.barcodeSearch);
        
        Button scanBut = (Button) findViewById(R.id.scanBut);
        Button newBut = (Button) findViewById(R.id.newBut);
        Button editBut = (Button) findViewById(R.id.editBut);
        Button inventoryBut = (Button) findViewById(R.id.inventoryBut);
        Button locateBut = (Button) findViewById(R.id.locateBut);
        
        // Start up barcode scanning
        scanBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent scanABarcodeIntent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
				startActivityForResult(scanABarcodeIntent, SCAN_BARCODE_REQUEST);
			}
		});
        
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
    
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
    	if (requestcode == SCAN_BARCODE_REQUEST){
    		// Check if request was successful
    		if (resultcode == RESULT_OK){
    			// Set the returned string to barcode box.
    			if (data == null) {
    				return;
    			} else {
    				barcode = data.getStringExtra("barcode");
        			barcode_entry.setText(barcode);
    			}
    			
    		}
    	} else {
    		Toast.makeText(this,"Couldn't scan barcode", Toast.LENGTH_SHORT).show();;
    	}
    }
}
