package com.example.nol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends BaseAdapter {
    Context context;
    int layout;
    LayoutInflater inflater;
    List<String> gameList = new ArrayList<String>();

    public GameListAdapter(Context context, int layout){
        this.context = context;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int i) {
        return gameList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(layout, null);

        TextView listStep = (TextView)view.findViewById(R.id.gameListStep);
        listStep.setText(gameList.get(i));
        return view;
    }
}
