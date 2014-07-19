package com.example.cs451_inventorypackage;

import java.util.ArrayList;

public class Location implements ISearchable<Location> {
	private static int idCount = 1;

	private int id;
	public int getId() {
		return id;
	}
	
	private Barcode barcode;
	public Barcode getBarcode() {
		return this.barcode;
	}
	public void setBarcode(Barcode bc) {
		this.barcode = bc;
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
	
	private ArrayList<Location> subLocations;
	public ArrayList<Location> getSubLocations() {
		return this.subLocations;
	}
	public void addSubLocation(Location l) {
		this.subLocations.add(l);
	}
	public boolean removeSubLocation(int index) {
		return this.subLocations.remove(index) != null;
	}
	public boolean removeSubLocation(Location l) {
		// false if it doesn't find it
		return this.subLocations.remove(l);
	}

	// Max number of items that can fit, -1 is a sentinel value saying it's infinite
	private int maxSize;
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	public Location() {
		this.subLocations = new ArrayList<Location>();
		this.itemList = new ArrayList<InventoryItem>();
		this.maxSize = -1;
		this.name = "DefaultLocationName";
		this.id = idCount;
		idCount++;
	}
	
	public Location(String locationName) {
		this();
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
	
	public InventoryItem getItem(int index) {
		return this.itemList.get(index);
	}
	
	public InventoryItem getItemWithId(int id) {
		for(int i = 0; i < this.Count(); i++) {
			InventoryItem item = this.itemList.get(i);
			if(item.getId() == id) {
				return item;
			}
		}
		return null;
	}
	
	public boolean Contains(int id) {
		for(int i = 0; i < this.Count(); i++) {
			if(this.itemList.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean Contains(InventoryItem item) {
		return this.itemList.contains(item);
	}
	
	// Num of items in location
	public int Count() {
		return this.itemList.size();	
	}
	
	public boolean SpaceAvailable() {
		return (this.maxSize == -1 || this.Count() < this.maxSize);
	}
	
	@Override
	public boolean searchName(String searchString) {
		return this.name.equals(searchString);
	}
	@Override
	public boolean searchId(int id) {
		return this.id == id;
	}
	@Override
	public Location find(int id) {
		if(this.id == id) {
			return this;
		}
		for(Location l : this.subLocations) {
			return l.find(id);
		}
		return null;
	}
	@Override
	public Location find(String name) {
		if(this.name.equals(name)) {
			return this;
		}
		for(Location l : this.subLocations) {
			return l.find(name);
		}
		return null;
	}
	
	public InventoryItem findItemById(int id) {
		for(InventoryItem i : this.itemList) {
			if(i.getId() == id) {
				return i;
			}
		}
		for(Location l : this.subLocations) {
			return l.findItemById(id);
		}
		return null;
	}
	
	public ArrayList<InventoryItem> findItemsWithName(String name) {
		ArrayList<InventoryItem> ret = new ArrayList<InventoryItem>();
		assembleList(ret, name);
		return ret;
	}
	private void assembleList(ArrayList<InventoryItem> list, String search) {
		for(InventoryItem i : this.itemList) {
			if(i.getName().equals(name)) {
				list.add(i);
			}
		}
		for(Location l : this.subLocations) {
			l.assembleList(list, name);
		}
	}
}