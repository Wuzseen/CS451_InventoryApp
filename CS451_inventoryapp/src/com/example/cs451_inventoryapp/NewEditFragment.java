package com.example.cs451_inventoryapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewEditFragment extends Fragment{
	String barcode;
	String sku;
	String description;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.newedit_fragment, container, false);
		
		return view;
	}
}
