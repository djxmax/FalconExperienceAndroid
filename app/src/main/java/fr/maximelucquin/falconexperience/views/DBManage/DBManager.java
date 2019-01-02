package fr.maximelucquin.falconexperience.views.DBManage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.views.MainActivity;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceListActivity;

public class DBManager extends AppCompatActivity {

    private String dbName = "falcon_experience_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbmanager);
    }

    public void openExportDB(View view) {
        if (isStoragePermissionGranted()) {
            createBackup();
        }

    }

    public void openImportDB(View view) {
        findDBFile();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted");
                return true;
            } else {

                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("", "Permission: " + permissions[0] + "was " + grantResults[0]);
            createBackup();
        }
    }

    public void createBackup() {

        SharedPreferences sharedPref = getSharedPreferences("dbBackUp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String dt = sharedPref.getString("dt", new SimpleDateFormat("dd-MM-yy_HH:mm:ss").format(new Date()));

        if (dt != new SimpleDateFormat("dd-MM-yy_HH:mm:ss").format(new Date())) {
            editor.putString("dt", new SimpleDateFormat("dd-MM-yy_HH:mm:ss").format(new Date()));

            editor.commit();
        }

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "FalconExperience");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {

            String directoryName = folder.getPath() + File.separator + sharedPref.getString("dt", "");
            folder = new File(directoryName);
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                exportDB(directoryName);
                Toast.makeText(this, "Export réussi", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Echec", Toast.LENGTH_SHORT).show();
        }

    }

    private void exportDB(String directoryName) {
        try {
            File dbFile = new File(this.getDatabasePath(dbName).getAbsolutePath());
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = directoryName + File.separator +
                    dbName + ".db";
            System.out.println(outFileName);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();


        } catch (IOException e) {
            Log.e("dbBackup:", e.getMessage());
        }
    }

    public void findDBFile() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                System.out.println(uri.toString());
                //showImage(uri);
            }
        }
    }


}
