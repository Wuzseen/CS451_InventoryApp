package com.example.cs451_inventoryapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class InventoryDialog extends DialogFragment{
	public Activity mActivity;
	public Dialog mDialog;
	String action = "";
	String current = "";
	RadioButton addBut;
	RadioButton subtractBut;
	RadioGroup radioGroup;
	EditText amount;
	TextView currentStock;
	Button ok;
	Button cancel;
	onSubmitListener mListener;
	
	interface onSubmitListener {
		void setOnSubmitListener(String arg);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.inventory_fragment);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
		ok = (Button) dialog.findViewById(R.id.ok);
		cancel = (Button) dialog.findViewById(R.id.cancel);
		currentStock = (TextView) dialog.findViewById(R.id.currentStock);
		amount = (EditText) dialog.findViewById(R.id.amountPicker);
		addBut = (RadioButton) dialog.findViewById(R.id.radio_add);
		subtractBut = (RadioButton) dialog.findViewById(R.id.radio_subtract);
		radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);
		
		currentStock.setText(current);
		
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
		
		ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int c = Integer.parseInt(current);
				int n = Integer.parseInt(amount.getText().toString());
				int f = 0;
				
				if(action.equalsIgnoreCase("A")){
					f = c + n;
				} else if(action.equalsIgnoreCase("S")){
					f = c - n;
				}
				mListener.setOnSubmitListener(Integer.toString(f));
				dismiss();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		return dialog;
	}
	
}
