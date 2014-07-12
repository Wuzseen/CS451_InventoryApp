using System;
using InventorySystem;

namespace InventoryConsole
{
	class MainClass
	{
		public static void Main(string[] args)
		{
			Console.WriteLine("Hello World!");
			InventoryItem i = new InventoryItem("Herro");
			Console.WriteLine(i.Name);
		}
	}
}
