using System;

namespace InventorySystem {
	public class InventoryItem {
		private string name;
		public string Name {
			get { return name; }
			set { name = value; }
		}

		private static int IDCount = 1;
		private int id;
		public int ID {
			get { return id; }
		}

		private void Setup() {
			id = IDCount;
			IDCount++;
		}

		public InventoryItem () {
			Setup();
			name = "InvItem";
		}

		public InventoryItem(string name) {
			Setup();
			this.name = name;
		}
	}
}

