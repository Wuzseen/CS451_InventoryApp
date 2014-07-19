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
	
	public SearchResult<InventoryItem> findByUniqueId(int id) {
		// Searches through locations
		SearchResult<InventoryItem> ret = new SearchResult<InventoryItem>();
		for(Location l : locationListing) {
			ret.addItem(l.findItemById(id));
			if(ret.Successful()) {
				return ret;
			}
		}
		return ret;
	}
	
	public SearchResult<InventoryItem> findByName(String name) {
		// Searches through locations
		SearchResult<InventoryItem> ret = new SearchResult<InventoryItem>();
		for(Location l : locationListing) {
			ret.addItems(l.findItemsWithName(name));
		}
		return ret;
	}
}
