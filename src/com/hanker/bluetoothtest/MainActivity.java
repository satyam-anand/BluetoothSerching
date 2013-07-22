package com.hanker.bluetoothtest;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static int BLUETOOTT_STAUTS;
	private static int BLUETOOTH_DISCOVERABLE;
	private ArrayList<String> btdevices = new ArrayList<String>();
	Button buttonON, buttonOFF, buttonDISCOVERABLE, buttonHIDE, bt_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonDISCOVERABLE = (Button) findViewById(R.id.bt_discoverable);
		buttonHIDE = (Button) findViewById(R.id.bt_hide);
		buttonOFF = (Button) findViewById(R.id.btn_off);
		buttonON = (Button) findViewById(R.id.thn_on);
		bt_search = (Button) findViewById(R.id.btn_btlist);
		
		final BluetoothAdapter bluetoothadapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothadapter == null) {
			System.out.append("device not supported");
		}
		buttonDISCOVERABLE.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!bluetoothadapter.isDiscovering()) {
					Intent intent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					startActivityForResult(intent, BLUETOOTH_DISCOVERABLE);

				}

			}
		});
		buttonHIDE.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bluetoothadapter.isDiscovering()) {
					bluetoothadapter.cancelDiscovery();
				}

			}
		});
		buttonOFF.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bluetoothadapter.isEnabled()) {
					bluetoothadapter.disable();
				}
			}
		});
		buttonON.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!bluetoothadapter.isEnabled()) {
					Intent intent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(intent, BLUETOOTT_STAUTS);

				}
			}
		});
		bt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("CALL", "BR");
				if(bluetoothadapter.isEnabled())
				{
					Intent intent=new Intent(getApplicationContext(),BtDevices.class);
					startActivity(intent);
					
				}else{
					Toast.makeText(getApplicationContext(), "Turn ON Bluetooth", Toast.LENGTH_LONG).show();
				}
				
			}

		});

	}

}
