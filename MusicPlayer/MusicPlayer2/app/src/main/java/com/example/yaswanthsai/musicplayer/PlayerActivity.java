package com.example.yaswanthsai.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    ImageButton previousButton;
    ImageButton currentSongButton;
    ImageButton nextButton;
    Boolean play = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        TextView playSongText;
        TextView playArtist;
        playSongText = findViewById(R.id.currentSongName);
        playArtist = findViewById(R.id.currentArtistName);
        Intent in = getIntent();
        playSongText.setText(in.getStringExtra("SONG_NAME"));
        playArtist.setText(in.getStringExtra("ARTIST_NAME"));
        previousButton = findViewById(R.id.previousSong);
        currentSongButton = findViewById(R.id.playCurrentSong);
        nextButton = findViewById(R.id.nextSong);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPerviousSong();
            }
        });

        currentSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playCurrentSong();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextSong();
            }
        });
    }

    //pervious Song Implementation
    private void playPerviousSong() {
        Toast.makeText(getApplicationContext(), "Plays Pervious Song", Toast.LENGTH_SHORT).show();
    }

    // Current song Implementation
    private void playCurrentSong() {
        if (play) {
            currentSongButton.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            Toast.makeText(getApplicationContext(), " Song is playing ", Toast.LENGTH_SHORT).show();
            play = false;
        } else {
            currentSongButton.setImageDrawable(getResources().getDrawable(R.drawable.playbutton));
            Toast.makeText(getApplicationContext(), " Song paused ", Toast.LENGTH_SHORT).show();
            play = true;
        }

    }

    //Next song Implementation

    private void playNextSong() {
        Toast.makeText(getApplicationContext(), "Plays Next Song", Toast.LENGTH_SHORT).show();
    }
}
