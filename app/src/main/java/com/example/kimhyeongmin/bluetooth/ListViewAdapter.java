package com.example.kimhyeongmin.bluetooth;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimhyeongmin on 2017. 12. 17..
 */

public class ListViewAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<ListViewItem> mListData = null;
    private int nListCnt = 0;

    public ListViewAdapter(ArrayList<ListViewItem> _ListData){

        mListData = _ListData;
        nListCnt = mListData.size();

    }

    @Override
    public int getCount() {

        return nListCnt;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            final Context context = parent.getContext();
            if (inflater == null){
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.expend_lisitem, parent, false);
        }

        TextView statusText = (TextView) convertView.findViewById(R.id.statusText);
        TextView dateText = (TextView) convertView.findViewById(R.id.dateText);
        TextView moneyText = (TextView) convertView.findViewById(R.id.moneyText);

        statusText.setText(mListData.get(position).status);
        dateText.setText(mListData.get(position).date);
        moneyText.setText(mListData.get(position).money);

        return convertView;
    }
}
