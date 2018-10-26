package fr.maximelucquin.falconexperience.views.ControlDevice;

import android.app.DialogFragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;

import java.util.ArrayList;
import java.util.List;

import fr.maximelucquin.falconexperience.R;

public class BluetoothFragment extends DialogFragment implements BluetoothService.OnBluetoothScanCallback {

    List<BluetoothDevice> devices;

    RecyclerView rv;
    BluetoothDeviceAdapter adapter;

    private BluetoothService service;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.bluetooth_device_frag_layout,container);

        devices = new ArrayList<>();

        //RECYCER
        rv= (RecyclerView) rootView.findViewById(R.id.bluetooth_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //ADAPTER
        adapter=new BluetoothDeviceAdapter(this.getActivity(),devices);
        rv.setAdapter(adapter);

        this.getDialog().setTitle("Appareils Bluetooth");

        //service = BluetoothService.getDefaultInstance();
        //service.setOnScanCallback(this);
        //service.startScan();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (service != null) {
            service.stopScan();
        }
    }

    @Override
    public void onDeviceDiscovered(BluetoothDevice device, int rssi) {
        devices.add(device);
        int index = devices.indexOf(device);
        if (index < 0) {
            devices.add(device);
            adapter.notifyItemInserted(devices.size() - 1);
        } else {
            devices.set(index, device);
            adapter.notifyItemChanged(index);
        }
    }

    @Override
    public void onStartScan() {
        this.getDialog().setTitle("Recherche...");
    }

    @Override
    public void onStopScan() {
        if (this.getDialog() != null) {
            this.getDialog().setTitle("Appareils Bluetooth");
        }

    }
}
