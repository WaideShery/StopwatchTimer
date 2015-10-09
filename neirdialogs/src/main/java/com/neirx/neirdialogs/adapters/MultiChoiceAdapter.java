package com.neirx.neirdialogs.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.Statical;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waide Shery on 25.07.2015.
 *
 */
public class MultiChoiceAdapter extends ChoiceAdapter {
    private LayoutInflater lInflater;
    private Context context;
    //private CheckBox checkBox;
    private List<String> listItems;
    List<Integer> listCheckedItemsPos;

    public Integer[] getCheckedItemsPos(){
        if(listCheckedItemsPos == null){
            return null;
        }
        return listCheckedItemsPos.toArray(new Integer[listCheckedItemsPos.size()]);
    }

    public MultiChoiceAdapter(List<String> listItems, int[] checkedItemsPos, Context context) {
        this.listItems = listItems;
        this.listCheckedItemsPos = new ArrayList<>();
        if (checkedItemsPos != null) {
            addToCheckedList(checkedItemsPos);
        }
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void addToCheckedList(int[] checkedItemsPos) {
        for (int item : checkedItemsPos) {
            if (!listCheckedItemsPos.contains(item)) {
                listCheckedItemsPos.add(item);
            }
        }
    }

    private void addToCheckedList(int itemPos) {
        if (!listCheckedItemsPos.contains(itemPos)) {
            listCheckedItemsPos.add(itemPos);
        }
    }

    private void removeFromCheckedList(Integer itemPos) {
        if (listCheckedItemsPos.contains(itemPos)) {
            listCheckedItemsPos.remove(itemPos);
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
        setParam(checkBox);
        checkBox.setText(listItems.get(position));
        if(leftFlagSelectorId > -1){
            checkBox.setCompoundDrawablesWithIntrinsicBounds(
                    context.getResources().getDrawable(leftFlagSelectorId), null, null, null);
        } else if(rightFlagSelectorId > -1){
            checkBox.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, context.getResources().getDrawable(rightFlagSelectorId), null);
        }
        if(bgSelector > -1) checkBox.setBackgroundResource(bgSelector);
        checkBox.setChecked(listCheckedItemsPos.contains(position));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Statical.TAG, "" + position);
                if (checkBox.isChecked()) {
                    addToCheckedList(position);
                } else {
                    removeFromCheckedList(position);
                }

            }
        });
        return view;
    }
}
