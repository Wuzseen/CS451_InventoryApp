package com.example.cs451_inventoryapp;

import java.util.ArrayList;
import java.util.List;

import com.example.cs451_inventoryapp.InventoryDialog.onSubmitListener;
import com.example.cs451_inventorypackage.Barcode;
import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.InventoryManager;
import com.example.cs451_inventorypackage.Location;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
	Location location;

	static final int SCAN_BARCODE_REQUEST = 1; // request code to send to BarcodeScannerActivity
	static final int RESULT_OK = 0;
	
	/* New item attributes */
	Barcode ibarcode;
	String iSKU;
	String iname;
	int istock;
	/* Something to manage the inventory */
	InventoryManager imanager;
	String action;
	ArrayList<Location> locs = new ArrayList<Location>();
	InventoryItem item = new InventoryItem();
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* TODO imanager.instance */
		setContentView(R.layout.activity_new_item);
		this.action = getIntent().getExtras().getString("action");
		this.item = (InventoryItem) getIntent().getExtras().get("item");
		this.locs = (ArrayList<Location>) getIntent().getExtras().get("locations");
		System.out.println("Action is " + action);
		scanBut = (ImageButton) findViewById(R.id.scan);
		saveBut = (Button) findViewById(R.id.saveBut);
		barcode = (EditText) findViewById(R.id.barcodeEntry);
		SKU = (EditText) findViewById(R.id.skuEntry);
		dName = (EditText) findViewById(R.id.dnameEntry);
		locSpnr = (Spinner) findViewById(R.id.locationEntry);
		stock = (TextView) findViewById(R.id.stock);
		inventoryBut = (Button) findViewById(R.id.updateInventoryBut);
		deleteBut = (Button) findViewById(R.id.deleteBut);
		
        LocSpinnerAdpt adapter = new LocSpinnerAdpt(NewItemActivity.this, R.layout.location_spinner,locs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locSpnr.setAdapter(adapter);

		/* Check the extras passed to determine whether a new or an edit item 
		is being done */
		if(action.equalsIgnoreCase("edit")){
			System.out.println("About to edit");
			item = (InventoryItem) getIntent().getExtras().get("item");
			System.out.println("Item had barcode " + item.getBarcode());
			barcode.setText(item.getBarcode());
			SKU.setText(item.getSKU());
			dName.setText(item.getName());
			// locSpnr.setSelection(locs.indexOf(item.getLoc()), true);
			stock.setText(Integer.toString(item.getCount()));
		} else {
			stock.setText("0");
		}
		
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
				if(item == null) {
					item = new InventoryItem();
				}
				item.setBarcode(b);
				item.setName(name);
				item.setSKU(sku);
				item.sLoc(location);
				item.setLoc(location.getName());
				item.setCount(inventory);
				
				Intent passItemBackIntent = new Intent(NewItemActivity.this, MainActivity.class);
				passItemBackIntent.putExtra("item", item);
				passItemBackIntent.putExtra("action", "s");
//				InventoryManager.Instance().updateItem(item);
//				InventoryManager.Instance().updateLocation(location);
				if(getParent() == null) {
					setResult(RESULT_OK, passItemBackIntent);
				} else {
					getParent().setResult(RESULT_OK, passItemBackIntent);
				}
				finish();
			}
		});
		
		deleteBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent passItemBackIntent = new Intent(NewItemActivity.this, MainActivity.class);
				passItemBackIntent.putExtra("item", item);
				passItemBackIntent.putExtra("action", "d");
				setResult(RESULT_OK, passItemBackIntent);
				finish();
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
		location = (Location) parent.getItemAtPosition(position);
		
		//Toast.makeText(NewItemActivity.this, "Picked " + location.getName(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		location = new Location();
	}
	
	private class LocSpinnerAdpt extends ArrayAdapter<Location>{
		private Context context;
		private List<Location> l;
		public LocSpinnerAdpt(Context context, int resource,
				List<Location> objects) {
			super(context, resource, objects);
			this.context = context;
			this.l = objects;
		}
		
		public int getCount(){
			return l.size();
		}
		
		public Location getItem(int position){
			return l.get(position);
		}
		
		public long getItemId(int position){
			return position;
		}
		
		/* Set the location names to the spinner */
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View view = convertView; // This is the view we are going to change
			
			// If the view to be changed is null, it hasn't been rendered yet and it needs to be inflated.
			if(view == null){
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.location_spinner, null);
			}
			
			TextView spnrTxt = (TextView) view.findViewById(R.id.locSpnr);
			spnrTxt.setTextColor(Color.BLACK);
			spnrTxt.setText(l.get(position).getName());
			return view;
		}
		
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent){
			View view = convertView; // This is the view we are going to change
			
			// If the view to be changed is null, it hasn't been rendered yet and it needs to be inflated.
			if(view == null){
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.location_spinner_drop ,null);
			}
			
			TextView spnrTxt = (TextView) view.findViewById(R.id.locSpnr);
			spnrTxt.setTextColor(Color.BLACK);
			spnrTxt.setText(l.get(position).getName());
			return view;
		}
	}
	
}
