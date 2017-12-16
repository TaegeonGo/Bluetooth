package com.example.kimhyeongmin.bluetooth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SettingActivity extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        final View convertView = inflater.inflate(R.layout.activity_setting, container, false);

        final SharedPreferences pref = getActivity().getSharedPreferences("private", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        String[] strArr = { "소리" , "패스워드 설정" };

        ArrayAdapter adapter = new ArrayAdapter(getActivity() , android.R.layout.simple_list_item_1, strArr) ;

        final ListView listview = (ListView) convertView.findViewById(R.id.settingList) ;
        listview.setAdapter(adapter);

        final EditText editText = new EditText(getActivity());

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (i == 1){
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Title")
                            .setView(editText)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editor.putString("code", editText.getText().toString());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }

            }
        });

        return convertView;
    }
}
