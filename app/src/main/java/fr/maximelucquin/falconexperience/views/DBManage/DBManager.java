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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.MainActivity;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceListActivity;

public class DBManager extends AppCompatActivity {

    private int DB_READ_CODE = 2;
    private int DB_SHM_READ_CODE = 3;
    private int DB_WAL_READ_CODE = 4;

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
        findDBFile(DB_READ_CODE);
    }
    public void openImportDBWAL(View view) {
        findDBFile(DB_WAL_READ_CODE);
    }
    public void openImportDBSHM(View view) {
        findDBFile(DB_SHM_READ_CODE);
    }

    public void openImportDBRessource(View view) {
        copyAttachedDatabaseFromRessource();
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
            File dbFileSHM = new File(this.getDatabasePath(dbName).getAbsolutePath()+"-shm");
            File dbFileWAL = new File(this.getDatabasePath(dbName).getAbsolutePath()+"-wal");
            //System.out.println(this.getDatabasePath(dbName).getAbsolutePath());
            FileInputStream fis = new FileInputStream(dbFile);
            FileInputStream fisSHM = new FileInputStream(dbFileSHM);
            FileInputStream fisWAL = new FileInputStream(dbFileWAL);

            String outFileName = directoryName + File.separator +
                    dbName;
            String outFileNameSHM = directoryName + File.separator +
                    dbName+ "-shm";
            String outFileNameWAL = directoryName + File.separator +
                    dbName+ "-wal";

            //System.out.println(outFileName);

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

            OutputStream outputSHM = new FileOutputStream(outFileNameSHM);

            // Transfer bytes from the inputfile to the outputfile
            byte[] bufferSHM = new byte[1024];
            int lengthSHM;
            while ((lengthSHM = fisSHM.read(bufferSHM)) > 0) {
                outputSHM.write(bufferSHM, 0, lengthSHM);
            }
            // Close the streams
            outputSHM.flush();
            outputSHM.close();
            fisSHM.close();

            OutputStream outputWAL = new FileOutputStream(outFileNameWAL);

            // Transfer bytes from the inputfile to the outputfile
            byte[] bufferWAL = new byte[1024];
            int lengthWAL;
            while ((lengthWAL = fisWAL.read(bufferWAL)) > 0) {
                outputWAL.write(bufferWAL, 0, lengthWAL);
            }
            // Close the streams
            outputWAL.flush();
            outputWAL.close();
            fisWAL.close();

            Toast.makeText(this, "Base exportée",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("dbBackup:", e.getMessage());
        }
    }

    public void findDBFile(int code) {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");
        startActivityForResult(intent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if ((requestCode == DB_READ_CODE || requestCode == DB_SHM_READ_CODE || requestCode == DB_WAL_READ_CODE) && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                System.out.println(uri.toString());
                copyAttachedDatabase(uri, requestCode);

            }
        }
    }

    private void copyAttachedDatabase(Uri uri, int code) {
        File dbPath = new File(this.getDatabasePath(dbName).getAbsolutePath());
        if (code == DB_SHM_READ_CODE) {
            dbPath = new File(this.getDatabasePath(dbName).getAbsolutePath()+"-shm");
        } else if (code == DB_WAL_READ_CODE) {
            dbPath = new File(this.getDatabasePath(dbName).getAbsolutePath()+"-wal");
        }

        System.out.println(this.getDatabasePath(dbName).getAbsolutePath());
        // If the database already exists, return
        /*if (dbPath.exists()) {
            System.out.println("DB already Exist");
            return;
        }*/

        // Make sure we have a path to the file
        //dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = getContentResolver().openInputStream(uri);
            //Log.e("InputStream Size","Size " + inputStream);
            final OutputStream output = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            inputStream.close();


            AppDatabase.destroyInstance();
            AppDatabase.getAppDatabase(getApplicationContext());

            Toast.makeText(this, "Base importée",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void copyAttachedDatabaseFromRessource() {
        getResources().openRawResource(R.raw.falcon_experience_db);
        File dbPath = new File(this.getDatabasePath(dbName).getAbsolutePath());
        File dbPathSHM = new File(this.getDatabasePath(dbName).getAbsolutePath()+"-shm");
        File dbPathWAL = new File(this.getDatabasePath(dbName).getAbsolutePath()+"-wal");


        System.out.println(this.getDatabasePath(dbName).getAbsolutePath());
        // If the database already exists, return
        /*if (dbPath.exists()) {
            System.out.println("DB already Exist");
            return;
        }*/

        // Make sure we have a path to the file
        //dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = getResources().openRawResource(R.raw.falcon_experience_db);
            //Log.e("InputStream Size","Size " + inputStream);
            final OutputStream output = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            inputStream.close();

            final InputStream inputStreamSHM = getResources().openRawResource(R.raw.falcon_experience_db_shm);
            //Log.e("InputStream Size","Size " + inputStream);
            final OutputStream outputSHM = new FileOutputStream(dbPathSHM);

            byte[] bufferSHM = new byte[8192];
            int lengthSHM;

            while ((lengthSHM = inputStreamSHM.read(bufferSHM, 0, 8192)) > 0) {
                outputSHM.write(bufferSHM, 0, lengthSHM);
            }

            outputSHM.flush();
            outputSHM.close();
            inputStreamSHM.close();

            final InputStream inputStreamWAL = getResources().openRawResource(R.raw.falcon_experience_db_wal);
            //Log.e("InputStream Size","Size " + inputStream);
            final OutputStream outputWAL = new FileOutputStream(dbPathWAL);

            byte[] bufferWAL = new byte[8192];
            int lengthWAL;

            while ((lengthWAL = inputStreamWAL.read(bufferWAL, 0, 8192)) > 0) {
                outputWAL.write(bufferWAL, 0, lengthWAL);
            }

            outputWAL.flush();
            outputWAL.close();
            inputStreamWAL.close();


            AppDatabase.destroyInstance();
            AppDatabase.getAppDatabase(getApplicationContext());

            Toast.makeText(this, "Base initiale importée",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }



}
