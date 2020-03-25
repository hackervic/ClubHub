package com.nucleus.events.clubhub.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nucleus.events.clubhub.fragment.Clubs_fragment;
import com.nucleus.events.clubhub.fragment.Events_fragment;
import com.nucleus.events.clubhub.fragment.Main_Home_Fragment;

public class Fragment_PageAdapter extends FragmentStatePagerAdapter {

    int nNooftabs;



    public Fragment_PageAdapter(FragmentManager fm, int nofotabs) {
        super(fm);
        this.nNooftabs = nofotabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Main_Home_Fragment main_home_fragment = new Main_Home_Fragment();
                return main_home_fragment;
            case 1:
                Clubs_fragment clubs_fragment = new Clubs_fragment();
                return clubs_fragment;
            case 2:
                Events_fragment events_fragment = new Events_fragment();
                return events_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nNooftabs;
        //run krna ab...
        //okk
    }
}
