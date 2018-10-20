package fr.maximelucquin.falconexperience.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.views.ControlDevice.ControlDeviceActivity;
import fr.maximelucquin.falconexperience.views.MediaLoader.MediaListActivity;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceListActivity;

public class MainActivity extends AppCompatActivity {

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
}
