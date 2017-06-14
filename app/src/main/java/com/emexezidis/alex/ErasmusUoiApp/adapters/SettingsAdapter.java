package com.emexezidis.alex.ErasmusUoiApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.SimpleSettingItem;

import java.util.ArrayList;

public class SettingsAdapter extends ArrayAdapter<SimpleSettingItem> {

    private ArrayList<SimpleSettingItem> mSettingItems;

    public SettingsAdapter(Context context, ArrayList<SimpleSettingItem> settingItems) {
        super(context, 0, settingItems);
        mSettingItems = settingItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        String title = getItem(position).getTitle();
        String data = getItem(position).getData();

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.two_line_list_layout_for_settings, parent, false);
        }

        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.stepTitle);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.stepInfoDescription);

        // Populate the data into the template view using the data object
        // AND add the number of the step in front of the text:
        tvTitle.setText(title);
        tvDescription.setText(data);

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public SimpleSettingItem getItem(int position) {
        return mSettingItems.get(position);
    }
}
