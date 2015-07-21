package com.neirx.stopwatchtimer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LapAdapter extends BaseAdapter {
    private static final String CLASS_NAME = "<LapAdapter> ";
    Context context;
    List<Lap> objects;
    LayoutInflater lInflater;

    public LapAdapter(Context context, List<Lap> laps){
        this.context = context;
        objects = laps;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addLapToList(Lap lap){
        objects.add(lap);
        Log.d(MainActivity.TAG, CLASS_NAME + "objects.size() = " + objects.size());
        this.notifyDataSetChanged();
    }

    public  void clearLapsFromList(){
        Log.d(MainActivity.TAG, CLASS_NAME + Thread.currentThread().getName());
        objects.clear();
        Log.d(MainActivity.TAG, CLASS_NAME + "objects.size() = " + objects.size());
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.view_adapter_lap, parent, false);
        }
        TextView tvNum = (TextView) view.findViewById(R.id.tvLapAdapNum);
        TextView tvTimeDiffer = (TextView) view.findViewById(R.id.tvLapAdapTimeDiffer);
        TextView tvSecondsDiffer = (TextView) view.findViewById(R.id.tvLapAdapSecondsDiffer);
        TextView tvMillisDiffer = (TextView) view.findViewById(R.id.tvLapAdapMillisDiffer);
        TextView tvTimeTotal = (TextView) view.findViewById(R.id.tvLapAdapTimeTotal);
        TextView tvSecondsTotal = (TextView) view.findViewById(R.id.tvLapAdapSecondsTotal);
        TextView tvMillisTotal = (TextView) view.findViewById(R.id.tvLapAdapMillisTotal);

        Lap lap = (Lap) getItem(position);
        tvNum.setText(lap.getStopwatchNum()+"."+lap.getTimeNum()+")");
        tvTimeDiffer.setText(lap.getTimeDifference().substring(0, 6));
        tvSecondsDiffer.setText(lap.getTimeDifference().substring(6, 9));
        tvMillisDiffer.setText(lap.getTimeDifference().substring(9, 12));
        tvTimeTotal.setText(lap.getTimeTotal().substring(0, 6));
        tvSecondsTotal.setText(lap.getTimeTotal().substring(6, 9));
        tvMillisTotal.setText(lap.getTimeTotal().substring(9, 12));
        return view;
    }
}
