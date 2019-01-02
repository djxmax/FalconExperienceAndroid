package fr.maximelucquin.falconexperience.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.UUID;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.views.ControlDevice.ControlDeviceActivity;
import fr.maximelucquin.falconexperience.views.DBManage.DBManager;
import fr.maximelucquin.falconexperience.views.MediaLoader.MediaListActivity;
import fr.maximelucquin.falconexperience.views.Player.PlayerActivity;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceListActivity;

public class MainActivity extends AppCompatActivity {

    public static UUID BT_UUID = UUID.fromString("ed8fa707-add4-4215-a387-cb5749da3046");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openSequenceListActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SequenceListActivity.class);
        startActivity(intent);
    }

    public void openControlDeviceActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ControlDeviceActivity.class);
        startActivity(intent);
    }

    public void openMediaListActivity(View view) {
        Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
        startActivity(intent);
    }

    public void openPlayerActivity(View view) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        startActivity(intent);
    }

    public void openDBManagerActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DBManager.class);
        startActivity(intent);
    }
}
