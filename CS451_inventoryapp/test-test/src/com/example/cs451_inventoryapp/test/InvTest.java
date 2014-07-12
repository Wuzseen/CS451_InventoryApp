package com.example.cs451_inventoryapp.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.cs451_inventoryapp.*;

public class InvTest extends TestCase {
	public void testInvItemName() {
		InventoryItem i = new InventoryItem();
		Assert.assertEquals("DefaultInventoryItem", i.getName());
		i = new InventoryItem("WalrusSnacks");
		Assert.assertEquals("WalrusSnacks", i.getName());
	}
}
