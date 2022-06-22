package com.tech.ble_v1;
/*
* @Autores: Caio Augusto Moreira Marques
*           Luis Gustavo Oliveira
* @Versões: 1.0 Caio - MainActivity 21/07/22
*           1.1 Luis Gustavo - byte_ManufacturerData 18/08/22
*
* */
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mText;
    private Button mAdvertiseButton;
    private Button mDiscoverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.text);
        mDiscoverButton = (Button) findViewById(R.id.discover_btn);
        mAdvertiseButton = (Button) findViewById(R.id.advertise_btn);

        mDiscoverButton.setOnClickListener(this);
        mAdvertiseButton.setOnClickListener(this);


        if (!BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported()) {
            Toast.makeText(this, "Multiple advertisement not supported", Toast.LENGTH_SHORT).show();
            mAdvertiseButton.setEnabled(false);
            mDiscoverButton.setEnabled(false);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingPermission")
    private void advertise() {
        BluetoothLeAdvertiser advertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(false)
                .build();

        ParcelUuid pUuid = new ParcelUuid(UUID.fromString(getString(R.string.ble_uuid)));

        AdvertiseData data = new AdvertiseData.Builder()
                .setIncludeDeviceName(false)
                .addServiceUuid(pUuid)
                .addServiceData(pUuid, "Data".getBytes(Charset.forName("UTF-8")))
                .addManufacturerData ( 1, byte_ManufacturerData ( "0e6b14" ) )
                .build();

        AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
            }

            @Override
            public void onStartFailure(int errorCode) {
                Log.e("BLE", "Advertising onStartFailure: " + errorCode);
                super.onStartFailure(errorCode);
            }
        };

        advertiser.startAdvertising ( settings, data, advertisingCallback );
    }

    public byte[] byte_ManufacturerData(String rfid){

        int len = rfid.length();
        if (len<6){
            rfid = "0"+rfid;
            len = rfid.length ();
        }
        byte[] data = new byte[len/2];

        for (int i = 0; i < len; i+=2) {
            data[i / 2] = (byte) ((Character.digit(rfid.charAt(i), 16) << 4) + Character.digit(rfid.charAt(i+1), 16));
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.advertise_btn) {
            advertise();
        }
    }
}