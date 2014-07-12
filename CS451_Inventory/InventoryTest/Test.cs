using NUnit.Framework;
using System;
using Inventory;

namespace InventoryTest
{
	[TestFixture ()]
	public class Test
	{
		[Test ()]
		public void ItemNameIsValid ()
		{
			string testName = "WalrusSnacks";
			InventoryItem i = new InventoryItem ();
			Assert.AreEqual("InvItem", i.Name);
			i = new InventoryItem(testName);
			Assert.AreEqual(testName, i.Name);
		}
	}
}

