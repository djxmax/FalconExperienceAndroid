package fr.maximelucquin.falconexperience.views.ControlDevice;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;

import java.util.ArrayList;
import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.views.ItemDetails.ItemDetailsActivity;
import fr.maximelucquin.falconexperience.views.ItemList.ItemsActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;
import fr.maximelucquin.falconexperience.views.TriggerDetails.ItemAdapter;

public class BluetoothDeviceActivity extends AppCompatActivity implements BluetoothService.OnBluetoothScanCallback, BluetoothService.OnBluetoothEventCallback {

    List<BluetoothDevice> devices;

    RecyclerView rv;
    Button searchButton;
    BluetoothDeviceAdapter adapter;

    private BluetoothService service;
    private Boolean isSearching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device);

        isSearching = false;
        devices = new ArrayList<>();

        searchButton = findViewById(R.id.bluetoothSearchButton);
        //RECYCER
        rv= (RecyclerView) findViewById(R.id.bluetoothDeviceRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //ADAPTER
        adapter=new BluetoothDeviceAdapter(this,devices);
        rv.setAdapter(adapter);



        service = BluetoothService.getDefaultInstance();
        service.setOnScanCallback(this);
        service.setOnEventCallback(this);
        //service.startScan();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        service.stopScan();
                        service.connect(devices.get(position));
                        searchButton.setText("Connexion...");
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public void clickSearch(View view) {
        if (isSearching) {
            service.stopScan();
            isSearching = false;
        } else {
            service.startScan();
            isSearching = true;
        }
    }

    @Override
    public void onDeviceDiscovered(BluetoothDevice device, int i) {
        System.out.println("bla");
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
        devices.clear();
        adapter.notifyDataSetChanged();
        isSearching = true;
        if (searchButton != null) {
            searchButton.setText("Stop");
        }
    }

    @Override
    public void onStopScan() {
        isSearching = false;
        if (searchButton != null) {
            searchButton.setText("Chercher");
        }
    }

    @Override
    public void onDataRead(byte[] bytes, int i) {

    }

    @Override
    public void onStatusChange(BluetoothStatus bluetoothStatus) {
        System.out.println(bluetoothStatus);
    }

    @Override
    public void onDeviceName(String s) {

    }

    @Override
    public void onToast(String s) {

    }

    @Override
    public void onDataWrite(byte[] bytes) {

    }
}
