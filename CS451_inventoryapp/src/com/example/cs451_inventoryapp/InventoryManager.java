package com.example.cs451_inventoryapp;

import java.util.ArrayList;

// Handles heirarchy of locations
public class InventoryManager {
	private ArrayList<Location> locationListing;
	
	public InventoryManager() {
		this.locationListing = new ArrayList<Location>();
	}
	
	public void AddRootItem(InventoryItem i) {
	}
	
	public void AddRootLocation(Location l) {
		this.locationListing.add(l);
	}
}
