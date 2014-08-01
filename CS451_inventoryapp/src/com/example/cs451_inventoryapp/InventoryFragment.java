package com.example.cs451_inventoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InventoryFragment extends Fragment{
	NumberPicker amountpicker;
	String action = "";
	RadioButton addBut;
	RadioButton subtractBut;
	RadioGroup radioGroup;
	EditText amount;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.inventory_fragment, container, false);
		amount = (EditText) view.findViewById(R.id.amountPicker);
		addBut = (RadioButton) view.findViewById(R.id.radio_add);
		subtractBut = (RadioButton) view.findViewById(R.id.radio_subtract);
		radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
		amount = (EditText) view.findViewById(R.id.amountPicker);
		return view;
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// Check which radio button clicked
		    	switch(checkedId){
		    	case R.id.radio_add:
		    		if (addBut.isChecked())
		    			action = "A"; //add
		    		break;
		    	case R.id.radio_subtract:
		    		if (subtractBut.isChecked())
		    			action = "S"; //subtract
		    		break;
		    	}
			}
		});
		
	}
}
