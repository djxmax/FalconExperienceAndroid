package fr.maximelucquin.falconexperience.views.SequencePlay;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.Triggeer;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.Sequence.StepActivity;
import fr.maximelucquin.falconexperience.views.Sequence.StepAdapter;
import fr.maximelucquin.falconexperience.views.StepDetails.StepDetailsActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.maximelucquin.falconexperience.views.SequencePlay.SequencePlayActivity.PlayStatus.*;


public class SequencePlayActivity extends AppCompatActivity {

    public static int LOOP_WAIT = 1000;

    private Sequence sequence;
    private List<Step> steps;
    private List<Item> items;
    private Map<String,Item> itemsMap;
    private int currentStep;
    private PlayStatus playStatus;
    private long lastStepTime;
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
        convertItemsToMap();

        currentStep = -1;

        stepRecycler = (RecyclerView) findViewById(R.id.playSequenceRecycler);
        itemRecycler = (RecyclerView) findViewById(R.id.playItemRecycler);
        statusView = (TextView) findViewById(R.id.playStatus);
        playBackButton = (ImageButton) findViewById(R.id.playBack);
        playButton = (ImageButton) findViewById(R.id.playButton);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        stopButton = (ImageButton) findViewById(R.id.stopButton);

        stepRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setLayoutManager(new GridLayoutManager(this, 6));

        stepAdapter = new StepAdapter(steps, getApplicationContext(), currentStep);
        stepRecycler.setAdapter(stepAdapter);

        itemAdapter = new SequencePlayItemAdapter(items, getApplicationContext());
        itemRecycler.setAdapter(itemAdapter);

        setPlayStatus(2);

        itemRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), itemRecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        if (items.get(position).isEnabled()) {
                            items.get(position).setEnabled(false);
                        } else {
                            items.get(position).setEnabled(true);
                        }
                        itemAdapter = new SequencePlayItemAdapter(items, getApplicationContext());
                        itemRecycler.setAdapter(itemAdapter);
                        convertItemsToMap();
                    }
                })
        );

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
                lastStepTime = System.currentTimeMillis();
                if (currentStep == -1) {
                    currentStep = 0;
                }
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
                currentStep = -1;
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
                currentStep = -1;
                break;
            default:
                break;
        }
        stepAdapter = new StepAdapter(steps, getApplicationContext(), currentStep);
        stepRecycler.setAdapter(stepAdapter);
    }

    public void launchPlayer() {
        final Handler handler = new Handler();
        player = new Runnable() {
            //int count = 0;

            public void run() {

                while (playStatus == PLAY) {
                    if (steps == null) {
                        handler.post(new Runnable() {
                            public void run() {
                                setPlayStatus(2);
                            }
                        });
                        return;
                    }

                    if (currentStep >= steps.size()) {
                        handler.post(new Runnable() {
                            public void run() {
                                setPlayStatus(2);
                            }
                        });
                        return;
                    }

                    Step step = steps.get(currentStep);

                    boolean triggerOk = checkTriggerOk(step);

                    if (triggerOk) {
                        //do things
                        currentStep++;
                        //stepAdapter = new StepAdapter(steps, getApplicationContext(), currentStep);
                        //stepRecycler.setAdapter(stepAdapter);
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            stepAdapter = new StepAdapter(steps, getApplicationContext(), currentStep);
                            stepRecycler.setAdapter(stepAdapter);
                        }
                    });


                    try {
                        Thread.sleep(LOOP_WAIT);
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

    private boolean checkTriggerOk(Step step) {
        Triggeer triggeer = step.getTriggeerWithoutReload(getApplicationContext());
        if (step.timeTrigger != 0) {
            if (lastStepTime <= System.currentTimeMillis()) {
                return true;
            }
            return false;
        } else if (triggeer != null) {
            boolean allOff = true;
            boolean allOn = true;
            List<Item> theItems = triggeer.getItemsWithoutReload(getApplicationContext());
            if (theItems == null) {
                return false;
            }
            for (Item thisItem: theItems) {

                Item item = itemsMap.get(thisItem.getItemId());
                System.out.println(item.getName()+" "+item.isEnabled());
                if (item.isEnabled()) {
                    allOff = false;
                } else {
                    allOn = false;
                }
            }

            if (triggeer.getType() != null && triggeer.getType() == Triggeer.TriggeerType.SWITCH_OFF) {
                return allOff;
            } else if (triggeer.getType() != null && triggeer.getType() == Triggeer.TriggeerType.SWITCH_ON){
                return allOn;
            }
        }

        return false;
    }

    private void convertItemsToMap() {
        if (itemsMap == null) {
            itemsMap = new HashMap<String,Item>();
        }
        for (Item i : items) itemsMap.put(i.getItemId(),i);
    }
}
