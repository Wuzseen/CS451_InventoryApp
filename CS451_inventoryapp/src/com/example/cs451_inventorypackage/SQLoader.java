package com.example.cs451_inventorypackage;

import java.util.ArrayList;

public class SQLoader {
	private static SQLHandler handler;
	
	public static ArrayList<InventoryItem> allItems() {
		if(handler == null) {
			handler = new SQLHandler();
		}
    	String html = handler.makePost("http://www.timjbday.com/classtuff/findItem.php", null);
    	String[] allItems = html.split("<br>");
    	ArrayList<InventoryItem> ret = new ArrayList<InventoryItem>();
    	for(String itemString : allItems) {
    		String[] components = itemString.split(",");
    		InventoryItem i = new InventoryItem();
    		i.setId(Integer.parseInt(components[0]));
    		i.setName(components[1]);
    		i.setCount(Integer.parseInt(components[2]));
    		
    		ret.add(i);
    	}
		return ret;
	}
	
	public static void saveItems(ArrayList<InventoryItem> items) {
		if(handler == null) {
			handler = new SQLHandler();
		}
		String address = "http://www.timjbday.com/classtuff/addItem.php";
		for(InventoryItem i : items) {
			ArrayList<String> args = new ArrayList<String>();
		}
	}
}
