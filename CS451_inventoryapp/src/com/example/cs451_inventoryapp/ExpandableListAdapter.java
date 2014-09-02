package com.example.cs451_inventoryapp;

import java.util.HashMap;
import java.util.List;

import com.example.cs451_inventorypackage.InventoryItem;
import com.example.cs451_inventorypackage.Location;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
/**
 * This class was created using the tutorial here
 * http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 * This is a 2-part expandable list that displays items and locations found 
 * from a search query.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Context _context;
	private List<String> _listDataHeader;
	private HashMap<String, List<?>> _listDataChild;
	
	public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String,List<?>> listChildData){
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	};
	
	@Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
	
	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.search_results_list_group, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.searchHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
			String childText = null;
			if(groupPosition == 0){
				InventoryItem item = (InventoryItem) getChild(groupPosition,childPosition);
				childText = item.getName();//(String) getChild(groupPosition, childPosition);
			}
			if(groupPosition == 1){
				Location loc = (Location) getChild(groupPosition,childPosition);
				childText = loc.getName();
			}
		 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.search_results_list_item, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView
	                .findViewById(R.id.searchListItem);
	 
	        txtListChild.setText(childText);
	        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}