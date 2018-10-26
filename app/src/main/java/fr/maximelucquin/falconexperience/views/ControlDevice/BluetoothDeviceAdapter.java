package fr.maximelucquin.falconexperience.views.ControlDevice;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.ParcelUuid;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.maximelucquin.falconexperience.R;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceHolder> {

    Context c;
    List<BluetoothDevice> devices;

    public BluetoothDeviceAdapter(Context c, List<BluetoothDevice> devices) {
        this.c = c;
        this.devices = devices;
    }

    @Override
    public BluetoothDeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_device_card,parent,false);
        BluetoothDeviceHolder holder=new BluetoothDeviceHolder(v);
        return holder;
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(BluetoothDeviceHolder holder, int position) {
        holder.nameTxt.setText(devices.get(position).getName());
        ParcelUuid[] uuid = devices.get(position).getUuids();
        if (uuid != null) {
            holder.rssiTxt.setText(devices.get(position).getUuids().toString());
        } else {
            holder.rssiTxt.setText(devices.get(position).getAddress());
        }

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}

