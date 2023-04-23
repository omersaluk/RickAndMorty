package com.example.rickandmortyapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rickandmortyapi.databinding.FragmentLocationBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LocationFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Character> characterList;
    private ApiService apiService;
    private static final String ARG_LOCATION_NAME = "location_name";

    public LocationFragment() {

    }

    public static LocationFragment newInstance(String locationName) {


        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_NAME, locationName);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);


        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService = retrofit.create(ApiService.class);

        return view;

    }
    private void loadFilteredData(String locationName) {
        List<Character> filteredCharacters = new ArrayList<>();

        for (Map.Entry<Integer, Character> entry : characterCache.entrySet()) {
            Character character = entry.getValue();

            if (character.getLocation().getName().equals(locationName)) {
                filteredCharacters.add(character);
            }
        }

        CharacterAdapter adapter = new CharacterAdapter(filteredCharacters);
        recyclerView.setAdapter(adapter);
    }




    private List<Character> allCharacters = new ArrayList<>();
    private Set<Integer> addedCharacterIds = new HashSet<>();
    private Map<Integer, Character> characterCache = new HashMap<>();

    @Override
    public void onResume() {
        super.onResume();

        Bundle args = getArguments();
        String locationName = args.getString(ARG_LOCATION_NAME, "");

        if (allCharacters.isEmpty()) {
            for (int i = 1; i <= 183; i++) {
                Call<CharacterResponse> call = apiService.getCharacters(i);

                call.enqueue(new Callback<CharacterResponse>() {
                    @Override
                    public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                        if (response.isSuccessful()) {
                            List<Character> characters = response.body().getResults();

                            for (Character character : characters) {
                                int characterId = character.getId();

                                if (!characterCache.containsKey(characterId)) {

                                    if (addedCharacterIds.add(characterId)) {
                                        characterCache.put(characterId, character);
                                    }
                                }
                            }

                            loadFilteredData(locationName);
                        }
                    }

                    @Override
                    public void onFailure(Call<CharacterResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Hata: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            for (Character character : allCharacters) {
                int characterId = character.getId();


                if (!characterCache.containsKey(characterId)) {
                    characterCache.put(characterId, character);
                }
            }

            loadFilteredData(locationName);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        characterCache.clear();
        addedCharacterIds.clear();
        allCharacters.clear();
    }
}