package com.example.bluetoothandwifi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class wifi_app extends AppCompatActivity {

    Switch w_switch;
    TextView tvWOnOff;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_wifi_app );

        w_switch = findViewById ( R.id.w_switch );
        tvWOnOff = findViewById ( R.id.tvWOnOff );
        wifiManager = (WifiManager) getApplicationContext ().getSystemService ( Context.WIFI_SERVICE );

        w_switch.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton , boolean isChecked) {
                Log.d ( "Checking" , String.valueOf ( isChecked ) );
                if (isChecked) {
                    wifiManager.setWifiEnabled ( true );
                    Log.d ( "Checking" , "IF condition for state check.." );
                    tvWOnOff.setText ( "On" );

                } else {

                    wifiManager.setWifiEnabled ( false );
                    Log.d ( "Checking" , "else condition for state check.." );
                    tvWOnOff.setText ( "Off" );
                }
            }
        } );

//        if (wifiManager.isWifiEnabled ()){
//            Toast.makeText ( this , "Scanning..." , Toast.LENGTH_SHORT ).show ();
//            w_switch.setChecked ( true );
//            tvWOnOff.setText ( "On" );
//        }else{
//            Toast.makeText ( this , "Wifi is disable.. plz enable it " , Toast.LENGTH_SHORT ).show ();
//            w_switch.setChecked ( false );
//            tvWOnOff.setText ( "Off" );
//        }
    }

    @Override
    protected void onStart() {
        super.onStart ();

        IntentFilter filter = new IntentFilter ( WifiManager.WIFI_STATE_CHANGED_ACTION );
        registerReceiver ( wifiState , filter );

    }

    @Override
    protected void onStop() {
        super.onStop ();
        unregisterReceiver ( wifiState );
    }


    private BroadcastReceiver wifiState = new BroadcastReceiver () {
        @Override
        public void onReceive(Context context , Intent intent) {
            //intent contain the wifi state in the form of int
            int wifiS = intent.getIntExtra ( WifiManager.EXTRA_WIFI_STATE , WifiManager.WIFI_STATE_UNKNOWN );

            switch (wifiS) {
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.d ( "Checking" , "Broadcast case1..." );
                    w_switch.setChecked ( true );
                    tvWOnOff.setText ( "On" );
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    Log.d ( "Checking" , "Broadcast case2..." );
                    w_switch.setChecked ( false );
                    tvWOnOff.setText ( "Off" );
                    break;
            }
        }
    };
}