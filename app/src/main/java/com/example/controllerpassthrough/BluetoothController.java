package com.example.controllerpassthrough;


import static android.content.Context.BLUETOOTH_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class BluetoothController {
	public Context context;
	private BluetoothAdapter bluetoothAdapter;
	private static final long SCAN_PERIOD = 10000;
	private BluetoothLeScanner bluetoothLeScanner;
	private boolean scanning = false;
	private ScanFilter filter;
	private Handler handler;
	public BluetoothController(Context ctxt){
		this.handler = new Handler();
		this.filter = new ScanFilter.Builder().setDeviceAddress("6feb780f-bf34-44d0-82ab-35166f1d26ae").build();
		this.context = ctxt;
	}
	public void init(){
		BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
		if(bluetoothManager != null){
			bluetoothAdapter = bluetoothManager.getAdapter();
			if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()){
				bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
				//finding a baneblade
				ScanSettings settings = new ScanSettings.Builder()
						.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
						.build();
				ScanCallback scancallback = new ScanCallback() {
					@Override
					public void onScanResult(int callBackType, ScanResult result){
						super.onScanResult(callBackType, result);
						// when we find device
					}
					@Override
					public void onScanFailed(int errorCode){
						super.onScanFailed(errorCode);
					}
				};
			}
			else {
				Log.d("bluetoothController", "Bluetooth LE not supported on this device");
			}
		}
		else {
			Log.d("bluetoothController", "Bluetooth manager failed to initalize");
		}
	}
	public void UpdateTankInstruction(){

	}
}
