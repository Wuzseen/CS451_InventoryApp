package com.example.cs451_inventorypackage;

import java.util.ArrayList;
import java.util.Iterator;

public class FindResult<T> implements Iterable<T> {
	private ArrayList<T> items;
	public ArrayList<T> getItems() {
		return items;
	}
	public void addItem(T item) {
		this.items.add(item);
	}
	public void addItems(ArrayList<T> items) {
		this.items.addAll(items);
	}
	
	public boolean successful() {
		// Has more than 0 results
		return items.size() > 0;
	}
	
	public boolean singleResult() {
		// If there's just one result, useful to assert that results are unique.
		return items.size() == 1;
	}
	
	public int size() {
		return items.size();
	}
	
	public FindResult () {
		this.items = new ArrayList<T>();
	}
	
	@Override
	public Iterator<T> iterator() {
		return this.items.iterator();
	}
}
