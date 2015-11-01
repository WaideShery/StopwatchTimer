package com.neirx.stopwatchtimer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neirx.stopwatchtimer.MainActivity;
import com.neirx.stopwatchtimer.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class VpStopwatchFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private static final String CLASS_NAME = "<StopwatchPagerFragment> ";
    FragmentManager fragmentManager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private static StopwatchFragment stopwatchFragment;
    private static LapsFragment lapsFragment;
    private String savedStopwatchFragment = "savedStopwatchFragment";
    private String savedLapsFragment = "savedLapsFragment";
    MainActivity activity;

    public static VpStopwatchFragment newInstance() {
        return new VpStopwatchFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's state here
        if(stopwatchFragment.isAdded()) {
            fragmentManager.putFragment(outState, "stopwatchFragment", stopwatchFragment);
            outState.putBoolean(savedStopwatchFragment, true);
        }
        if(lapsFragment.isAdded()) {
            fragmentManager.putFragment(outState, "lapsFragment", lapsFragment);
            outState.putBoolean(savedLapsFragment, true);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_stopwatch_pager, container, false);

        fragmentManager = getChildFragmentManager();
        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean(savedStopwatchFragment, false)) {
                stopwatchFragment = (StopwatchFragment) fragmentManager
                        .getFragment(savedInstanceState, "stopwatchFragment");
            }
            if(savedInstanceState.getBoolean(savedLapsFragment, false)) {
                lapsFragment = (LapsFragment) fragmentManager
                        .getFragment(savedInstanceState, "lapsFragment");
            }
        } else {
            stopwatchFragment = StopwatchFragment.newInstance();
            lapsFragment = LapsFragment.newInstance();
        }

        // Создание адаптера, который будет возвращать фрагмент для каждой из
        // основных секций/разделов активити.
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);

        // Настраивает ViewPager адаптером секций/разделов.

        mViewPager = (ViewPager) rootView.findViewById(R.id.stopwatchPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        activity = (MainActivity) getActivity();

        return rootView;
    }

    public LapsFragment getLapsFragment() {
        return lapsFragment;
    }

    public StopwatchFragment getStopwatchFragment() {
        return stopwatchFragment;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (activity != null) {
            switch (position) {
                case 0:
                    activity.setVisibleClearMenu(false);
                    break;
                case 1:
                    activity.setVisibleClearMenu(true);
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * Наследуется от {Android.support.v13.app.FragmentPagerAdapter}. Возвращает фрагмент,
     * соответствующий одному из разделов/вкладок/страниц.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // getItem вызывается для получения экземпляра фрагмента для данной страницы.
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (activity != null) {
                    activity.setVisibleClearMenu(false);
                }
                if (stopwatchFragment == null) {
                    stopwatchFragment = StopwatchFragment.newInstance();
                }
                return stopwatchFragment;
            } else {
                if (lapsFragment == null) {
                    lapsFragment = LapsFragment.newInstance();
                }
                return lapsFragment;
            }
        }

        @Override
        public int getCount() {
            // Показывает общее количество страниц.
            return 2;
        }

    }

    /*/-------------------- Методы жизненного цикла(BEGIN) --------------------
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(MainActivity.TAG, CLASS_NAME + "onAttach");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreate");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onActivityCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, CLASS_NAME + "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDetach");
    }
    //-------------------- Методы жизненного цикла(END) --------------------*/
}
