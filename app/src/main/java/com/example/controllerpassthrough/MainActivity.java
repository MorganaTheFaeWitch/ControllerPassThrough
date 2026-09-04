

package com.example.controllerpassthrough;

import static android.view.InputDevice.SOURCE_GAMEPAD;
import static android.view.InputDevice.SOURCE_JOYSTICK;
import static android.view.KeyEvent.KEYCODE_BUTTON_A;
import static android.view.KeyEvent.KEYCODE_BUTTON_L1;
import static android.view.KeyEvent.KEYCODE_BUTTON_R1;
import static android.view.MotionEvent.AXIS_X;
import static android.view.MotionEvent.AXIS_Z;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    //default setup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.isFromSource(SOURCE_GAMEPAD) || event.getRepeatCount() == 0){
            // proving the event happend
                switch (keyCode){
                    case KEYCODE_BUTTON_A:
                        Log.d("controllerInput",  "A btn");
                        break;
                    case KEYCODE_BUTTON_R1:
                        Log.d("controllerInput",  "RB btn");
                        break;
                    case KEYCODE_BUTTON_L1:
                        Log.d("controllerInput",  "LB btn");
                        break;
                    default:
                        break;
                }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (event.isFromSource(SOURCE_JOYSTICK)) {
            //left stick
            if ((event.getAxisValue(MotionEvent.AXIS_Y) > 0.1 || event.getAxisValue(MotionEvent.AXIS_Y) < -0.1)){
                Log.d("GameView", "Gamepad event Lstick: " + event);
                return true;
            }
            //right stick
            if ((event.getAxisValue(MotionEvent.AXIS_Z) > 0.1 || event.getAxisValue(AXIS_Z) < -0.1)){
                Log.d("GameView", "Gamepad event Rstick: " + event);
                return true;
            }
        }
        return super.onGenericMotionEvent(event);
    }

}