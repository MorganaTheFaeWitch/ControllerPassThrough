package com.example.controllerpassthrough;


import static android.content.Context.BLUETOOTH_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.core.app.ActivityCompat;

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
		this.filter = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("6feb780f-bf34-44d0-82ab-35166f1d26ae")).build();
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
				//handeling results
				ScanCallback scancallback = new ScanCallback() {
					@Override
					public void onScanResult(int callBackType, ScanResult result){
						super.onScanResult(callBackType, result);
						// when we find device
						Log.d("BluetoothScanner", "found the tonk");
						connect(result.getDevice());
					}
					@Override
					public void onScanFailed(int errorCode){
						super.onScanFailed(errorCode);
					}
				};
				//scanning for the tonk
				scanForTonk(scancallback);
			}
			else {
				Log.d("bluetoothController", "Bluetooth LE not supported on this device");
			}
		}
		else {
			Log.d("bluetoothController", "Bluetooth manager failed to initalize");
		}
	}

	public void scanForTonk(ScanCallback LECB){
//		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//			return;
//		}
//		if (!scanning){
//			handler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//						return;
//					}
//					scanning = false;
//					bluetoothLeScanner.stopScan(LECB);
//				}
//			}, SCAN_PERIOD);
//			scanning = true;
//			bluetoothLeScanner.startScan(LECB);
//		}
//		else {
//			scanning = false;
//			bluetoothLeScanner.stopScan(LECB);
//		}
		// this shoud scan for a set perioud of time then stop the scan, unless i read it wrong then i need to ammend it
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) return;
		bluetoothLeScanner.startScan(LECB);
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				// checking permission, it will get mad without it even though its inaccessable without passing through the previous one???
				if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) return;
				bluetoothLeScanner.stopScan(LECB);
			}
		}, SCAN_PERIOD);
	}

	public boolean connect(){
		return false;
	}

	public void UpdateTankInstruction(){

	}
}
