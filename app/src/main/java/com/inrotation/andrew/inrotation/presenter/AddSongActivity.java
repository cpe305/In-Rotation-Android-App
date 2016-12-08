package com.inrotation.andrew.inrotation.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.inrotation.andrew.inrotation.R;
import com.inrotation.andrew.inrotation.data_access.DatabaseModifier;
import com.inrotation.andrew.inrotation.model.Song;

import java.util.ArrayList;

/**
 * Represents the activity that handles adding a new song to the current playlist.
 * Created by Andrew Cofano
 */
public class AddSongActivity extends AppCompatActivity {

    private ListView mListView;
    private DatabaseModifier dbModifier;
    private String playlistCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            playlistCode = (String) b.get("playlistCode");
        }

        setContentView(R.layout.activity_add_song);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle(R.string.add_song_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ExpectFirstSongPick();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void ExpectFirstSongPick() {
        final EditText editTextName = (EditText) findViewById(R.id.SongNameSearch);
        final Context context = getApplicationContext();
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (editTextName.getText().toString().isEmpty()) {
                        Toast.makeText(context,context.getString(R.string.empty_input_reminder),
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String songSearches = editTextName.getText().toString();
                        presentSongMatches(songSearches);
                        handled = true;
                    }
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextName.getWindowToken(), 0);

                return handled;
            }
        });
    }

    protected void presentSongMatches(String searchInput) {
        mListView = (ListView) findViewById(R.id.songSearchListView);

        final ArrayList<Song> songMatches = LibrarySearcher.obtainSongMatches(searchInput.split(" "), this);
        SearchListAdapter adapter = new SearchListAdapter(this, songMatches);
        Log.d("SongMatches", songMatches.toString());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song selectedSong = songMatches.get(position);
                addSelectedSong(selectedSong);
            }
        });
    }

    private void addSelectedSong(Song selectedSong) {

        dbModifier = new DatabaseModifier();
        dbModifier.addSongToPlaylist(playlistCode, selectedSong);
        finish();
        return;
    }

}
