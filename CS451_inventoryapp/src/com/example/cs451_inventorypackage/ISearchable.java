package com.example.cs451_inventorypackage;

import java.util.ArrayList;

/*
 * Handles handling of searching through inventory elements
 */
public interface ISearchable<T> {
	public boolean searchName(String searchString);
	public boolean searchId(int id);
	
	public T find(int id);
	public ArrayList<T> find(String name);
	public ArrayList<T> findContaining(String name);
}
