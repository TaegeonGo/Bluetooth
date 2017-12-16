package com.example.kimhyeongmin.bluetooth;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends Fragment {


    private static final String TAG = "bluetooth2";

    TextView obakCnt, bakCnt, oshipCnt, shipCnt, totalMoneyCnt;

    Handler h;


    final int RECIEVE_MESSAGE = 1;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private static boolean flag = false;

    private long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
    String getTime = sdf.format(date);

    private SharedPreferences hispref,preflog,count;
    private SharedPreferences.Editor hisedit, logedit,countedit;


    private BluetoothAdapter mBluetoothAdapter;
    public ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)

    private static String address = "20:16:07:14:38:28";

    int obak = 1, bak =1 , oship = 1, ship = 1;


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View convertView = inflater.inflate(R.layout.activity_main, container, false);

        init(convertView);

        Log.d("now Time" , getTime);

        Log.d("hisprefCount",String.valueOf(hispref.getAll().size()));

        if (hispref.getAll().size() == 0){

            hisedit.putInt("500cnt", 0);
            hisedit.putInt("100cnt", 0);
            hisedit.putInt("50cnt", 0);
            hisedit.putInt("10cnt", 0);
            hisedit.putInt("TotalMoney",0);
            hisedit.commit();

            countedit.putString("count","0");
            countedit.commit();

            logedit.putString("time"+count.getString("count","count"), getTime);
            logedit.putString("status"+count.getString("count","count"), "돈들어왔다");
            logedit.putInt("money"+count.getString("count","count"), (obak * 500) + (bak * 100) + (oship * 50) + (ship * 10));
            logedit.commit();


        }


        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);

                        Log.d("strIncom",strIncom);

                        if (strIncom.equals("a")){
                            Log.d("strInCom a" , strIncom);

                            hisedit.putInt("500cnt", hispref.getInt("500cnt", 10) +  obak);
                            hisedit.commit();
                            logedit.putInt("money"+count.getString("count","count"), hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10));
                            logedit.putString("time"+count.getString("count","count"), getTime);
                            logedit.putString("status"+count.getString("count","count"), "돈들어왔다");
                            logedit.commit();
                            int k = Integer.valueOf(count.getString("count","count")) + 1;
                            countedit.putString("count", String.valueOf(k));
                            countedit.commit();
                            obakCnt.setText("500원\n" +hispref.getInt("500cnt",0) + "개");

                        }else if (strIncom.equals("b")){
                            Log.d("strInCom b" , strIncom);
                            hisedit.putInt("100cnt", hispref.getInt("100cnt", 10) +  bak);
                            hisedit.commit();
                            logedit.putInt("money"+count.getString("count","count"), hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10));
                            logedit.putString("time"+count.getString("count","count"), getTime);
                            logedit.putString("status"+count.getString("count","count"), "돈들어왔다");
                            logedit.commit();
                            int k = Integer.valueOf(count.getString("count","count")) + 1;
                            countedit.putString("count", String.valueOf(k));
                            countedit.commit();
                            bakCnt.setText("100원\n" +hispref.getInt("100cnt",0) + "개");

                        }else if (strIncom.equals("c")){
                            Log.d("strInCom c" , strIncom);
                            hisedit.putInt("50cnt", hispref.getInt("50cnt", 10) +  oship);
                            hisedit.commit();
                            logedit.putInt("money"+count.getString("count","count"), hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10));
                            logedit.putString("time"+count.getString("count","count"), getTime);
                            logedit.putString("status"+count.getString("count","count"), "돈들어왔다");
                            logedit.commit();
                            int k = Integer.valueOf(count.getString("count","count")) + 1;
                            countedit.putString("count", String.valueOf(k));
                            countedit.commit();
                            oshipCnt.setText("50원\n" +hispref.getInt("50cnt",0) + "개");

                        }else if (strIncom.equals("d")){
                            Log.d("strInCom d" , strIncom);
                            hisedit.putInt("10cnt", hispref.getInt("10cnt", 10) +  ship);
                            hisedit.commit();
                            logedit.putInt("money"+count.getString("count","count"), hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10));
                            logedit.putString("time"+count.getString("count","count"), getTime);
                            logedit.putString("status"+count.getString("count","count"), "돈들어왔다");
                            logedit.commit();
                            int k = Integer.valueOf(count.getString("count","count")) + 1;
                            countedit.putString("count", String.valueOf(k));
                            countedit.commit();
                            shipCnt.setText("10원\n" +hispref.getInt("10cnt",0) + "개");

                        }

                        totalMoneyCnt.setText(String.valueOf(hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10)) + " 원");

                        sb.append(strIncom);
                        break;
                }
            }
        };

        obakCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("a");
            }
        });
        bakCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("b");
            }
        });
        oshipCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("c");
            }
        });
        shipCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("d");
            }
        });

        obakCnt.setText("500원\n" + hispref.getInt("500cnt", 10) + "개");
        bakCnt.setText("100원\n" + hispref.getInt("100cnt", 20) + "개");
        oshipCnt.setText("50원\n" + hispref.getInt("50cnt", 30) + "개");
        shipCnt.setText("10원\n" + hispref.getInt("10cnt", 40) + "개");

        totalMoneyCnt.setText(String.valueOf(hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10)) + " 원");


        totalMoneyCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                        alertDialog.setMessage("저금통을 열으시겠습니까?");
                        alertDialog.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setNegativeButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                                mConnectedThread.write("A");

                                logedit.putString("time"+count.getString("count","count"), getTime);
                                logedit.putString("status"+count.getString("count","count"), "돈나갔다");
                                logedit.putInt("money"+count.getString("count","count"), hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10));
                                logedit.commit();

                                hisedit.putInt("500cnt", 0);
                                hisedit.putInt("100cnt", 0);
                                hisedit.putInt("50cnt", 0);
                                hisedit.putInt("10cnt", 0);
                                hisedit.putInt("TotalMoney",0);
                                hisedit.commit();

                                int k = Integer.valueOf(count.getString("count","count")) + 1;
                                countedit.putString("count", String.valueOf(k));
                                countedit.commit();



                                obakCnt.setText("500원\n" +hispref.getInt("500cnt",0) + "개");
                                bakCnt.setText("100원\n" +hispref.getInt("100cnt",0) + "개");
                                oshipCnt.setText("50원\n" +hispref.getInt("50cnt",0) + "개");
                                shipCnt.setText("10원\n" +hispref.getInt("10cnt",0) + "개");
                                totalMoneyCnt.setText(String.valueOf(hispref.getInt("TotalMoney",0) + (hispref.getInt("500cnt", 10) * 500) + (hispref.getInt("100cnt", 10) * 100) + (hispref.getInt("50cnt", 10) * 50) + (hispref.getInt("10cnt", 10) * 10)) + " 원");

                            }
                        }).show();

            }
        });
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();



        return convertView;

    }

    Handler timer = new Handler(){
        @Override
        public void handleMessage(Message msg) {


            flag=false;
        }
    };


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {


        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {

        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }


        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getActivity().getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    public void init(View view){

        hispref = getActivity().getSharedPreferences("moneyhistory", Context.MODE_PRIVATE);
        hisedit = hispref.edit();

        preflog = getActivity().getSharedPreferences("spendlog", Context.MODE_PRIVATE);
        logedit = preflog.edit();

        count = getActivity().getSharedPreferences("count",Context.MODE_PRIVATE);
        countedit = count.edit();

        obakCnt = view.findViewById(R.id.obak);
        bakCnt = view.findViewById(R.id.bak);
        oshipCnt = view.findViewById(R.id.oship);
        shipCnt = view.findViewById(R.id.ship);
        totalMoneyCnt = view.findViewById(R.id.totalMoney);

    }


    public class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler

                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }

}