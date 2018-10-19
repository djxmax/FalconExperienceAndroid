package fr.maximelucquin.falconexperience.views.SequencePlay;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.Triggeer;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.Sequence.StepActivity;
import fr.maximelucquin.falconexperience.views.Sequence.StepAdapter;
import fr.maximelucquin.falconexperience.views.StepDetails.StepDetailsActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;
import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.maximelucquin.falconexperience.data.Actiion.ActiionType.ON;
import static fr.maximelucquin.falconexperience.views.SequencePlay.SequencePlayActivity.PlayStatus.*;


public class SequencePlayActivity extends AppCompatActivity implements ArduinoListener {

    public static int LOOP_WAIT = 200;

    private Arduino arduino;

    private Sequence sequence;
    private List<Step> steps;
    private List<Item> items;
    private Map<String,Item> itemsMap;
    private Map<Long, Actiion> taskDelayTodo;
    private Map<Long, Actiion> taskDurationTodo;
    private int currentStep;
    private PlayStatus playStatus;
    private long lastStepTime;
    private StepAdapter stepAdapter;
    private SequencePlayItemAdapter itemAdapter;

    private List<String> dataToSend;


    private Runnable player;

    private RecyclerView stepRecycler;
    private RecyclerView itemRecycler;
    private TextView statusView;
    private TextView arduinoStatus;
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

        arduino = new Arduino(this);
        arduino.addVendorId(6790);

        String sequenceId = getIntent().getExtras().getString("sequenceId");
        sequence = AppDatabase.getAppDatabase(getApplicationContext()).sequenceDAO().getSequence(sequenceId);
        steps = sequence.getSteps(getApplicationContext());

        items = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getAllItems();
        convertItemsToMap();

        lastStepTime = System.currentTimeMillis();
        currentStep = -1;

        dataToSend = new ArrayList<>();
        taskDelayTodo = new HashMap<Long, Actiion>();
        taskDurationTodo = new HashMap<Long, Actiion>();

