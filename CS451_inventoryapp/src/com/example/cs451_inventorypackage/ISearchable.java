package com.example.cs451_inventorypackage;
/*
 * Handles handling of searching through inventory elements
 */
public interface ISearchable<T> {
	public boolean searchName(String searchString);
	public boolean searchId(int id);
	
	public T find(int id);
	public T find(String name);
}
