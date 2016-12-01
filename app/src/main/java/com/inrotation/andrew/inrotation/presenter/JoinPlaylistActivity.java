package com.inrotation.andrew.inrotation.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inrotation.andrew.inrotation.R;
import com.inrotation.andrew.inrotation.model.Playlist;
import com.inrotation.andrew.inrotation.model.Song;

public class JoinPlaylistActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_playlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("Join A Playlist");


        database = FirebaseDatabase.getInstance();

        final EditText editTextName = (EditText) findViewById(R.id.searchPlaylistCode);
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextName.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    handled = true;
                }
                return handled;
            }
        });
        Button doneButton = (Button) findViewById(R.id.PlaylistNameDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPlaylistCode(editTextName.getText().toString(), v);
            }
        });
    }

    protected void checkPlaylistCode(String codeInput, final View v) {
        final Context context = getApplicationContext();
        DatabaseReference ref = database.getReference("server/saving-data/playlists/");
        ref.child(codeInput).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Playlist post = snapshot.getValue(Playlist.class);
                    Intent joinPlaylistIntent = new Intent(v.getContext(), ActivePlaylistActivity.class);
                    joinPlaylistIntent.putExtra("PlaylistCode", post.getKey());
                    startActivity(joinPlaylistIntent);
                }
                else {
                    Toast.makeText(context, "Playlist does not exist. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("JoinPlaylistActivity", "Error" + firebaseError);
            }
        });

    }


}