        stepRecycler = (RecyclerView) findViewById(R.id.playSequenceRecycler);
        itemRecycler = (RecyclerView) findViewById(R.id.playItemRecycler);
        statusView = (TextView) findViewById(R.id.playStatus);
        arduinoStatus = (TextView) findViewById(R.id.arduinoState);
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
                        updateItemsRecycler();
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
        updateStepsRecycler();
    }

    public void updateStepsRecycler() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stepAdapter.setCurrentStep(currentStep);
                stepRecycler.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void updateItemsRecycler() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemRecycler.getAdapter().notifyDataSetChanged();
            }
        });
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
                        doActions(step);
                        currentStep++;
                        lastStepTime = System.currentTimeMillis();
                        //stepAdapter = new StepAdapter(steps, getApplicationContext(), currentStep);
                        //stepRecycler.setAdapter(stepAdapter);
                    }

                    updateStepsRecycler();

                    checkTaskTodo();

                    sendData();


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
            if (lastStepTime + step.timeTrigger <= System.currentTimeMillis()) {
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

    private void doActions(Step step) {
        List<Actiion> actions = step.getActiions(getApplicationContext());
        if (actions == null) {
            return;
        }

        for (Actiion action: actions) {
            generateStringToSend(action);

        }

    }

    private void convertItemsToMap() {
        if (itemsMap == null) {
            itemsMap = new HashMap<String,Item>();
        }
        for (Item i : items) itemsMap.put(i.getItemId(),i);
    }

    private void generateStringToSend(Actiion action) {
        List<Item> items = action.getItems(getApplicationContext());
        if(items == null) {
            return;
        }

        //System.out.println(action.delay);
        if (action.delay == 0) {
            dataToSend.addAll(actionStringGenerator(items,action, false));

            if (action.getDuration() != 0) {
                taskDurationTodo.put(System.currentTimeMillis(), action);
            }
        } else {
            taskDelayTodo.put(System.currentTimeMillis(), action);
        }
    }

    private List<String> actionStringGenerator(List<Item> items, Actiion action, boolean inv) {
        List <String> data = new ArrayList<>();
        for (Item item : items) {
            if (!inv) {
                String str = "#" + item.getName();
                if (action.getType() == ON) {
                    str = str + "-ON";
                } else {
                    str = str + "-OF";
                }

                if (action.getBlinkFreq() != 0) {
                    str = str + "$" + action.getBlinkFreq();
                }

                data.add(str);
            } else {
                String str = "#" + item.getName();
                if (action.getType() == ON) {
                    str = str + "-OF";
                } else {
                    str = str + "-ON";
                }

                data.add(str);
            }

        }
        return data;
    }

    private void checkTaskTodo() {
        Actiion actionToDelete = null;
        for (Map.Entry<Long, Actiion> entry : taskDelayTodo.entrySet()) {
            Long time = entry.getKey();
            Actiion action = entry.getValue();
            if (action.getDelay()+time > System.currentTimeMillis()) {
                dataToSend.addAll(actionStringGenerator(action.getItems(getApplicationContext()),action, false));
                actionToDelete = action;
            }
        }
        if (actionToDelete != null) {
            taskDelayTodo.remove(actionToDelete);
        }
        actionToDelete = null;

        for (Map.Entry<Long, Actiion> entry : taskDurationTodo.entrySet()) {
            Long time = entry.getKey();
            Actiion action = entry.getValue();
            if (action.getDuration()+time > System.currentTimeMillis()) {
                dataToSend.addAll(actionStringGenerator(action.getItems(getApplicationContext()),action, true));
                actionToDelete = action;
            }
        }
        if (actionToDelete != null) {
            taskDurationTodo.remove(actionToDelete);
        }
    }

    private void sendData() {
        //System.out.println("Coucou");
        String strTosend = "";
        for (String str: dataToSend) {
            strTosend = strTosend + str + " ";
        }
        System.out.println("SEND : "+strTosend);

        if (!strTosend.isEmpty() && arduino.isOpened()) {
            arduino.send(strTosend.getBytes());
            dataToSend.clear();
        }
        dataToSend.clear();
    }

    private void decodeData(String data) {
        System.out.println(data);
        String[] separated = data.split("#");
        for (int i = 0; i < separated.length;i++) {
            String str = separated[i];
            String value = str.trim();
            String[] values = value.split("-");
            if (values.length != 2) {
                continue;
            }

            String item = values[0];
            String actionFreq = values[1];
            String action = "";
            String freq = "";
            String[] values2 = actionFreq.split("$");
            if (values2.length == 1) {
                action = values2[0];
            } else if (values2.length == 2){
                action = values2[0];
                freq = values2[1];
            }
            //System.out.println("item : "+item+" action : "+action+" freq : "+freq);

            for (int j = 0; j < items.size(); j++) {
                if (items.get(j).getName().equals(item)) {
                    if (action.equals("ON")) {
                        items.get(j).enabled = true;
                    } else {
                        items.get(j).enabled = false;
                    }
                }
            }
        }

        updateItemsRecycler();
    }


    @Override
    protected void onStart() {
        super.onStart();
        arduino.setArduinoListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arduino.unsetArduinoListener();
        arduino.close();
    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        arduino.open(device);
        arduinoStatus.setTextColor(Color.GREEN);
    }

    @Override
    public void onArduinoDetached() {
        // arduino detached from phone
        arduinoStatus.setTextColor(Color.RED);
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {
        final String message = new String(bytes);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //arTextRecive.setText(arTextRecive.getText() + "\n" + message);
                decodeData(message);
            }});
        // new message received from arduino
    }

    @Override
    public void onArduinoOpened() {
        // you can start the communication

    }

    @Override
    public void onUsbPermissionDenied() {
        // Permission denied, display popup then
        //arInfo.setText("USB permission denied, retrying in 3 sec");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arduino.reopen();
            }
        }, 3000);
    }


}
