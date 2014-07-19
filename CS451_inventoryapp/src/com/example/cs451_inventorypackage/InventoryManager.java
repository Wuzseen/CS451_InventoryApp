package com.example.cs451_inventorypackage;

import java.util.ArrayList;

// Handles hierarchy of locations
public class InventoryManager {
	private ArrayList<Location> locationListing;
	
	public InventoryManager() {
		this.locationListing = new ArrayList<Location>();
	}
	
	public void AddRootLocation(Location l) {
		this.locationListing.add(l);
	}
	
	public FindResult<InventoryItem> findByUniqueId(int id) {
		// Searches through locations
		FindResult<InventoryItem> ret = new FindResult<InventoryItem>();
		for(Location l : locationListing) {
			ret.addItem(l.findItemById(id));
			if(ret.Successful()) {
				return ret;
			}
		}
		return ret;
	}
	
	public FindResult<InventoryItem> findByName(String name) {
		// Searches through locations
		FindResult<InventoryItem> ret = new FindResult<InventoryItem>();
		for(Location l : locationListing) {
			ret.addItems(l.findItemsWithName(name));
		}
		return ret;
	}
}
