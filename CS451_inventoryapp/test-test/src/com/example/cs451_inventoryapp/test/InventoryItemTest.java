package com.example.cs451_inventoryapp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.example.cs451_inventoryapp.*;
import com.example.cs451_inventorypackage.InventoryItem;

public class InventoryItemTest extends AndroidTestCase {
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
	
	public void testItemSerialize() {
		InventoryItem item1 = new InventoryItem("Test");
		String filename = "testserial.invitem";
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		Context c = this.getContext();
		try {
			fos = c.openFileOutput(filename,Context.MODE_PRIVATE);//new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(item1);
		} catch (Exception ex) {
			Assert.fail(String.format("Could not serialize: %s", ex.toString()));
		}
		
		// Make sure file was saved
		boolean exists = false;
		for(String s : c.fileList()) {
			if(s.equals(filename)) {
				exists = true;
				break;
			}
		}
		Assert.assertTrue(exists);
	}
	
	public void testItemDeserialize() {
		
	}
}
