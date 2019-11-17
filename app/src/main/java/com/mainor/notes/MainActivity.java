package com.mainor.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mainor.notes.entities.Note;
import com.mainor.notes.dummy.NotesContent;
import com.mainor.notes.persistence.AppDatabase;
import com.mainor.notes.persistence.NoteDao;

import org.joda.time.DateTime;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NotesFragment.OnListFragmentInteractionListener {
    private MyNoteRecyclerViewAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();


        List<Note> myDataLists = database.noteDao().selectAllNotes();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mAdapter = new  MyNoteRecyclerViewAdapter(myDataLists, database, MainActivity.this);


        mRecyclerView.setAdapter(mAdapter);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mRecyclerView.setLayoutManager(linearLayoutManager);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainactivity_notes_list, new NotesFragment()).commit();
        findViewById(R.id.mainactivity_btn_new_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle edit = new Bundle();
                boolean editNote = false;
                edit.putBoolean("editNote", editNote);
                startActivity(new Intent(view.getContext(),NoteEditActivity.class).putExtras(edit));
            }
        });


        }



    @Override
    public void onListFragmentInteraction(Note item) {

    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }




}

