package com.example.cs451_inventoryapp;

import java.util.ArrayList;

public class Location {
	private static int idCount = 1;

	private int id;
	public int getId() {
		return id;
	}
	
	// Is not setable outside of Add and Remove items
	private ArrayList<InventoryItem> itemList;
	public ArrayList<InventoryItem> getItemList() {
		return itemList;
	}

	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	// Max number of items that can fit, -1 is a sentinel value saying it's infinite
	private int maxSize;
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	private void Setup() {
		this.itemList = new ArrayList<InventoryItem>();
		this.maxSize = -1;
		this.name = "DefaultLocationName";
		this.id = idCount;
		idCount++;
	}
	
	public Location() {
		Setup();
	}
	
	public Location(String locationName) {
		Setup();
		this.name = locationName;
	}
	
	public Location(String locationName, int size) {
		this(locationName);
		this.maxSize = size;
	}
	
	public void AddItem(InventoryItem item) {
		this.itemList.add(item);
	}
	
	public void RemoveItem(InventoryItem item) {
		this.itemList.remove(item);
	}

	public void RemoveItem(int itemId) {
		for(int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getId() == itemId) {
				this.itemList.remove(this.itemList.get(i));
				return;
			}
		}
	}
	
	// Num of items in location
	public int Count() {
		return this.itemList.size();	
	}
	
	public boolean SpaceAvailable() {
		return (this.maxSize == -1 || this.Count() < this.maxSize);
	}
}
