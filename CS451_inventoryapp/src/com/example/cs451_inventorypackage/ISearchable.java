package com.example.cs451_inventorypackage;
/*
 * Handles handling of searching through inventory elements
 */
public interface ISearchable {
	public boolean nameSearch(String searchString);
	public boolean idSearch(int id);
}
