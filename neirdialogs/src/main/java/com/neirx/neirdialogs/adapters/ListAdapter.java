package com.neirx.neirdialogs.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.enums.TextStyle;

/**
 * Created by Waide Shery on 25.07.2015.
 *
 */
public class ListAdapter extends BaseAdapter {
    LayoutInflater lInflater;
    TextView textView;
    private String[] items;
    private int itemTextColor;
    private float itemTextSize;
    private TextStyle itemTextStyle;
    private Typeface itemTextTypeface;
    private int itemBackgroundSelectorId;
    private int itemTextGravity;
    protected int itemPaddingStart, itemPaddingTop, itemPaddingEnd, itemPaddingBottom;

    public ListAdapter(String[] items, Context context){
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    public void setItemParam(int itemTextColor, float itemTextSize, TextStyle itemTextStyle,
                             Typeface itemTextTypeface, int itemTextGravity, int itemBackgroundSelectorId){
        this.itemTextColor = itemTextColor;
        this.itemTextSize = itemTextSize;
        this.itemTextStyle = itemTextStyle;
        this.itemTextTypeface = itemTextTypeface;
        this.itemBackgroundSelectorId = itemBackgroundSelectorId;
        this.itemTextGravity = itemTextGravity;
    }

    public void setItemTextPadding(int start, int top, int end, int bottom){
        itemPaddingStart = start;
        itemPaddingTop = top;
        itemPaddingEnd = end;
        itemPaddingBottom = bottom;
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
            view = lInflater.inflate(R.layout.adapter_listchoice, parent, false);
        }
        textView = (TextView) view.findViewById(R.id.textView);

        textView.setText(items[position]);
        setParam(textView);
        if(itemBackgroundSelectorId > -1) view.setBackgroundResource(itemBackgroundSelectorId);
        return view;
    }

    private void setParam(TextView textView){
        textView.setTextColor(itemTextColor);
        if(itemTextSize > 0) textView.setTextSize(itemTextSize);
        if(itemTextGravity > -1) textView.setGravity(itemTextGravity);
        if(itemTextTypeface != null && itemTextStyle != null) textView.setTypeface(itemTextTypeface, itemTextStyle.getValue());
        else if(itemTextTypeface != null) textView.setTypeface(itemTextTypeface);
        else if(itemTextStyle != null) textView.setTypeface(Typeface.DEFAULT, itemTextStyle.getValue());

        if(itemPaddingStart > -1) {
            if (Build.VERSION.SDK_INT >= 16) {
                textView.setPaddingRelative(itemPaddingStart, itemPaddingTop, itemPaddingEnd, itemPaddingBottom);
            } else {
                textView.setPadding(itemPaddingStart, itemPaddingTop, itemPaddingEnd, itemPaddingBottom);
            }
        }
    }
}
