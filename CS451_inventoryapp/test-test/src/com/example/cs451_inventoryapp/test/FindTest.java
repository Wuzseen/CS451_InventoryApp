package com.example.cs451_inventoryapp.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.cs451_inventoryapp.*;
import com.example.cs451_inventorypackage.*;

import org.junit.Test;

public class FindTest extends TestCase{
	public void testFindItemByName() {
		InventoryManager manager = new InventoryManager();
		Location testRoot = new Location("Test");
		Location subLocation = new Location("Sub");
		InventoryItem i1,i2,i3,i4;
		i1 = new InventoryItem("TestItem");
		i2 = new InventoryItem("TestItem");
		i3 = new InventoryItem("TestItem2");
		i4 = new InventoryItem("TestItem3");
		testRoot.AddItem(i1);
		testRoot.addSubLocation(subLocation);
		subLocation.AddItem(i2);
		subLocation.AddItem(i3);
		subLocation.AddItem(i4);
		manager.AddRootLocation(testRoot);
		FindResult<InventoryItem> items = manager.findByName("TestItem");
		Assert.assertEquals(2, items.size());
	}
	
	public void testFindItemByID() {
		InventoryManager manager = new InventoryManager();
		Location testRoot = new Location("Test");
		Location subLocation = new Location("Sub");
		InventoryItem i1,i2,i3,i4;
		i1 = new InventoryItem("TestItem");
		i2 = new InventoryItem("TestItem");
		i3 = new InventoryItem("TestItem2");
		i4 = new InventoryItem("TestItem3");
		testRoot.AddItem(i1);
		testRoot.addSubLocation(subLocation);
		subLocation.AddItem(i2);
		subLocation.AddItem(i3);
		subLocation.AddItem(i4);
		manager.AddRootLocation(testRoot);
		FindResult<InventoryItem> items = manager.findByUniqueId(i1.getId());
		Assert.assertTrue(items.singleResult());
		Assert.assertEquals(i1, items.getItems().get(0));
		items = manager.findByUniqueId(i4.getId());
		Assert.assertTrue(items.singleResult());
		Assert.assertEquals(i4, items.getItems().get(0));
	}
	
	public void testFindLocationContainingString() {
		InventoryManager manager = new InventoryManager();
		Location testRoot = new Location("Test");
		Location subLocation1 = new Location("Sub1");
		Location subLocation2 = new Location("Sub2");
		Location subLocation3 = new Location("Sub3");
		manager.AddRootLocation(testRoot);
		testRoot.addSubLocation(subLocation1);
		subLocation1.addSubLocation(subLocation2);
		manager.AddRootLocation(subLocation3);
		FindResult<Location> results = manager.findContainingLocationByName("Sub");
		Assert.assertEquals(3, results.size());
		results = manager.findContainingLocationByName("Test");
		Assert.assertEquals(1, results.size());
	}
	
	public void testFindLocationMatchingString() {
		InventoryManager manager = new InventoryManager();
		Location testRoot = new Location("Test");
		Location subLocation1 = new Location("Sub1");
		Location subLocation2 = new Location("Sub2");
		Location subLocation3 = new Location("Sub3");
		manager.AddRootLocation(testRoot);
		testRoot.addSubLocation(subLocation1);
		subLocation1.addSubLocation(subLocation2);
		manager.AddRootLocation(subLocation3);
		FindResult<Location> results = manager.findMatchingLocationByName("Sub1");
		Assert.assertEquals(1, results.size());
		results = manager.findContainingLocationByName("Sub2");
		Assert.assertEquals(1, results.size());
	}
}
