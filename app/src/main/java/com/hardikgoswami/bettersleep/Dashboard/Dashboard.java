package com.hardikgoswami.bettersleep.Dashboard;

import android.app.LoaderManager;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.hardikgoswami.bettersleep.Data.Source.SleepDataRepository;
import com.hardikgoswami.bettersleep.R;
import com.hardikgoswami.bettersleep.SleepPattern.SleepPatterPresenter;
import com.hardikgoswami.bettersleep.SleepPattern.SleepPatternContract;
import com.hardikgoswami.bettersleep.SleepPunch.SleepPunchContract;
import com.hardikgoswami.bettersleep.SleepPunch.SleepPunchPresenter;


public class Dashboard extends AppCompatActivity {
    public static final String TAG = "BETTERSLEEP";
    private Boolean exit = false;
    ViewPagerAdapter pagerAdapter;
    LoaderManager loaderManager;
    SleepDataRepository dataRepository;
    SleepPunchContract.Presenter presenterPunchIn;
    SleepPatternContract.Presenter presenterPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_act);
        // setup  toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setup viewpager & header strip
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
        Log.d(TAG, "Dashboard activity");
        // setup loadermanager
        loaderManager = getLoaderManager();
        // setup SleepData Repository
        dataRepository = new SleepDataRepository(this);
        // setup listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected : " + position);
                // TODO: Setup Presenter for appropriate fragment

                switch (position) {
                    case 0:
                        // punchin fragment
                        SleepPunchContract.View mViewPunch = (SleepPunchContract.View) getSupportFragmentManager()
                                .findFragmentByTag("android:switcher:" + R.id.vpPager + ":" + viewPager.getCurrentItem());

                        presenterPunchIn = new SleepPunchPresenter(loaderManager, dataRepository, mViewPunch);

                        break;
                    case 1:
                        // yognidra
                        break;
                    case 2:
                        // patter fragment
                        SleepPatternContract.View mViewPattern = (SleepPatternContract.View)  getSupportFragmentManager()
                                .findFragmentByTag("android:switcher:" + R.id.vpPager + ":" + viewPager.getCurrentItem());
                        presenterPattern = new SleepPatterPresenter(loaderManager,dataRepository,mViewPattern);
                        
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
