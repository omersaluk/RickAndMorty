package com.example.rickandmortyapi;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LocationPagerAdapter adapter;
    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        adapter = new LocationPagerAdapter(getSupportFragmentManager(), new ArrayList<>());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black));
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.transparent));


        locationService = createLocationService();

            locationService.getLocations(1).enqueue(new Callback<LocationResponse>() {
                @Override
                public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                    if (response.isSuccessful()) {
                        List<String> locations = new ArrayList<>();
                        for (Location location : response.body().getResults()) {
                            locations.add(location.getName());
                        }
                        adapter.setLocations(locations);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<LocationResponse> call, Throwable t) {
                    // handle error
                }
            });
        }

        private LocationService createLocationService () {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(LocationService.class);

    }
}
