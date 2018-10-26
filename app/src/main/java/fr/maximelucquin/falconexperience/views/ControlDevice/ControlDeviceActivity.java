package fr.maximelucquin.falconexperience.views.ControlDevice;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.views.MainActivity;
import fr.maximelucquin.falconexperience.views.Player.PlayerActivity;
import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

import android.app.FragmentManager;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;

import java.util.UUID;

public class ControlDeviceActivity extends AppCompatActivity implements ArduinoListener {

    Arduino arduino;

    private TextView arInfo;
    private EditText arTextSend;
    private ImageButton arButtonSend;
    private EditText arTextRecive;
    private TextView blueInfo;

    private BluetoothConfiguration config;
    private BluetoothService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_device);

        arInfo = (TextView) findViewById(R.id.controlArInfo);
        blueInfo = (TextView) findViewById(R.id.controlBlueInfo);
        arTextSend = (EditText) findViewById(R.id.controlArSendText);
        arButtonSend = (ImageButton) findViewById(R.id.controlArSendButton);
        arTextRecive = (EditText) findViewById(R.id.controlArReceiveText);

        arduino = new Arduino(this);
        arduino.addVendorId(6790);

        config = new BluetoothConfiguration();
        config.context = getApplicationContext();
        config.bluetoothServiceClass = BluetoothClassicService.class; // BluetoothClassicService.class or BluetoothLeService.class
        config.bufferSize = 1024;
        config.characterDelimiter = '\n';
        config.deviceName = "TFE";
        config.callListenersInMainThread = true;
        config.uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        BluetoothService.init(config);
        service = BluetoothService.getDefaultInstance();

    }


    @Override
    protected void onStart() {
        super.onStart();
        arduino.setArduinoListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arduino.unsetArduinoListener();
        arduino.close();
    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        arduino.open(device);
        arInfo.setTextColor(Color.GREEN);
    }

    @Override
    public void onArduinoDetached() {
        // arduino detached from phone
        arInfo.setTextColor(Color.RED);
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {
        final String message = new String(bytes);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arTextRecive.setText(arTextRecive.getText() + "\n" + message);
            }});
        // new message received from arduino
    }

    @Override
    public void onArduinoOpened() {
        // you can start the communication
        String str = "Hello Arduino !";
        arduino.send(str.getBytes());
    }

    @Override
    public void onUsbPermissionDenied() {
        // Permission denied, display popup then
        arInfo.setText("USB permission denied, retrying in 3 sec");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arduino.reopen();
            }
        }, 3000);
    }

    public void sendTextToArduino(View view) {
        arduino.send(arTextSend.getText().toString().getBytes());
        arTextSend.setText("");
    }

    public void showScanDeviceDialog(View view) {
        Intent intent = new Intent(ControlDeviceActivity.this, BluetoothDeviceActivity.class);
        startActivity(intent);
    }
}
