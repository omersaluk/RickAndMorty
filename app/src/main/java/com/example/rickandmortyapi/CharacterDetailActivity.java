package com.example.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CharacterDetailActivity extends AppCompatActivity {
    private TextView statusChar, specyChar, genderChar, nameChar, createChar, episodeChar, locationChar, originChar;
    private ImageView imageView, backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        getSupportActionBar().hide();
        backImage = (ImageView) findViewById(R.id.backButton);
        nameChar = (TextView) findViewById(R.id.characterName);
        genderChar = (TextView) findViewById(R.id.gender);
        statusChar = (TextView) findViewById(R.id.status);
        specyChar = (TextView) findViewById(R.id.specy);
        createChar = (TextView) findViewById(R.id.created);
        episodeChar = (TextView) findViewById(R.id.episode);
        locationChar  = (TextView) findViewById(R.id.location);
        originChar = (TextView) findViewById(R.id.origin);
        imageView=(ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        String specy = intent.getStringExtra("species");
        String status = intent.getStringExtra("status");
        String image = intent.getStringExtra("image");
        String origin = intent.getStringExtra("origin");
        String location = intent.getStringExtra("location");
        String episode = intent.getStringExtra("episode");
        String created = intent.getStringExtra("created");

        List<String> episodeList = Arrays.asList(episode.substring(1, episode.length()-1).split(", "));
        for (int i=0; i<episodeList.size(); i++) {
            String[] parts = episodeList.get(i).split("/");
            episodeList.set(i, parts[parts.length-1]);
        }
        String episodeString = TextUtils.join(", ", episodeList);

        // İlk olarak orijinal stringin formatını belirtiyoruz:
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
// Daha sonra istediğimiz formatı belirliyoruz:
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        try {
            // Orijinal stringi parçalayarak bir Date nesnesine dönüştürüyoruz:
            Date date = originalFormat.parse(created);
            // Date nesnesini istediğimiz formata dönüştürüyoruz:
            String formattedDatetime = targetFormat.format(date);

            // Elde ettiğimiz sonucu ekrana yazdırabiliriz:

            createChar.setText(formattedDatetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        nameChar.setText(name);
        originChar.setText(origin);
        locationChar.setText(location);
        episodeChar.setText(episodeString);
        genderChar.setText(gender);
        specyChar.setText(specy);
        statusChar.setText(status);
        Picasso.get().load(image).into(imageView);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

}