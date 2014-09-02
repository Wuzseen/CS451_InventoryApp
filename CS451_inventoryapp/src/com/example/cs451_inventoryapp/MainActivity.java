package com.example.cs451_inventoryapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.cs451_inventorypackage.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * This is the first activity the user sees when the application launches, 
 * It displays a number of buttons and a search box that the user can interact
 * with. If a user attempts to search for an item, an expandable list view
 * is shown, showing the items and locations. 
 */
public class MainActivity extends ActionBarActivity implements OnItemSelectedListener{
	/* TODO
	 * Items need to be loaded first
	 */
	Button searchBut;
	ImageButton scanBut;
	ImageButton itemBut;
	ImageButton locationBut;
	Spinner searchSpin;
	String searchType;
	EditText searchBx;
	ExpandableListAdapter listAdpt;
	ExpandableListView elistView;
	List<String> listDataHeader;
	HashMap<String, List<?>> listDataChild;
	ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
	InventoryManager iManager;
	
	ArrayList<InventoryItem> allItems = new ArrayList<InventoryItem>();
	ArrayList<Location> allLoc = new ArrayList<Location>();
	
	static final int SCAN_BARCODE_REQUEST = 1; // request code to send to BarcodeScannerActivity
	static final int NEW_EDIT_ITEM_REQUEST = 2; // request code to send to the NewItemActivity
	static final int NEW_LOCATION_REQUEST = 3; // request code to sent to the NewLocationActivity
	static final int RESULT_OK = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimDBTask tIm = new TimDBTask();
        tIm.execute("load");
        try {
			String res = tIm.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        iManager = InventoryManager.Instance();
        allLoc = iManager.getAllLoc();
        
        for(int i = 0; i<allLoc.size(); i++){
        	Log.i("Location found", allLoc.get(i).getName());
        }
        
        allItems = iManager.getAllItems();
        
        for(int j = 0; j<allItems.size(); j++){
        	Log.i("Items found", allItems.get(j).getName());
        } 
        
        searchBx = (EditText) findViewById(R.id.searchBx);
        scanBut = (ImageButton) findViewById(R.id.scanBut);
        itemBut = (ImageButton) findViewById(R.id.itemBut);
        locationBut = (ImageButton) findViewById(R.id.locationBut);
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
					InventoryItem item = (InventoryItem) listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
		
					//InventoryItem item = (InventoryItem) parent.getItemAtPosition(groupPosition).
					System.out.println("Clicked on item with name " + item.getName());
					Intent showItemIntent = new Intent(MainActivity.this, NewItemActivity.class);
					showItemIntent.putExtra("action", "edit");
					//String clicked = parent.getItemAtPosition(childPosition).toString();
					//System.out.println("Clicked this " + clicked);
					showItemIntent.putExtra("item", item);
					showItemIntent.putExtra("locations",allLoc);
					startActivityForResult(showItemIntent, NEW_EDIT_ITEM_REQUEST);
//					startActivityForResult(showItemIntent);
				} else {
					// Open the Location Activity ();
					// pass a flag to edit
					Location loc = (Location) listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
					System.out.println("Clicked on location with name " + loc.getName());
					Intent showLocationIntent = new Intent(MainActivity.this, LocationDetailsActivity.class);
					showLocationIntent.putExtra("location", loc);
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
				newItemIntent.putExtra("locations", allLoc);
				newItemIntent.putExtra("action","new");
				startActivityForResult(newItemIntent, NEW_EDIT_ITEM_REQUEST);
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
				System.out.println("Searching for " + entry + "of type " + searchType);
				performASearch(entry,searchType);
				
				listAdpt = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
				 
		        // setting list adapter
		        elistView.setAdapter(listAdpt);	
			}
		});
    }
    
    protected void performASearch(String query,String type) {
    	FindResult<InventoryItem> resI = null;
    	FindResult<Location> resL = null;
    	FindResult<Location> resl = null;
    	if(type.equals("barcode")) {
    		resI = iManager.findByBarcode(query);
    	} else if(type.equals("name")) {
    		resI = iManager.findByName(query);
    	} else if(type.equals("location")) {
    		//resL = iManager.findMatchingLocationByName(query);
    		resl = iManager.findContainingLocationByName(query);
    	} else if(type.equals("SKU")) {
    		resI = iManager.findByName(query);
    	}
    	
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<?>>();
		
		// Adding child data
		listDataHeader.add("Items");
		listDataHeader.add("Location");
		
		// Add children
		List<InventoryItem> items = new ArrayList<InventoryItem>();
//		InventoryItem dummy = new InventoryItem();
//		dummy.setName("Dummy");
//		dummy.setLoc("Basement");
//		dummy.setBarcode("12345467890");
//		dummy.setSKU("A dummy item");
//		dummy.setCount(12);
//		items.add(dummy);
//		InventoryItem dummy2 = new InventoryItem();
//		dummy2.setName("Dummy2");
//		dummy2.setLoc("Basement");
//		dummy2.setBarcode("12345467891");
//		dummy2.setSKU("Another dummy item");
//		dummy.setCount(3);
//		items.add(dummy2);
		boolean success = false;;
		if(resI != null) {
			success = resI.successful();
			if(success == true){
				for(InventoryItem item : resI){
					//items.add(item.getName());
					items.add(item);
				}
			} else {
				Toast.makeText(MainActivity.this, "Sorry item doesn't exist!", Toast.LENGTH_SHORT).show();
				System.out.println("No items found");
			}	
		}
		List<Location> locations = new ArrayList<Location>();
//		Location l_dummy  = new Location();
//		l_dummy.contains(dummy);
//		l_dummy.setName("Basement");
//		locations.add(l_dummy);
		//if(!(resL == null))
		if(resL != null) {
			success = resL.successful();
			if(success == true){
				for(Location loc : resL){
					//locations.add(loc.getName());
					locations.add(loc);
				}
			} else {
				Toast.makeText(MainActivity.this, "Sorry Location doesn't exist!", Toast.LENGTH_SHORT).show();
				Log.i("Location search", "None found");
			}
		}
		if(resl != null) {
			success = resl.successful();
			if(success == true){
				for(Location loc : resl){
					//locations.add(loc.getName());
					locations.add(loc);
				}
			} else {
				Toast.makeText(MainActivity.this, "Sorry Location doesn't exist!", Toast.LENGTH_SHORT).show();
				Log.i("Location search", "None found");
			}
		}
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
    			}
    			
    		}
    	} else if(requestcode == NEW_EDIT_ITEM_REQUEST) {
    		// Check if request was successful
    		if (resultcode == RESULT_OK){
    			// Get the returned Inventory item and add it to the list.
    			if (data == null) {
    				return;
    			} else {
    				String action = data.getExtras().getString("action").toString();
    				if(action.equals("s")){
    					InventoryItem item = (InventoryItem) data.getExtras().get("item"); 
    					items.add(item);
    					TimDBTask tIm_save = new TimDBTask();
    					tIm_save.execute("save");
    				} else if(action.equals("d")){
    					InventoryItem item = (InventoryItem) data.getExtras().get("item");
    					String id = item.getId().toString();
    					TimDBTask tIm_del = new TimDBTask();
    					tIm_del.execute("del",id);
    				}
    				
    			}
    		} 
    	} else if (requestcode == NEW_LOCATION_REQUEST){
    		if (resultcode == RESULT_OK){
    			if(data == null){
    				return;
    			} else {
    				Location loc = (Location) data.getExtras().get("loc");
    				allLoc.add(loc);
    				TimDBTask tIm_save = new TimDBTask();
    				tIm_save.execute("savel");
    			}
    		}
    	} else {
    		Toast.makeText(this,"Couldn't scan barcode", Toast.LENGTH_SHORT).show();;
    	}
    }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
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
	
	private class TimDBTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			String action = params[0];
			String success = "done";
			if (action.equals("load")){
				ArrayList<InventoryItem> items_arr = SQLoader.allItems();
				ArrayList<Location> loc_arr = SQLoader.allLocations();
			} else if (action.equals("save")){
				SQLoader.saveItems(items);
				SQLoader.saveLocation(allLoc);
			} else if(action.equals("savel")){
				SQLoader.saveLocation(allLoc);
				SQLoader.saveItems(items);
			} else if(action.equals("del")){
				SQLoader.deleteItem(Integer.parseInt(params[1]));
			}
			return success;
		}
	}
}
