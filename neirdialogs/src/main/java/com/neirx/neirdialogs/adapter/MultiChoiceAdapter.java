package com.neirx.neirdialogs.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.dialog.SelectDialogFragment;
import com.neirx.neirdialogs.dialog.TextStyle;
import com.neirx.neirdialogs.interfaces.ChoiceItem;

import java.util.List;

/**
 * Created by Waide Shery on 25.07.2015.
 */
public class MultiChoiceAdapter extends BaseAdapter {
    private LayoutInflater lInflater;
    private Context context;
    //private CheckBox checkBox;
    private List<ChoiceItem> listItems;
    private int textColor;
    private float textSize;
    private TextStyle textStyle;
    private Typeface textTypeface;
    @DrawableRes
    private int flagSelector, bgSelector;
    private SelectDialogFragment.SelectItemListener listener = null;
    private int requestCode = -1;
    private boolean[] arrayItemsChecked;

    public MultiChoiceAdapter(List<ChoiceItem> listItems, Context context, int textColor, float textSize,
                              TextStyle textStyle, Typeface textTypeface, int flagSelector, int bgSelector,
                              SelectDialogFragment.SelectItemListener listener, int requestCode) {
        this.listItems = listItems;
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.textColor = textColor;
        this.textSize = textSize;
        this.textStyle = textStyle;
        this.textTypeface = textTypeface;
        this.flagSelector = flagSelector;
        this.bgSelector = bgSelector;
        this.listener = listener;
        this.requestCode = requestCode;
        arrayItemsChecked = new boolean[listItems.size()];
        for (int i = 0; i < listItems.size(); i++) {
            arrayItemsChecked[i] = listItems.get(i).isChecked();
        }
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.adapter_multichoice, parent, false);
        }
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        final ChoiceItem item = listItems.get(position);
        checkBox.setText(item.getTitle());

        checkBox.setTextColor(textColor);
        checkBox.setTextSize(textSize);
        checkBox.setTypeface(textTypeface, textStyle.getValue());
        checkBox.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(flagSelector), null);
        checkBox.setBackgroundResource(bgSelector);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayItemsChecked[position] = checkBox.isChecked();
                if (listener != null) {
                    listener.onFinishSelectDialog(requestCode, arrayItemsChecked);
                }
            }
        });
        checkBox.setChecked(item.isChecked());
        return view;
    }
}
