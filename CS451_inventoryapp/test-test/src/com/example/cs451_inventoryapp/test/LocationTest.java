package com.example.cs451_inventoryapp.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.Location;

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
		
		Assert.assertEquals((Integer)l2.getId(), (Integer)(l1.getId() + 1));
	}
	
	public void testAddingItem() {
		Location l  = new Location();
		InventoryItem i = new InventoryItem();
		Assert.assertEquals(0, l.count());
		l.addItem(i);
		Assert.assertEquals(1, l.count());
	}
	
	public void testContains() {
		Location l = new Location();
		InventoryItem i = new InventoryItem();
		l.addItem(i);
		Assert.assertTrue(l.contains(i.getId()));
		Assert.assertTrue(l.contains(i));
		InventoryItem falseItem = new InventoryItem();
		Assert.assertFalse(l.contains(falseItem));
		Assert.assertFalse(l.contains(falseItem.getId()));
	}
	
	public void testGetItem() {
		Location l = new Location();
		InventoryItem i1 = new InventoryItem();
		InventoryItem i2 = new InventoryItem();
		l.addItem(i1);
		Assert.assertNull(l.getItemWithId(i2.getId()));
		Assert.assertEquals(i1, l.getItemWithId(i1.getId()));
		Assert.assertEquals(i1, l.getItem(0));
	}
	
	public void testItemRemovalFromId() {
		Location l  = new Location();
		InventoryItem i = new InventoryItem();
		Assert.assertEquals(0, l.count());
		l.addItem(i);
		Assert.assertEquals(1, l.count());
		l.removeItem(i.getId());
		Assert.assertFalse(l.getItemList().contains(i));
	}
	
	public void testItemRemovalFromObject() {
		Location l  = new Location();
		InventoryItem i = new InventoryItem();
		Assert.assertEquals(0, l.count());
		l.addItem(i);
		Assert.assertEquals(1, l.count());
		l.removeItem(i);
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
		Assert.assertTrue(l.spaceAvailable());
		l.setMaxSize(1);
		Assert.assertTrue(l.spaceAvailable());
		l.addItem(new InventoryItem());
		Assert.assertFalse(l.spaceAvailable());
	}
}
