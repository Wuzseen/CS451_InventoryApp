package com.example.cs451_inventoryapp;

import java.util.ArrayList;

import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.InventoryManager;
import com.example.cs451_inventorypackage.Location;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This activity shows a user the list of all the 
 * items located in the chosen location
 */
public class LocationDetailsActivity extends ListActivity {
	Location location;
	TextView lhdr;
	ArrayList<InventoryItem> itemsAtLoc;
	ListView ilist;
	InventoryManager iManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_details);
		location = (Location) getIntent().getExtras().get("location");
		lhdr = (TextView) findViewById(R.id.locHdr);
		lhdr.setText(location.getName());
		ilist = getListView();
		
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
			}
		});
	}

}
