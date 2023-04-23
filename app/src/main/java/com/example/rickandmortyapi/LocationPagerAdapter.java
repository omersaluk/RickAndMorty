package com.example.rickandmortyapi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class LocationPagerAdapter extends FragmentPagerAdapter {

    private List<String> locations;

    public LocationPagerAdapter(FragmentManager fm, List<String> locations) {
        super(fm);
        this.locations = locations;
    }

    @Override
    public Fragment getItem(int position) {
        return LocationFragment.newInstance(locations.get(position));
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return locations.get(position);
    }
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
