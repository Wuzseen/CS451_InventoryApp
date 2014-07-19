package com.example.cs451_inventorypackage;

public class InventoryItem implements ISearchable {
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
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private void Setup(String itemName) {
		this.id = idCount;
		InventoryItem.idCount++;
		this.name = itemName;
	}
	
	public InventoryItem(String itemName) {
		this.Setup(itemName);
	}
	
	public InventoryItem() {
		this("DefaultInventoryItem");
	}
	
	public boolean equals(InventoryItem i) {
		return this.getId() == i.getId();
	}
	@Override
	public boolean nameSearch(String searchString) {
		// TODO Auto-generated method stub
		return this.name.equals(searchString);
	}
	@Override
	public boolean idSearch(int id) {
		// TODO Auto-generated method stub
		return this.id == id;
	}

	public boolean barcodeSearch(Barcode code) {
		// TODO Auto-generated method stub
		return this.barcode.equals(code);
	}
}
