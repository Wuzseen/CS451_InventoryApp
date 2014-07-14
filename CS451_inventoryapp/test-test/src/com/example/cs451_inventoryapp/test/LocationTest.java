package com.example.cs451_inventoryapp.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.cs451_inventoryapp.*;

public class LocationTest extends TestCase{
	public void testLocationSetup() {
		Location l = new Location();
		Assert.assertEquals("DefaultLocationName", l.getName());
		l = new Location("WalrusSnackLocation");
		Assert.assertEquals("WalrusSnackLocation", l.getName());
	}

	public void testLocationId() {
		Location l1 = new Location();
		Location l2 = new Location();
		
		Assert.assertEquals(l2.getId(), l1.getId() + 1);
	}
	
	public void testAddingItem() {
		Location l  = new Location();
		InventoryItem i = new InventoryItem();
		Assert.assertEquals(0, l.Count());
		l.AddItem(i);
		Assert.assertEquals(1, l.Count());
	}
	
	public void testItemRemovalFromId() {
		Location l  = new Location();
		InventoryItem i = new InventoryItem();
		Assert.assertEquals(0, l.Count());
		l.AddItem(i);
		Assert.assertEquals(1, l.Count());
		l.RemoveItem(i.getId());
		Assert.assertFalse(l.getItemList().contains(i));
	}
	
	public void testItemRemovalFromObject() {
		Location l  = new Location();
		InventoryItem i = new InventoryItem();
		Assert.assertEquals(0, l.Count());
		l.AddItem(i);
		Assert.assertEquals(1, l.Count());
		l.RemoveItem(i);
		Assert.assertFalse(l.getItemList().contains(i));
	}
	
	public void testMaxSize() {
		Location l = new Location();
		Assert.assertEquals(-1, l.getMaxSize());
		l.setMaxSize(5);
		Assert.assertEquals(5, l.getMaxSize());
	}
	
	public void testSpaceAvailable() {
		Location l = new Location();
		Assert.assertTrue(l.SpaceAvailable());
		l.setMaxSize(1);
		Assert.assertTrue(l.SpaceAvailable());
		l.AddItem(new InventoryItem());
		Assert.assertFalse(l.SpaceAvailable());
	}
}
