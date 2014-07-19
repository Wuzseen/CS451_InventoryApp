package com.example.cs451_inventorypackage;

public class InventoryItem {
	private static Integer idCount = 1;
	
	private Integer id;
	public Integer getId() {
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
	public boolean equals(Integer itemId) {
		return this.getId().equals(itemId);
	}
	public boolean equals(String name) {
		return this.name.equals(name);
	}
	public boolean equals(Barcode code) {
		return this.barcode.equals(code);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(String.class.equals(obj.getClass())) {
			return this.equals((String)obj);
		} else if (Integer.class.equals(obj.getClass())) {
			return this.equals((Integer)obj);
		} else if (Barcode.class.equals(obj.getClass())) {
			return this.equals((Barcode)obj);
		}
		
		return false;
	}
	
}
