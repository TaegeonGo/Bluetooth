package com.example.kimhyeongmin.bluetooth;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ExpenditureActivity extends Fragment {


    private ListView mListview = null;

    private SharedPreferences preflog;
    private SharedPreferences.Editor logedit;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        final View convertView = inflater.inflate(R.layout.activity_expenditure, container, false);

        init();

        ArrayList<ListViewItem> arrayList = new ArrayList<>();

        int j = preflog.getAll().size() / 3;

        preflog.getString("status","status");
        preflog.getString("time", "time");
        preflog.getInt("money", 0);

        for (int i = j-1; i >= 0 ; i--){
            ListViewItem listViewItem = new ListViewItem();

            listViewItem.date = preflog.getString("time" + String.valueOf(i), "time");
            listViewItem.status = preflog.getString("status" + String.valueOf(i),"status");
            listViewItem.money = preflog.getInt("money" + String.valueOf(i), 10);

            arrayList.add(listViewItem);
        }

        mListview = (ListView) convertView.findViewById(R.id.expendListView);
        ListAdapter listAdapter = new ListViewAdapter(arrayList);
        mListview.setAdapter(listAdapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                refresh();
            }
        });


        return convertView;
    }

    private void init() {
        preflog = getActivity().getSharedPreferences("spendlog", Context.MODE_PRIVATE);
        logedit = preflog.edit();
    }

    public void refresh(){
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }
}
