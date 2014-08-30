package com.example.cs451_inventorypackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class InventoryItem implements Serializable, ISerialize {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Integer idCount = 1;
	
	private Integer count;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer i) {
		count = i;
	}
	public void addCount(Integer i) {
		count += i;
	}
	public void subtractCount(Integer i)  {
		count -= i;
	}
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(int val) {
		id = val;
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
		this.count = 1;
	}
	
	public InventoryItem(String itemName) {
		this.Setup(itemName);
	}
	
	public InventoryItem(String itemName, Integer num) {
		this.Setup(itemName);
		this.count = num;
	}
	
	public InventoryItem(Integer num) {
		this();
		this.count = num;
	}
	
	public InventoryItem() {
		this("DefaultInventoryItem");
	}
	
	public boolean equals(InventoryItem i) {
		return this.getId().intValue() == i.getId().intValue();
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
	
	public boolean SerializeItem(String filename, Context c) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = c.openFileOutput(filename,Context.MODE_PRIVATE);//new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			return true;
		} catch (Exception ex) {
			System.out.println(String.format("Could not serialize: %s", ex.toString()));
			return false;
		}
	}
	
	public static InventoryItem Deserialize(String filename, Context c) {
		InventoryItem i = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = c.openFileInput(filename);
			in = new ObjectInputStream(fis);
			i = (InventoryItem)in.readObject();
		} catch(Exception ex) {
			System.out.println(String.format("Could not deserialize object: %s", ex.toString()));
		}
		return i;
	}
}
