package com.tech.ble_v1;
/*
* @Autores: Caio Augusto Moreira Marques
*           Luis Gustavo Oliveira
* @Versões: 1.0 Caio - MainActivity 21/07/22
*           1.1 Luis Gustavo - byte_ManufacturerData 18/08/22
*
* */
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.ble_v1.utility.Advertise;
import com.tech.ble_v1.utility.ComandID;

import java.nio.charset.Charset;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mText;
    private Button mAdvertiseButton;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestart ();
        setContentView(R.layout.activity_main);


        mText = (TextView) findViewById(R.id.text);
        mAdvertiseButton = (Button) findViewById(R.id.btn_pump);
        mAdvertiseButton.setOnClickListener(this);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();

        if (!bluetoothAdapter.isEnabled ()) {
            bluetoothAdapter.enable ();
        }
        else if (!BluetoothAdapter.getDefaultAdapter ().isMultipleAdvertisementSupported ()) {
            Toast.makeText ( this, "Bluetooth desativado, ative por favor!", Toast.LENGTH_LONG ).show ();
            mAdvertiseButton.setEnabled ( false );
        } else {
            Toast.makeText ( this, "Bluetooth ativado!", Toast.LENGTH_LONG ).show ();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onStop() {
        super.onStop ();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();

        if (bluetoothAdapter.isEnabled ()) {
            bluetoothAdapter.disable ();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.btn_pump:

            case R.id.btn_refeicao:
                Advertise.advertise();
                break;
        }
    }
}