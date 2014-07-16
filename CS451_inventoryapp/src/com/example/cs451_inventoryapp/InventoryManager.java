package com.example.cs451_inventoryapp;

import java.util.ArrayList;

// Handles heirarchy of items and locations
public class InventoryManager {
	private ArrayList<Location> locationListing;
	private ArrayList<InventoryItem> invItems;
	
	public InventoryManager() {
		locationListing = new ArrayList<Location>();
		invItems = new ArrayList<InventoryItem>();
	}
	
	public void AddLocation(Location l) {
		locationListing.add(l);
	}
}
