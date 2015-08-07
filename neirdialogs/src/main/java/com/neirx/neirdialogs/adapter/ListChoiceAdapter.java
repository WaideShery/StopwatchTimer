package com.neirx.neirdialogs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.neirx.neirdialogs.R;

/**
 * Created by Waide Shery on 25.07.2015.
 */
public class ListChoiceAdapter extends BaseAdapter {
    LayoutInflater lInflater;
    TextView textView;
    String[] items;

    public ListChoiceAdapter(String[] items){
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.adapter_singlechoice, parent, false);
        }
        textView = (TextView) view.findViewById(R.id.textView);

        for (String item : items){
            textView.setText(item);
        }
        return view;
    }
}
