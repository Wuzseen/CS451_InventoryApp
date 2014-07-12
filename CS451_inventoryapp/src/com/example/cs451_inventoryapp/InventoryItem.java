package com.example.cs451_inventoryapp;

public class InventoryItem {
	private static int idCount = 1;
	
	private int id;
	public int getId() {
		return id;
	}
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private void Setup(String itemName) {
		this.id = idCount;
		idCount++;
		this.name = itemName;
	}
	
	public InventoryItem(String itemName) {
		this.Setup(itemName);
	}
	
	public InventoryItem() {
		this("DefaultInventoryItem");
	}
}
