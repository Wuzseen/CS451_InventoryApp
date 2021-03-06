package com.example.cs451_inventorypackage;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable, ISearchable<Location> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Integer idCount = 1;

	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(int val) {
		id = val;
		if(val > idCount) {
			idCount = val + 1;
		}
	}
	
	private String barcode;
	public String getBarcode() {
		return this.barcode;
	}
	public void setBarcode(String bc) {
		this.barcode = bc;
	}
	
	// Is not setable outside of Add and Remove items
	private ArrayList<InventoryItem> itemList;
	public ArrayList<InventoryItem> getItemList() {
		return itemList;
	}
	public void setItems(ArrayList<InventoryItem> items) {
		itemList = items;
	}
	public String itemSQLString() {
		String ret = itemList.get(0).getId().toString();
		for(int i = 1; i < itemList.size(); i++) {
			ret += "," + itemList.get(i).getId().toString();
		}
		return ret;
	}

	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private ArrayList<Integer> subIds;
	public void addSubId(Integer id) {
		if(subIds == null) {
			subIds = new ArrayList<Integer>();
		}
		subIds.add(id);
	}
	
	public void LoadFromSubIDs() {
		if (subIds == null){
			return;
		}
		InventoryManager im = InventoryManager.Instance();
		for(Integer i : subIds) {
			this.addSubLocation(im.locationLookup(i));
		}
	}
	
	private ArrayList<Location> subLocations;
	public ArrayList<Location> getSubLocations() {
		return this.subLocations;
	}
	public String subLocationSQLString() {
		if(subLocations.size() == 0) {
			return "";
		}
		String ret = subLocations.get(0).getId().toString();
		for(int i = 1; i < subLocations.size(); i++) {
			ret += "," + subLocations.get(i).getId().toString();
		}
		return ret;
	}
	
	public boolean isRoot = true;
	
	public void addSubLocation(Location l) {
		this.subLocations.add(l);
		l.isRoot = false;
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
	
	public void addItem(InventoryItem item) {
		this.itemList.add(item);
	}
	
	public void removeItem(InventoryItem item) {
		this.itemList.remove(item);
	}

	public void removeItem(int itemId) {
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
		for(int i = 0; i < this.count(); i++) {
			InventoryItem item = this.itemList.get(i);
			if(item.getId() == id) {
				return item;
			}
		}
		return null;
	}
	
	public boolean contains(int id) {
		for(int i = 0; i < this.count(); i++) {
			if(this.itemList.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(InventoryItem item) {
		return this.itemList.contains(item);
	}
	
	// Num of items in location
	public int count() {
		return this.itemList.size();	
	}
	
	public boolean spaceAvailable() {
		return (this.maxSize == -1 || this.count() < this.maxSize);
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
	public ArrayList<Location> find(String name) {
		ArrayList<Location> ret = new ArrayList<Location>();
		this.assembleLocationListMatching(ret,name);
		return ret;
	}
	@Override
	public ArrayList<Location> findContaining(String name) {
		ArrayList<Location> ret = new ArrayList<Location>();
		assembleLocationListContaining(ret,name);
		return ret;
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
		assembleItemList(ret, name);
		return ret;
	}
	public ArrayList<InventoryItem> findItemsWithBarcode(Barcode code) {
		ArrayList<InventoryItem> ret = new ArrayList<InventoryItem>();
		assembleItemList(ret, code);
		return ret;
	}
	private void assembleItemList(ArrayList<InventoryItem> list, Object search) {
		for(InventoryItem i : this.itemList) {
			if(i.equals(search)) {
				list.add(i);
			}
		}
		for(Location l : this.subLocations) {
			l.assembleItemList(list, search);
		}
	}
	private void assembleLocationListContaining(ArrayList<Location> list, String search) {
		if(this.name.contains(search)) {
			list.add(this);
		}
		for(Location l : this.subLocations) {
			l.assembleLocationListContaining(list, search);
		}
	}
	private void assembleLocationListMatching(ArrayList<Location> list, String search) {
		if(this.name.equals(search)) {
			list.add(this);
		}
		for(Location l : this.subLocations) {
			l.assembleLocationListContaining(list, search);
		}
	}
	public ArrayList<InventoryItem> findItemsWithBarcode(String barcode) {
		ArrayList<InventoryItem> ret = new ArrayList<InventoryItem>();
		assembleItemList(ret, barcode);
		return ret;
	}
}
