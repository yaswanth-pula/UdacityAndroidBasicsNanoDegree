package com.example.yaswanthsai.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SongLibrary extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Song> songs = new ArrayList<>();
    SongAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songlist);
        recyclerView = findViewById(R.id.songsListView);
        mAdapter = new SongAdapter(songs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getApplicationContext(), recyclerView, new RecyclerClickListener.ClickListener() {
            // Single Click
            @Override
            public void onClick(View view, int position) {
                // Launch the song player activity
                Song song = songs.get(position);
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                // pass the song information to be played
                intent.putExtra("SONG_NAME", song.getSongName());
                intent.putExtra("ARTIST_NAME", song.getArtistName());
                startActivity(intent);
            }


            @Override
            public void onLongClick(View view, int position) {

                Song dSong = songs.get(position);
                String songDetails = "" + dSong.getSongName() + "\n" + dSong.getArtistName();
                Toast.makeText(getApplicationContext(), songDetails, Toast.LENGTH_SHORT).show();
            }
        }));

        recyclerView.setAdapter(mAdapter);
        addSongs();
    }


    private void addSongs() {
        songs.add(new Song("Don't Let Me Down", "ChainSmokers"));
        songs.add(new Song("Galway Girl", "Ed sheeran"));
        songs.add(new Song("Stitches", "Shawn Mendes"));
        songs.add(new Song("Let Me Love You", "DJ Snake"));
        songs.add(new Song("Not Afraid", "Eminem"));
        songs.add(new Song("Numb", "Linkin Park"));
        songs.add(new Song("Havaana", "Camila Cabello"));
        songs.add(new Song("Blank Space", "Taylor Swift"));
        songs.add(new Song("Something Just Like This", "ChainSmokers"));
        songs.add(new Song("Shape Of You", "Ed sheeran"));
        songs.add(new Song("Hey Brother", "Avicii"));
        songs.add(new Song("Animals", "Maroon 5"));
        songs.add(new Song("Somebody That I used to know", "Gotye"));
        songs.add(new Song("Cheap Thrills", "Sia"));
        songs.add(new Song("Channa Mereya", "Arijit Singh"));
        songs.add(new Song("Hamari Adhuri Kahani", "Arijit Singh"));

    }

}
