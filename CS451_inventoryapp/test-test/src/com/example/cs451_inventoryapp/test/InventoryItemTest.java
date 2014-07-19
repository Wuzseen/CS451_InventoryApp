package com.example.cs451_inventoryapp.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.cs451_inventoryapp.*;
import com.example.cs451_inventorypackage.InventoryItem;

public class InventoryItemTest extends TestCase {
	public void testInvItemName() {
		InventoryItem i = new InventoryItem();
		Assert.assertEquals("DefaultInventoryItem", i.getName());
		i = new InventoryItem("WalrusSnacks");
		Assert.assertEquals("WalrusSnacks", i.getName());
	}
	
	public void testItemId() {
		// Inventory item ids should increment by 1 after each new inventory item
		InventoryItem item1  = new InventoryItem();
		InventoryItem item2 = new InventoryItem();
		
		Assert.assertEquals((Integer)item2.getId(), (Integer)(item1.getId() + 1));
	}
	
	public void testItemEquality() {
		// Evaluated based on id.
		InventoryItem item1  = new InventoryItem();
		InventoryItem item3 = item1;
		InventoryItem item2 = new InventoryItem();
		
		Assert.assertFalse(item1.equals(item2));
		Assert.assertTrue(item1.equals(item3));
	}
}
