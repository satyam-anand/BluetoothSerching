package com.hanker.bluetoothtest;



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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BtDevices extends Activity {

	private ListView listview;
	private BluetoothAdapter mAdapter;
	ArrayAdapter<String> mArrayDevice;
	String BT_ADDRESS="B0:C4:E7:1E:01:63";
	//String BT_ADDRESS="30:C3:65:18:66:12";
	
	// 
	
	//sa
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bt_device_list);
		
		mArrayDevice = new ArrayAdapter<String>(this, R.layout.device_name);
		listview=(ListView) findViewById(R.id.listView1);
		listview.setAdapter(mArrayDevice);
		listview.setOnItemClickListener(mDeviceLickListner);
		
		IntentFilter filter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReciever, filter);
		
		filter=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReciever, filter);
		
		
		mAdapter=BluetoothAdapter.getDefaultAdapter();
		
		BluetoothDevice conn=mAdapter.getRemoteDevice(BT_ADDRESS);
	}
	
	private BroadcastReceiver mReciever=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				try{
					mArrayDevice.add(device.getName()+"\n"+device.getAddress());
					Log.i("bt add", device.getAddress()+device.hashCode());
					if(BT_ADDRESS.contains(device.getAddress()))
					{
						Toast.makeText(BtDevices.this, "IS CONNECTED", Toast.LENGTH_LONG).show();
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            
                if (mArrayDevice.getCount() == 0) {
                   Toast.makeText(getApplicationContext(), "NO DEVICE", Toast.LENGTH_SHORT).show();
                }
            }
	
			
		}
	};
	
	private OnItemClickListener mDeviceLickListner=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			 mAdapter.cancelDiscovery();

	            // Get the device MAC address, which is the last 17 chars in the View
			 Toast.makeText(getApplicationContext(), 
				      arg0.getItemAtPosition(arg2).toString(), 
				      Toast.LENGTH_LONG).show();
		}
	};
	

	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mAdapter.startDiscovery();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mAdapter != null)
		{
			mAdapter.cancelDiscovery();
		}
		
	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mAdapter != null)
		{
			mAdapter.cancelDiscovery();
		}
		this.unregisterReceiver(mReciever);
	}
	
}
