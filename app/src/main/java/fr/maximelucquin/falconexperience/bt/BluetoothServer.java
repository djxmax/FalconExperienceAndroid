package fr.maximelucquin.falconexperience.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import fr.maximelucquin.falconexperience.views.MainActivity;

public class BluetoothServer extends Thread {


    private BluetoothAdapter btAdapter;
    private String socketString = "TFE";
    private BluetoothServerSocket btServerSocket;
    private BluetoothSocket btConnectedSocket;
    private final String TAG = "Server";
    private MainActivity parent;
    /*package-protected*/ static final String ACTION = "Bluetooth socket is connected";
    private boolean connected;

    public BluetoothServer(MainActivity parent) {
        this.parent = parent;
        connected= false;
    }

    @Override
    public void run() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            Log.i(TAG, "getting socket from adapter");
            btServerSocket = btAdapter.listenUsingRfcommWithServiceRecord(socketString, MainActivity.BT_UUID);
            listen();

        }
        catch (IOException ex) {
            Log.e(TAG, "error while initializing");
        }
    }

    private void listen() {
        Log.i(TAG, "listening");
        btConnectedSocket = null;
        while (!connected) {
            try {
                btConnectedSocket = btServerSocket.accept();
            }
            catch (IOException ex) {
                Log.e(TAG,"connection failed");
                connectionFailed();
            }

            if (btConnectedSocket != null) {
                broadcast();
                closeServerSocket();
            }
            else {
                Log.i(TAG,  "socket is null");
                connectionFailed();
            }
        }

    }

    private void broadcast() {
        try {
            Log.i(TAG, "connected? "+btConnectedSocket.isConnected());
            Intent intent = new Intent();
            intent.setAction(ACTION);
            intent.putExtra("state", btConnectedSocket.isConnected());
            parent.sendBroadcast(intent);
            connected = true;
        }
        catch (RuntimeException runTimeEx) {

        }

        closeServerSocket();
    }


    private void connectionFailed () {

    }

    public void closeServerSocket() {
        try {
            btServerSocket.close();
        }
        catch (IOException ex) {
            Log.e(TAG+":cancel", "error while closing server socket");
        }
    }
}
