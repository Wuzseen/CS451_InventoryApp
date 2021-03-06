package com.example.cs451_inventorypackage;

import java.util.ArrayList;

import android.util.Log;

import com.example.cs451_inventorypackage.InventoryManager;

import org.apache.http.NameValuePair; 
import org.apache.http.message.BasicNameValuePair;

public class SQLoader {
	private static SQLHandler handler;
	
	public static ArrayList<InventoryItem> allItems() {
		if(handler == null) {
			handler = new SQLHandler();
		}
    	String html = handler.makePost("http://www.timjbday.com/classtuff/findItem.php", new ArrayList<NameValuePair>());
    	String[] allItems = html.replace("\n", "").split("<br>");
    	InventoryManager im = InventoryManager.Instance();
    	ArrayList<InventoryItem> ret = new ArrayList<InventoryItem>();
    	for(String itemString : allItems) {
    		String[] components = itemString.split(",");
    		InventoryItem i = new InventoryItem();
    		im.addItem(i);
    		Log.i("the ID", components[0]);
    		String s = components[0].trim();
    		Log.i("String length is ", Integer.toString(s.length()));
    		int theInt = Integer.parseInt(s);
    		i.setId(theInt);
    		i.setName(components[1]);
    		i.setCount(Integer.parseInt(components[2]));
    		i.setBarcode(components[3]);
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
			ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
			// These are the defined attributes for the PHP pages
			// inb4sqlinjections
			args.add(new BasicNameValuePair("ID",i.getId().toString()));
			args.add(new BasicNameValuePair("Name",i.getName()));
			args.add(new BasicNameValuePair("Count",i.getCount().toString()));
			args.add(new BasicNameValuePair("Bar",i.getBarcode().toString()));
			handler.makePost(address, args);
		}
	}
	
	public static void deleteItem(Integer id) {
		if(handler == null) {
			handler = new SQLHandler();
		}
		String address = "http://www.timjbday.com/classtuff/rmItem.php";
		ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
		args.add(new BasicNameValuePair("ID",id.toString()));
		handler.makePost(address, args);
	}
	
	public static void deleteLocation(Integer id) {
		if(handler == null) {
			handler = new SQLHandler();
		}
		String address = "http://www.timjbday.com/classtuff/rmLoc.php";
		ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
		args.add(new BasicNameValuePair("ID",id.toString()));
		handler.makePost(address, args);
	}
	
	public static ArrayList<Location> allLocations() {
		if(handler == null) {
			handler = new SQLHandler();
		}
    	String html = handler.makePost("http://www.timjbday.com/classtuff/findLoc.php", new ArrayList<NameValuePair>());
    	String[] allItems = html.replace("\n", "").split("<br>");
    	ArrayList<Location> ret = new ArrayList<Location>();
    	InventoryManager im = InventoryManager.Instance();
    	for(String itemString : allItems) {
    		String[] components = itemString.split(",");
    		Location i = new Location();
    		i.setId(Integer.parseInt(components[0]));
    		im.addLocation(i);
    		i.setName(components[1]);
    		i.setBarcode(components[2]);
    		i.isRoot = components[3].equals("1");
    		boolean sublocations = true;
    		for(int z = 4; z < components.length; z++) {
    			if(components[z].equals("#SPLIT#")) { // This is a sentinel reserved to divide between locations and items;
    				sublocations = false;
    				continue;
    			}
    			if(sublocations) {
    				if(components[z].equals(""))
    					continue;
    				i.addSubId(Integer.parseInt(components[z]));
    			} else {
    				i.addItem(im.itemLookup(Integer.parseInt(components[z])));
    			}
    		}
    		
    		ret.add(i);
    	}
    	for(Location l : ret) {
    		l.LoadFromSubIDs();
    	}
    	for(Location l: ret) {
    		if(l.isRoot) {
    			im.addRootLocation(l);
    		}
    	}
		return ret;
	}
	
	public static void saveLocation(ArrayList<Location> locations) {
		if(handler == null) {
			handler = new SQLHandler();
		}
		String address = "http://www.timjbday.com/classtuff/addLoc.php";
		for(Location l : locations) {
			ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
			// These are the defined attributes for the PHP pages
			// inb4sqlinjections
			args.add(new BasicNameValuePair("ID",l.getId().toString()));
			args.add(new BasicNameValuePair("Name",l.getName()));
			args.add(new BasicNameValuePair("Subs",l.subLocationSQLString()));
			args.add(new BasicNameValuePair("Items",l.itemSQLString()));
			args.add(new BasicNameValuePair("Barcode",l.getBarcode()));
			args.add(new BasicNameValuePair("Root",l.isRoot == true ? "1" : "0"));
			handler.makePost(address, args);
		}
	}
}
