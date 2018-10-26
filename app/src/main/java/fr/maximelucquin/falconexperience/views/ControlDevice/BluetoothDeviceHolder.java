package fr.maximelucquin.falconexperience.views.ControlDevice;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;

public class BluetoothDeviceHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;
    TextView rssiTxt;

    public BluetoothDeviceHolder(View itemView) {
        super(itemView);
        nameTxt= (TextView) itemView.findViewById(R.id.bluetooth_name);
        rssiTxt= (TextView) itemView.findViewById(R.id.bluetooth_rssi);
    }
}