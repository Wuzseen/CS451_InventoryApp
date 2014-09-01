package com.example.cs451_inventoryapp;

import java.util.ArrayList;
import java.util.List;

import com.example.cs451_inventoryapp.InventoryDialog.onSubmitListener;
import com.example.cs451_inventorypackage.Barcode;
import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.InventoryManager;
import com.example.cs451_inventorypackage.Location;
import com.example.cs451_inventorypackage.SQLoader;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This activity allows the user to enter information about a new item. This 
 * activity is also reused whenever a user wants to edit information about a 
 * specific item.
 */
public class NewItemActivity extends ActionBarActivity 
	implements onSubmitListener, OnItemSelectedListener{
	ImageButton scanBut;
	Button saveBut;
	Button inventoryBut;
	Button deleteBut;
	EditText barcode;
	EditText SKU;
	EditText dName;
	Spinner locSpnr;
	TextView stock;
	String location;

	static final int SCAN_BARCODE_REQUEST = 1; // request code to send to BarcodeScannerActivity
	static final int RESULT_OK = 0;
	
	/* New item attributes */
	Barcode ibarcode;
	String iSKU;
	String iname;
	int istock;
	/* The item itself */
	InventoryItem mitem = new InventoryItem();
	/* Something to manage the inventory */
	InventoryManager imanager;
	String action;
	ArrayList<Location> locs = new ArrayList<Location>();
	InventoryItem item = new InventoryItem();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* TODO imanager.instance */
		setContentView(R.layout.activity_new_item);
		action = getIntent().getExtras().getString("action");
		scanBut = (ImageButton) findViewById(R.id.scan);
		saveBut = (Button) findViewById(R.id.saveBut);
		barcode = (EditText) findViewById(R.id.barcodeEntry);
		SKU = (EditText) findViewById(R.id.skuEntry);
		dName = (EditText) findViewById(R.id.dnameEntry);
		locSpnr = (Spinner) findViewById(R.id.locationEntry);
		stock = (TextView) findViewById(R.id.stock);
		inventoryBut = (Button) findViewById(R.id.updateInventoryBut);
		deleteBut = (Button) findViewById(R.id.deleteBut);
		
		/* Check the extras passed to determine whether a new or an edit item 
		is being done */
		if(action=="edit"){
			item = (InventoryItem) getIntent().getExtras().get("item");
		}
		
		stock.setText("0");
		
		scanBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Scan the barcode and enter it onto the entrybox
				Intent scanABarcodeIntent = new Intent(NewItemActivity.this, BarcodeScannerActivity.class);
				startActivityForResult(scanABarcodeIntent, SCAN_BARCODE_REQUEST);
			}
		});
		
		inventoryBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				InventoryDialog dialog = new InventoryDialog();
				dialog.mListener = NewItemActivity.this;
				// TODO Set the numbers
				dialog.current = stock.getText().toString();
				dialog.show(getSupportFragmentManager(), "");
			}
		});
		
		locSpnr.setOnItemSelectedListener(this);
		
		saveBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(NewItemActivity.this, "Saved", Toast.LENGTH_SHORT).show();
				String b = barcode.getText().toString();
				String sku = SKU.getText().toString();
				String name = dName.getText().toString();
				int inventory = Integer.parseInt(stock.getText().toString());
				// Save new item to the database then close activity
				mitem.setBarcode(b);
				mitem.setName(name);
				mitem.setSKU(sku);
				mitem.setLoc(location);
				mitem.setCount(inventory);
				
				Intent passItemBackIntent = new Intent(NewItemActivity.this, MainActivity.class);
				passItemBackIntent.putExtra("item", mitem);
				setResult(RESULT_OK, passItemBackIntent);
				finish();
			}
		});
		
		deleteBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent data){
		super.onActivityResult(requestcode, resultcode, data);
		if (requestcode == SCAN_BARCODE_REQUEST){
    		// Check if request was successful
    		if (resultcode == RESULT_OK){
    			// Set the returned string to barcode box.
    			if (data == null) {
    				return;
    			} else {
    				String b = data.getStringExtra("barcode");
        			barcode.setText(b);
    			}
    		}
    	} else {
    		Toast.makeText(this,"Couldn't scan barcode", Toast.LENGTH_SHORT).show();;
    	}
	}

	@Override
	public void setOnSubmitListener(String arg) {
		stock.setText(arg);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// Get the item that was selected. 
		String loc = parent.getItemAtPosition(position).toString();
		location = loc;
		Toast.makeText(NewItemActivity.this, "Picked " + loc, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		location = "";
	}
	
}
