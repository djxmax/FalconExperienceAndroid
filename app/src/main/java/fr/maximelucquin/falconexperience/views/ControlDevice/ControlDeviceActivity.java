package fr.maximelucquin.falconexperience.views.ControlDevice;

import fr.maximelucquin.falconexperience.R;
import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

import android.hardware.usb.UsbDevice;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ControlDeviceActivity extends AppCompatActivity implements ArduinoListener {

    Arduino arduino;

    private TextView arInfo;
    private EditText arTextSend;
    private ImageButton arButtonSend;
    private EditText arTextRecive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_device);

        arInfo = (TextView) findViewById(R.id.controlArInfo);
        arTextSend = (EditText) findViewById(R.id.controlArSendText);
        arButtonSend = (ImageButton) findViewById(R.id.controlArSendButton);
        arTextRecive = (EditText) findViewById(R.id.controlArReceiveText);

        arduino = new Arduino(this);
        arduino.addVendorId(6790);
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
        arInfo.setText("Arduino connected");
    }

    @Override
    public void onArduinoDetached() {
        // arduino detached from phone
        arInfo.setText("Arduino deconnected");
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
}
