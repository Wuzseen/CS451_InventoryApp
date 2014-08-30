package com.example.cs451_inventorypackage;

import java.util.ArrayList;

// Handles hierarchy of locations
public class InventoryManager {
	private ArrayList<Location> locationListing;
	
	public InventoryManager() {
		this.locationListing = new ArrayList<Location>();
	}
	
	public void addRootLocation(Location l) {
		this.locationListing.add(l);
	}
	
	public FindResult<InventoryItem> findByUniqueId(int id) {
		// Searches through locations
		FindResult<InventoryItem> ret = new FindResult<InventoryItem>();
		for(Location l : locationListing) {
			ret.addItem(l.findItemById(id));
			if(ret.successful()) {
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
	
	public FindResult<Location> findMatchingLocationByName(String name) {
		FindResult<Location> ret = new FindResult<Location>();
		for(Location l : locationListing) {
			ret.addItems(l.find(name));
		}
		return ret;
	}
	
	// Does a location name contain the search string
	public FindResult<Location> findContainingLocationByName(String name) {
		FindResult<Location> ret = new FindResult<Location>();
		for(Location l : locationListing) {
			ret.addItems(l.findContaining(name));
		}
		return ret;
	}
	
	public FindResult<InventoryItem> findByBarcode(String code) {
		// Searches through locations
		FindResult<InventoryItem> ret = new FindResult<InventoryItem>();
		for(Location l : locationListing) {
			ret.addItems(l.findItemsWithBarcode(code));
		}
		return ret;
	}
}
