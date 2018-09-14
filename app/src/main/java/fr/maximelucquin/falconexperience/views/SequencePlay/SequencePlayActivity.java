package fr.maximelucquin.falconexperience.views.SequencePlay;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.Sequence.StepAdapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import static fr.maximelucquin.falconexperience.views.SequencePlay.SequencePlayActivity.PlayStatus.*;

public class SequencePlayActivity extends AppCompatActivity {

    private Sequence sequence;
    private List<Step> steps;
    private List<Item> items;
    private PlayStatus playStatus;

    private StepAdapter stepAdapter;
    private SequencePlayItemAdapter itemAdapter;


    private Runnable player;

    private RecyclerView stepRecycler;
    private RecyclerView itemRecycler;
    private TextView statusView;
    private ImageButton playBackButton;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton stopButton;

    public enum PlayStatus {
        PLAY,
        PAUSE,
        STOP
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_play);

        String sequenceId = getIntent().getExtras().getString("sequenceId");
        sequence = AppDatabase.getAppDatabase(getApplicationContext()).sequenceDAO().getSequence(sequenceId);
        steps = sequence.getSteps(getApplicationContext());

        items = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getAllItems();

        stepRecycler = (RecyclerView) findViewById(R.id.playSequenceRecycler);
        itemRecycler = (RecyclerView) findViewById(R.id.playItemRecycler);
        statusView = (TextView) findViewById(R.id.playStatus);
        playBackButton = (ImageButton) findViewById(R.id.playBack);
        playButton = (ImageButton) findViewById(R.id.playButton);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        stopButton = (ImageButton) findViewById(R.id.stopButton);

        stepRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setLayoutManager(new GridLayoutManager(this, 6));

        stepAdapter = new StepAdapter(steps, getApplicationContext());
        stepRecycler.setAdapter(stepAdapter);

        itemAdapter = new SequencePlayItemAdapter(items, getApplicationContext());
        itemRecycler.setAdapter(itemAdapter);

        setPlayStatus(2);

    }

    public void onPlayClick(View view) {
        if (playStatus == PAUSE || playStatus == STOP) {
            setPlayStatus(0);
        }
    }

    public void onPauseClick(View view) {
        if (playStatus == PLAY) {
            setPlayStatus(1);
        }
    }

    public void onStopClick(View view) {
        if (playStatus == PLAY) {
            setPlayStatus(2);
        }
    }

    public void onBackClick(View view) {
        if (playStatus == STOP) {
            setPlayStatus(3);
        }
    }

    public void setPlayStatus(int button) {
        switch (button) {
            case 0:
                //play
                playButton.setEnabled(false);
                playButton.setAlpha(.3f);
                playBackButton.setEnabled(false);
                playBackButton.setAlpha(.3f);
                pauseButton.setEnabled(true);
                pauseButton.setAlpha(1f);
                stopButton.setEnabled(true);
                stopButton.setAlpha(1f);
                playStatus = PLAY;
                statusView.setText("PLAY");
                launchPlayer();
                break;
            case 1:
                //pause
                playButton.setEnabled(true);
                playButton.setAlpha(1f);
                playBackButton.setEnabled(false);
                playBackButton.setAlpha(.3f);
                pauseButton.setEnabled(false);
                pauseButton.setAlpha(.3f);
                stopButton.setEnabled(false);
                stopButton.setAlpha(.3f);
                playStatus = PAUSE;
                statusView.setText("PAUSE");
                break;
            case 2:
                //stop
                playButton.setEnabled(true);
                playButton.setAlpha(1f);
                playBackButton.setEnabled(true);
                playBackButton.setAlpha(1f);
                pauseButton.setEnabled(false);
                pauseButton.setAlpha(.3f);
                stopButton.setEnabled(false);
                stopButton.setAlpha(.3f);
                playStatus = STOP;
                statusView.setText("STOP");
                break;
            case 3:
                //back
                playButton.setEnabled(true);
                playButton.setAlpha(1f);
                playBackButton.setEnabled(true);
                playBackButton.setAlpha(1f);
                pauseButton.setEnabled(false);
                pauseButton.setAlpha(.3f);
                stopButton.setEnabled(false);
                stopButton.setAlpha(.3f);
                playStatus = STOP;
                statusView.setText("STOP");
                break;
            default:
                break;
        }
    }

    public void launchPlayer() {
        player = new Runnable() {
            //int count = 0;

            public void run() {

                while (playStatus == PLAY) {
                    /*if (mOnBPMListener != null)
                        mOnBPMListener.onBPM(count);
                    long millis = System.currentTimeMillis();
                    for (int i = 0; i < rows; i++) {
                        System.out.println("Row-COl " + i + "-" + count);
                        if (matrix.getCellValue(i, count) != 0)
                            sound.play(samples[i], 100, 100, 1, 0, 1);
                    }*/

                    //count = (count + 1) % beats;
                    //long next = (60 * 1000) / bpm;
                    try {
                        //Thread.sleep(next - (System.currentTimeMillis() - millis));
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };

        //playing = true;
        Thread thandler = new Thread(player);
        thandler.start();
    }
}
