package com.example.cs451_inventoryapp;

import java.util.ArrayList;
import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.InventoryManager;
import com.example.cs451_inventorypackage.Location;
import com.example.cs451_inventorypackage.SQLoader;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * This activity shows a user the list of all the 
 * items located in the chosen location
 */
public class LocationDetailsActivity extends ListActivity {
	Location location;
	TextView lhdr;
	TextView locCount;
	ArrayList<InventoryItem> itemsAtLoc;
	ListView ilist;
	InventoryManager iManager;
	ArrayList<InventoryItem> allItems = new ArrayList<InventoryItem>();
	ArrayList<Location> allLoc = new ArrayList<Location>();
	ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
	
	static final int NEW_EDIT_ITEM_REQUEST = 2; // request code to send to the NewItemActivity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iManager = InventoryManager.Instance();
		allLoc = iManager.getAllLoc();
		allItems = iManager.getAllItems();		
		setContentView(R.layout.activity_location_details);
		location = (Location) getIntent().getExtras().get("location");
		lhdr = (TextView) findViewById(R.id.locHdr);
		lhdr.setText(location.getName());
		ilist = getListView();
		locCount = (TextView) findViewById(R.id.locCount);
		int count;
		if(location.getItemList().size()==0){
			count = 0;
		} else {
			count = location.getItemList().size();
		}
		
		locCount.setText("Items at location : " + Integer.toString(count));
		
		itemsAtLoc = location.getItemList();
		
		LocDetListAdpt adapter = new LocDetListAdpt(
				LocationDetailsActivity.this, 
				R.layout.location_list_item,
				itemsAtLoc);
		
		ilist.setAdapter(adapter);
		ilist.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				InventoryItem item = itemsAtLoc.get(position);
				Intent showItemDetails = new Intent(LocationDetailsActivity.this, NewItemActivity.class);
				showItemDetails.putExtra("item", item);
				showItemDetails.putExtra("action", "edit");
				showItemDetails.putExtra("locations",allLoc);
				System.out.println("Passing Locations of size " + Integer.toString(allLoc.size()));
				startActivityForResult(showItemDetails, NEW_EDIT_ITEM_REQUEST);
			}
		});
	}
	
	@Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
		if(requestcode == NEW_EDIT_ITEM_REQUEST) {
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
		}
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
