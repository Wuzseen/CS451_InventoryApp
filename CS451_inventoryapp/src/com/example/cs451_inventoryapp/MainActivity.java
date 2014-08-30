package com.example.cs451_inventoryapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.cs451_inventorypackage.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * This is the first activity the user sees when the application launches, 
 * It displays a number of buttons and a search box that the user can interact
 * with. If a user attempts to search for an item, an expandable list view
 * is shown, showing the items and locations. 
 */
public class MainActivity extends ActionBarActivity implements OnItemSelectedListener{
	Button searchBut;
	Button scanBut;
	Button itemBut;
	Button locationBut;
	Spinner searchSpin;
	String searchType;
	EditText searchBx;
	ExpandableListAdapter listAdpt;
	ExpandableListView elistView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
	InventoryManager iManager;
	static final int SCAN_BARCODE_REQUEST = 1; // request code to send to BarcodeScannerActivity
	static final int RESULT_OK = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        iManager = new InventoryManager();
        
        searchBx = (EditText) findViewById(R.id.searchBx);
        scanBut = (Button) findViewById(R.id.scanBut);
        itemBut = (Button) findViewById(R.id.itemBut);
        locationBut = (Button) findViewById(R.id.locationBut);
        searchSpin = (Spinner) findViewById(R.id.searchSpin);
        searchBut = (Button) findViewById(R.id.searchBut);
        elistView = (ExpandableListView) findViewById(R.id.searchRes);
        
        elistView.setOnChildClickListener(new OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if(groupPosition == 0){
					// Open the Item Activity ();
					// pass a flag to edit.
					Intent showItemIntent = new Intent(MainActivity.this, NewItemActivity.class);
					showItemIntent.putExtra("flag", "edit item");
					startActivity(showItemIntent);
				} else {
					// Open the Location Activity ();
					// pass a flag to edit
					Intent showLocationIntent = new Intent(MainActivity.this, LocationDetailsActivity.class);
					showLocationIntent.putExtra("flag", "edit location");
					startActivity(showLocationIntent);
				}
				return false;
			}
        });

        // Start up barcode scanning
        scanBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent scanABarcodeIntent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
				startActivityForResult(scanABarcodeIntent, SCAN_BARCODE_REQUEST);
			}
		});
        
        // Create a new item
        itemBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent newItemIntent = new Intent(MainActivity.this, NewItemActivity.class);
				startActivity(newItemIntent);
			}
        });
        
        // Create a new location
        locationBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent newLocationIntent = new Intent(MainActivity.this, NewLocationActivity.class);
				startActivity(newLocationIntent);
			}
        });
        
        // Adapter for the options
        ArrayAdapter<CharSequence> optionsAdpt = ArrayAdapter.createFromResource(this, R.array.search_array, android.R.layout.simple_spinner_item);
        
        // Chose the layout to use for the choices
        optionsAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        
        // Apply adapter to the spinner
        searchSpin.setAdapter(optionsAdpt);
        
        searchSpin.setOnItemSelectedListener(this);
        
        searchBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String entry = searchBx.getText().toString();
				
				performASearch();
				
				listAdpt = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
				 
		        // setting list adapter
		        elistView.setAdapter(listAdpt);	
			}
		});
    }
    
    protected void performASearch() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		
		// Adding child data
		listDataHeader.add("Items");
		listDataHeader.add("Location");
		
		// Add children
		List<String> items = new ArrayList<String>();
		items.add("Item 1");
		items.add("Item 2");
		items.add("Items 3");
		
		List<String> locations = new ArrayList<String>();
		locations.add("Location 1");
		locations.add("Location 2");
		locations.add("Location 3");
		
		// Map the children to their groups
		listDataChild.put(listDataHeader.get(0), items);
		listDataChild.put(listDataHeader.get(1), locations);
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
    				String barcode = data.getStringExtra("barcode");
    				// TODO pass the barcode to the search function 
    				// and populate the list.
    			}
    			
    		}
    	} else {
    		Toast.makeText(this,"Couldn't scan barcode", Toast.LENGTH_SHORT).show();;
    	}
    }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// Get the item that was selected. 
		long itemID = parent.getItemIdAtPosition(position);
		switch ((int)itemID){
		case 0:
			this.searchType = "barcode";
			break;
		case 1:
			this.searchType = "name";
			break;
		case 2:
			this.searchType = "location";
			break;
		case 3:
			this.searchType = "SKU";
			break;
		default:
			this.searchType = "barcode";				
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}
}
