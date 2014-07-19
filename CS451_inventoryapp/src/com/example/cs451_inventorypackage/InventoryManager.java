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
}
