package com.neirx.stopwatchtimer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.neirx.neirdialogs.interfaces.MessageDialog;
import com.neirx.neirdialogs.interfaces.NeirDialogInterface;
import com.neirx.stopwatchtimer.fragments.BottomMenuFragment;
import com.neirx.stopwatchtimer.fragments.LapsFragment;
import com.neirx.stopwatchtimer.fragments.StopwatchFragment;
import com.neirx.stopwatchtimer.fragments.TimerPagerFragment;
import com.neirx.stopwatchtimer.fragments.VpStopwatchFragment;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends Activity implements ActionBar.OnNavigationListener, NeirDialogInterface.OnClickListener {
    public static final String TAG = "ThisApp";
    private static final String CLASS_NAME = "<MainActivity> ";
    SettingsManagement settings;
    boolean isSoundOn, isVisibleClearMenu;
    int screenOrientation;
    FragmentManager fragmentManager;
    VpStopwatchFragment vpStopwatchFragment;
    BottomMenuFragment bottomMenuFragment;
    protected PowerManager.WakeLock mWakeLock;
    PowerManager pm;
    MediaPlayer clearLapsSound;
    Set<SoundStateListener> soundStateListeners;
    private boolean wasKeepBright, isRunNow;
    private final String whatDisplay = "whatDisplay";
    private final int SHOW_STOPWATCH = 1;
    private final int notifyId = 412;


    public void setSoundStateListener(SoundStateListener listener) {
        if (soundStateListeners == null) {
            soundStateListeners = new HashSet<>();
        }
        soundStateListeners.add(listener);
    }

    public interface SoundStateListener {
        void onChangeState(boolean state);
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        outState.putBoolean("isRunNow", isRunNow);
        fragmentManager.putFragment(outState, "vpStopwatchFragment", vpStopwatchFragment);
        fragmentManager.putFragment(outState, "bottomMenuFragment", bottomMenuFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        screenOrientation = settings.getIntPref(SettingPref.Int.screenOrientation, Statical.SCREEN_ORIENTATION_SYSTEM);
        switchScreenOrientation(screenOrientation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Настройка ActionBar
        ActionBar actionBar = getActionBar();
        String[] titleList = getResources().getStringArray(R.array.drop_down_navigation);
        setTitle("Секундомер");

        if (actionBar != null) {
            /*/убрать название приложения
            actionBar.setDisplayShowTitleEnabled(false);
            //установка навигиции с помощью выпадающего списка
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.drop_down_navigation,
                    android.R.layout.simple_spinner_dropdown_item);
            actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
            //*/
        }
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        settings = AppSettings.getInstance(this);
        isSoundOn = settings.getBoolPref(SettingPref.Bool.soundState);

        fragmentManager = getFragmentManager();

        //vpStopwatchFragment = VpStopwatchFragment.newInstance();
        if (savedInstanceState == null) {
            isRunNow = settings.getBoolPref(SettingPref.Bool.isStopwatchRun);
            vpStopwatchFragment = VpStopwatchFragment.newInstance();
            bottomMenuFragment = BottomMenuFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.mainContainer, vpStopwatchFragment);
            fragmentTransaction.add(R.id.mainContainerBottom, bottomMenuFragment);
            //toBackStack(fragmentTransaction);
            fragmentTransaction.commit();
        } else {
            isRunNow = savedInstanceState.getBoolean("isRunNow", false);
            vpStopwatchFragment = (VpStopwatchFragment) fragmentManager.getFragment(savedInstanceState, "vpStopwatchFragment");
            bottomMenuFragment = (BottomMenuFragment) fragmentManager.getFragment(savedInstanceState, "bottomMenuFragment");
        }
        clearLapsSound = MediaPlayer.create(this, R.raw.sw_clearlaps_btn);
    }

    /**
     * Установка ориентации экрана приложения
     *
     * @param orientation сохраненная в настройках ориентация
     */
    private void switchScreenOrientation(int orientation) {
        switch (orientation) {
            case Statical.SCREEN_ORIENTATION_SYSTEM:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
            case Statical.SCREEN_ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Statical.SCREEN_ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Statical.SCREEN_ORIENTATION_AUTO:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
        }
    }

    /**
     * Метод для сообщения фрагменту BottomMenuFragment, что произошло нажатие на
     * view секундомера.
     */
    public void clickedStopwatch(boolean isRun) {
        if (bottomMenuFragment != null) {
            bottomMenuFragment.clickedStopwatch(isRun);
        }
        isRunNow = isRun;
        checkWakeLock();
    }

    public void switchStopwatch(boolean isRun) {
        isRunNow = isRun;
        checkWakeLock();
    }

    private void checkWakeLock() {
        Log.d(MainActivity.TAG, CLASS_NAME + "checkBright isRunNow = " + isRunNow);
        if (isRunNow) {

            boolean isNotScreenDim = settings.getBoolPref(SettingPref.Bool.isNotScreenDim, false);
            Log.d(MainActivity.TAG, CLASS_NAME + "checkBright isNotScreenDim = " + isNotScreenDim);
            brightLock(isNotScreenDim);
        } else {
            wakeLock();
        }
    }


    public void brightLock(boolean keepBright) {
        if (keepBright) {
            if (mWakeLock == null || !wasKeepBright) {
                if (mWakeLock != null) try {
                    mWakeLock.release();
                } catch (Throwable th) {
                }
                mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ON_AFTER_RELEASE, "BrightOnTag");
            }
            mWakeLock.acquire();
            wasKeepBright = true;
        } else {
            wakeLock();
        }
    }

    public void wakeLock() {
        if (mWakeLock == null || wasKeepBright) {
            if (mWakeLock != null) try {
                mWakeLock.release();
            } catch (Throwable ignored) {
            }
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK |
                    PowerManager.ON_AFTER_RELEASE, "ScreenOnTag");
        }
        mWakeLock.acquire();
        wasKeepBright = false;
    }

    /**
     * Возвращает ссылку на фрагмент LapsFragment
     */
    public LapsFragment getLapsFragment() {
        return vpStopwatchFragment.getLapsFragment();
    }

    /**
     * Возвращает ссылку на фрагмент StopwatchFragment
     */
    public StopwatchFragment getStopwatchFragment() {
        return vpStopwatchFragment.getStopwatchFragment();
    }

    /**
     * Скрытие пункта меню для очистки списка кругов
     */
    public void setVisibleClearMenu(boolean visible) {
        isVisibleClearMenu = visible;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                ThisApp.tracker().send(new HitBuilders.EventBuilder("ui", "open")
                        .setLabel("settings")
                        .build());
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            case R.id.action_clear_laps:
                if (getLapsFragment().lapsNotEmpty()) {
                    showClearLapsDialog();
                }
                return true;
            case R.id.action_sound:
                if (isSoundOn) {
                    item.setIcon(R.drawable.sound_off);
                    isSoundOn = false;
                    settings.setPref(SettingPref.Bool.soundState, false);
                } else {
                    item.setIcon(R.drawable.sound_on);
                    isSoundOn = true;
                    settings.setPref(SettingPref.Bool.soundState, true);
                }
                for (SoundStateListener listener : soundStateListeners) {
                    if (listener != null) listener.onChangeState(isSoundOn);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearLapsDialog() {
        Resources res = getResources();
        CustomDialogFactory dialogFactory = CustomDialogFactory.newInstance(this);
        MessageDialog clearLapsDialog = dialogFactory.createMessageDialog();
        clearLapsDialog.setOnClickListener(this, "clearLapsDialog");
        clearLapsDialog.setPositiveButton(res.getString(R.string.yes_btn));
        clearLapsDialog.setNegativeButton(res.getString(R.string.cancel_btn));
        clearLapsDialog.setMessage(res.getString(R.string.clear_laps_message));
        clearLapsDialog.setTitle(res.getString(R.string.clear_laps));
        clearLapsDialog.show(fragmentManager, "clearLapsDialog");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem soundItem = menu.findItem(R.id.action_sound);
        if (isSoundOn) {
            soundItem.setIcon(R.drawable.sound_on);
        } else {
            soundItem.setIcon(R.drawable.sound_off);
        }
        MenuItem clearItem = menu.findItem(R.id.action_clear_laps);
        if (isVisibleClearMenu) {
            clearItem.setVisible(true);
        } else {
            clearItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Log.d(TAG, CLASS_NAME + "onNavigationItemSelected");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (itemPosition) {
            case 0:
                fragmentTransaction.replace(R.id.mainContainer, vpStopwatchFragment);
                break;
            case 1:
                fragmentTransaction.replace(R.id.mainContainer, TimerPagerFragment.newInstance());
                break;
        }
        fragmentTransaction.commit();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
        checkWakeLock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroy");
    }

    @Override
    public void onClick(String tag, int buttonId, Object extraData) {
        switch (buttonId) {
            case NeirDialogInterface.BUTTON_POSITIVE:
                if (tag.equals("clearLapsDialog")) {
                    getLapsFragment().clearLaps();
                    if (isSoundOn) clearLapsSound.start();
                }
                break;
            case NeirDialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    //-------------------- Методы жизненного цикла(BEGIN) --------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onRestart");
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
    }*/
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, CLASS_NAME + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop");
        if (mWakeLock != null) {
            try {
                mWakeLock.release();
            } catch (Throwable th) {
            }
            mWakeLock = null;
        }
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        if(isRunNow){
           long totalTime = getStopwatchFragment().getTotalTime();
            showStopwatchNotify(totalTime);
        }
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop notify");
    }

    private void showStopwatchNotify(long startTime){
        Notification.Builder notifyBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notify_icon)
                //.setContentText("")
                .setContentTitle(getResources().getString(R.string.stopwatch))
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis() - startTime)
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(whatDisplay, SHOW_STOPWATCH);
        /*
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
                */
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notifyBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, notifyBuilder.build());
    }


    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroy");
    }*/
    //-------------------- Методы жизненного цикла(END) --------------------/
}
