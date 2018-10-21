package fr.maximelucquin.falconexperience.views.MediaLoader;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.net.URI;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Triggeer;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;

import static fr.maximelucquin.falconexperience.data.Item.ItemOutput.EXTERNAL;
import static fr.maximelucquin.falconexperience.data.Item.ItemOutput.INTERNAL;

public class MediaItemActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;

    private Item item;

    private VideoView videoView;
    private MediaController mediaController;
    private TextView itemOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_item);

        itemOutput = findViewById(R.id.itemOutput);
        videoView = findViewById(R.id.itemVideo);
        mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        String itemId = getIntent().getExtras().getString("itemId");
        item = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getItem(itemId);

        setItemOutput();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                item.setFileURL(uri.toString());
                System.out.println(uri.toString());
                //showImage(uri);
            }
        }
    }

    public void setItemOutput() {
        if (item.getOutput() != null) {
            switch (item.getOutput()) {

                case INTERNAL:
                    itemOutput.setText("Type : interne");
                    break;
                case EXTERNAL:
                    itemOutput.setText("Type : externe");
                    break;
            }
        } else {
            itemOutput.setText("Type : inconnu");
        }
    }

    public void findFile(View view) {

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
        if (item.type == Item.ItemType.VIDEO) {
            intent.setType("video/*");
        } else {
            intent.setType("audio/*");
        }

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void play(View view) {
        if (item.getFileURL() == null) {
            return;
        }
        Uri fileURI = Uri.parse(item.getFileURL());
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(fileURI);
        videoView.requestFocus();
        videoView.start();

    }

    public void stop(View view) {
        videoView.stopPlayback();
    }

    public void editType(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selection du type :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Interne");
        arrayAdapter.add("Externe");

        builderSingle.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        item.setOutput(INTERNAL);
                        break;
                    case 1:
                        item.setOutput(EXTERNAL);
                        break;
                    default:
                        break;
                }
                setItemOutput();
            }
        });
        builderSingle.show();
    }

    public void saveItem(View view) {
        item.save(getApplicationContext());
        super.onBackPressed();
    }

}
