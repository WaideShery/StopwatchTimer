package com.neirx.stopwatchtimer.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.SettingItem;

import java.util.ArrayList;
import java.util.TreeSet;

public class SettingsAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    private ArrayList<SettingItem> mData = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<>();

    private LayoutInflater mInflater;

    public SettingsAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final SettingItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final SettingItem item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public SettingItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int rowType = getItemViewType(position);
        TextView tvTitle;
        TextView tvSum;
        CheckBox checkBox;
        SettingItem item = getItem(position);
        switch (rowType) {
            case TYPE_ITEM:
                convertView = mInflater.inflate(R.layout.adapter_pref_item, null);
                tvTitle = (TextView) convertView.findViewById(R.id.title);
                tvSum = (TextView) convertView.findViewById(R.id.summary);
                checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                String title = item.getTitle();
                String summary = item.getSummary();
                tvTitle.setText(title);
                if(summary != null){
                    tvSum.setText(summary);
                } else {
                    tvSum.setVisibility(View.GONE);
                }
                if(item.hasCheckBox()){
                    checkBox.setChecked(item.isChecked());
                } else {
                    checkBox.setVisibility(View.GONE);
                }
                if(getItemViewType(position+1) == TYPE_HEADER){
                    View divider = convertView.findViewById(R.id.dividerItem);
                    divider.setVisibility(View.GONE);
                }
                break;
            case TYPE_HEADER:
                convertView = mInflater.inflate(R.layout.adapter_pref_header, null);
                tvTitle = (TextView) convertView.findViewById(R.id.title);
                tvTitle.setText(item.getTitle());
                break;
        }

        return convertView;
    }

}
