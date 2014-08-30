package com.example.cs451_inventoryapp;

import com.example.cs451_inventoryapp.InventoryDialog.onSubmitListener;
import com.example.cs451_inventorypackage.Barcode;
import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.InventoryManager;
import com.example.cs451_inventorypackage.SQLoader;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This activity allows the user to enter information about a new item. This 
 * activity is also reused whenever a user wants to edit information about a 
 * specific item.
 */
public class NewItemActivity extends ActionBarActivity 
	implements onSubmitListener{
	Button scanBut;
	Button saveBut;
	Button inventoryBut;
	EditText barcode;
	EditText SKU;
	EditText dName;
	EditText location;
	TextView stock;

	static final int SCAN_BARCODE_REQUEST = 1; // request code to send to BarcodeScannerActivity
	static final int RESULT_OK = 0;
	
	/* New item attributes */
	Barcode ibarcode;
	Location ilocation;
	String iSKU;
	String iname;
	int istock;
	/* The item itself */
	InventoryItem mitem = new InventoryItem();
	/* Something to manage the inventory */
	InventoryManager imanager;
	/* Something to save and load to the SQL DB */
	SQLoader msqlloader = new SQLoader();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_item);
		scanBut = (Button) findViewById(R.id.scan);
		saveBut = (Button) findViewById(R.id.saveBut);
		barcode = (EditText) findViewById(R.id.barcodeEntry);
		SKU = (EditText) findViewById(R.id.skuEntry);
		dName = (EditText) findViewById(R.id.dnameEntry);
		location = (EditText) findViewById(R.id.locationEntry);
		stock = (TextView) findViewById(R.id.stock);
		inventoryBut = (Button) findViewById(R.id.updateInventoryBut);
		
		/* Check the extras passed to determine whether a new or an edit item 
		is being done */
		
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
		
		saveBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String b = barcode.getText().toString();
				ibarcode = new Barcode(b);
				String sku = SKU.getText().toString();
				String name = dName.getText().toString();
				String loc = location.getText().toString();
				ilocation = new Location();
				// TODO Save new item to the database then close activity
				mitem.setBarcode(ibarcode);
				mitem.setName(name);
				mitem.setSKU(sku);
				mitem.setLoc()
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
	
}
