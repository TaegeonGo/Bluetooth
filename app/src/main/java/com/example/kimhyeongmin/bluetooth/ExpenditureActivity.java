package com.example.kimhyeongmin.bluetooth;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ExpenditureActivity extends Fragment {


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        final View convertView = inflater.inflate(R.layout.activity_expenditure, container, false);

        String[] strStatus = { "입금" , "입금" , "지출" , "입금" };
        String[] strDate = { "2017.12.17" , "2017.12.17" , "2017.12.17" , "2017.12.17" };
        String[] strMoney = { "1,000" , "500" , "2,000" , "500" };

        ArrayList<ListViewItem> mData = new ArrayList<>();

        for (int i = 0; i < strStatus.length ; ++i ){

            ListViewItem listViewItem = new ListViewItem();

            if (strStatus.equals("입금")){
                listViewItem.status = strStatus[i];

            }
        }

        return convertView;
    }
}
