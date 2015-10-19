package com.neirx.stopwatchtimer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.neirx.stopwatchtimer.fragments.BottomMenuFragment;
import com.neirx.stopwatchtimer.fragments.LapsFragment;
import com.neirx.stopwatchtimer.fragments.StopwatchFragment;
import com.neirx.stopwatchtimer.fragments.TimerPagerFragment;
import com.neirx.stopwatchtimer.fragments.VpStopwatchFragment;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;


public class MainActivity extends Activity implements ActionBar.OnNavigationListener {
    public static final String TAG = "ThisApp";
    private static final String CLASS_NAME = "<MainActivity> ";
    SettingsManagement settings;
    boolean isSoundOn, isVisibleClearMenu;
    int screenOrientation;
    FragmentManager fragmentManager;
    VpStopwatchFragment vpStopwatchFragment;
    BottomMenuFragment bottomMenuFragment;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        fragmentManager.putFragment(outState, "vpStopwatchFragment", vpStopwatchFragment);
        fragmentManager.putFragment(outState, "bottomMenuFragment", bottomMenuFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
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
        if(actionBar != null) {
            //убрать название приложения
            actionBar.setDisplayShowTitleEnabled(false);
            //установка навигиции с помощью выпадающего списка
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.drop_down_navigation,
                    android.R.layout.simple_spinner_dropdown_item);
            actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        }

        settings = AppSettings.getInstance(this);
        isSoundOn = settings.getBoolPref(SettingPref.Bool.soundState);

        fragmentManager = getFragmentManager();

        //vpStopwatchFragment = VpStopwatchFragment.newInstance();
        if(savedInstanceState == null) {
            vpStopwatchFragment = VpStopwatchFragment.newInstance();
            bottomMenuFragment = BottomMenuFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.mainContainer, vpStopwatchFragment);
            fragmentTransaction.add(R.id.mainContainerBottom, bottomMenuFragment);
            //toBackStack(fragmentTransaction);
            fragmentTransaction.commit();
        } else {
            vpStopwatchFragment = (VpStopwatchFragment) fragmentManager.getFragment(savedInstanceState, "vpStopwatchFragment");
            bottomMenuFragment = (BottomMenuFragment) fragmentManager.getFragment(savedInstanceState, "bottomMenuFragment");
        }



    }

    /**
     * Установка ориентации экрана приложения
     * @param orientation сохраненная в настройках ориентация
     */
    private void switchScreenOrientation(int orientation){
        switch (orientation){
            case Statical.SCREEN_ORIENTATION_SYSTEM:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
            case Statical.SCREEN_ORIENTATION_PORTRAIT:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Statical.SCREEN_ORIENTATION_LANDSCAPE:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Statical.SCREEN_ORIENTATION_AUTO:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
        }
    }

    /**
     * Метод для сообщения фрагменту BottomMenuFragment, что произошло нажатие на
     * view секундомера.
     */
    public void clickedStopwatch(){
        if(bottomMenuFragment != null){
            bottomMenuFragment.clickedStopwatch();
        }
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
    public void setVisibleClearMenu(boolean visible){
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
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            case R.id.action_clear_laps:
                getLapsFragment().clearLaps();
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
                return true;
        }

        return super.onOptionsItemSelected(item);
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
        if (isVisibleClearMenu){
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
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, CLASS_NAME + "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroy");
    }
    //-------------------- Методы жизненного цикла(END) --------------------/
}
