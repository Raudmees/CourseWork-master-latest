package com.mainor.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mainor.notes.entities.Note;
import com.mainor.notes.persistence.AppDatabase;
import com.mainor.notes.persistence.NoteDao;
import com.mainor.notes.persistence.NotePersistence;
//import com.mainor.notes.persistence.NotePersistence;

import org.joda.time.DateTime;

import java.util.Date;

public class NoteEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        final Context context = this;
        final Note newNote = new Note();
        newNote.setCreationDate(DateTime.now().getMillis());

        final EditText titleEditText = findViewById(R.id.noteEditActivity_et_title);
        TextView dateTextView = findViewById(R.id.noteEditActivity_et_creationDate);
        final EditText contentEditText = findViewById(R.id.noteEditActivity_et_content);
        ImageButton fab = findViewById(R.id.fab);
        Button home =  findViewById(R.id.home);

        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();

     final   NoteDao noteDao = database.noteDao();
      final   Note note = new Note();
        Intent intent = getIntent();
       final Bundle extras = intent.getExtras();

       boolean editTrue = extras.getBoolean("editNote");

        Long strDate1 = DateTime.now().getMillis();
        Date date = new Date(strDate1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateText1 = dateFormat.format(date);
        dateTextView.setText("Creation Date :" +dateText1);

        if (editTrue){
            final int editId = extras.getInt("ID");
            final Note noteUpdate = database.noteDao().selectNote(editId);
            titleEditText.setText(noteUpdate.getTitle());
            contentEditText.setText(noteUpdate.getContent());
                    Long upDate = noteUpdate.getCreationDate();
            Date updateDate = new Date(upDate);
                    String dateText2 = dateFormat.format(updateDate);
                    dateTextView.setText("Creation date: " +dateText2);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                if (titleEditText.getText().toString().trim().length()>0) {


                    final long noteId;


                    noteUpdate.setTitle(titleEditText.getText().toString());
                    noteUpdate.setContent(contentEditText.getText().toString());
                    noteUpdate.setUpdateDate(DateTime.now().getMillis());

                    Runnable target = new Runnable() {
                        @Override
                        public void run() {
                            NotePersistence.getDb(view.getContext()).noteDao().insert(note);
                        }
                    };

                    Thread t = new Thread(target);
                    t.start();
                    noteId = editId;
                    noteDao.updateNote(noteUpdate);
                    startActivity(new Intent(context, NoteViewActivity.class).putExtra("ID", noteId));
                } else {
                    Toast.makeText(NoteEditActivity.this, "Please enter a title to continue",
                            Toast.LENGTH_LONG).show();
                }
                }
            });


        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    if (titleEditText.getText().toString().trim().length()>0) {


                        final long noteId;


                        note.setTitle(titleEditText.getText().toString());
                        note.setContent(contentEditText.getText().toString());
                        note.setCreationDate(DateTime.now().getMillis());

                        Runnable target = new Runnable() {
                            @Override
                            public void run() {
                                NotePersistence.getDb(view.getContext()).noteDao().insert(note);
                            }
                        };

                        Thread t = new Thread(target);
                        t.start();
                        noteId = noteDao.insert(note);
                        startActivity(new Intent(context, NoteViewActivity.class).putExtra("ID", noteId));
                    } else {
                        Toast.makeText(NoteEditActivity.this, "Please enter a title to continue",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });


        }



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),MainActivity.class));
            }
        });
    }
  /*  @Override
    public void onBackPressed() {
        Context backContext = this;

        startActivity(new Intent(backContext, MainActivity.class));
    }
 */
}
