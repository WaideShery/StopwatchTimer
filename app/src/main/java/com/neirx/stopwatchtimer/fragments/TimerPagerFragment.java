package com.neirx.stopwatchtimer.fragments;

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
public class TimerPagerFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private static final String CLASS_NAME = "<StopwatchPagerFragment> ";
    FragmentManager fragmentManager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    public static TimerPagerFragment newInstance() {
        return new TimerPagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_timer_pager, container, false);

        fragmentManager = getChildFragmentManager();
/*
        if(savedInstanceState != null){
            bottomFragment = (BottomFragment) fragmentManager.getFragment(savedInstanceState, "bottomFragment");
        } else if(bottomFragment == null) {
            bottomFragment = BottomFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragBottom, bottomFragment);
            fragmentTransaction.commit();
        }
*/


        // Создание адаптера, который будет возвращать фрагмент для каждой из
        // основных секций/разделов активити.
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);

        // Настраивает ViewPager адаптером секций/разделов.

        mViewPager = (ViewPager) rootView.findViewById(R.id.timerPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(this);

        return rootView;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * Метод меняет икону в TopFragment в зависимости от выбранной
     * вкладки ViewPager.
     *
     * @param position id вкладки
     */
    @Override
    public void onPageSelected(int position) {
        /*
        if (getActivity() != null) {
            MainActivity activity = (MainActivity) getActivity();
            TopPagerFragment topFragment = activity.getTopPagerFragment();
            if (position == 0) {
                topFragment.setIconAlarmChanged();
                topFragment.setPosition(0);
            } else if (position == 1) {
                topFragment.setIconGraphicChanged();
                topFragment.setPosition(1);
            }
        }*/

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
                return TimerFragmet.newInstance();
            } else {
                return QueueFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Показывает общее количество страниц.
            return 2;
        }

    }
}
