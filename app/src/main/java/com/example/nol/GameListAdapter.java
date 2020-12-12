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
    int flag; // 몇 단계까지만 누를 수 있는가

    public GameListAdapter(Context context, int layout, int flag){
        this.context = context;
        this.layout = layout;
        this.flag = flag;
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

        // 깬 단계는 갈색
        view.setBackground(context.getResources().getDrawable(R.drawable.round_corner_gamelist));
        // 안 깬 단계는 회색
        if (i >= flag)
            view.setBackground(context.getResources().getDrawable(R.drawable.round_corner_gamelist_gray));

        TextView listStep = (TextView)view.findViewById(R.id.gameListStep);
        listStep.setText(gameList.get(i));

        return view;
    }
}
