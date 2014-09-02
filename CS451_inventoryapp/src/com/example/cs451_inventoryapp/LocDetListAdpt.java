package com.example.cs451_inventoryapp;

import java.util.ArrayList;

import com.example.cs451_inventorypackage.InventoryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LocDetListAdpt extends ArrayAdapter<InventoryItem>{
	ArrayList<InventoryItem> items;
	public LocDetListAdpt(Context context, int resource,
			ArrayList<InventoryItem> objects) {
		super(context, resource, objects);
		this.items = objects;	
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.location_list_item, null);
		}
		
		InventoryItem itemAtLoc = items.get(position);
		if(itemAtLoc!=null){
			TextView txtV = (TextView) view.findViewById(R.id.itematLoc);
			txtV.setText(itemAtLoc.getName());
		}
		
		return view;
	}
}
