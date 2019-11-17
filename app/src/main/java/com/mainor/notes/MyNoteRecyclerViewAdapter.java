package com.mainor.notes;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.mainor.notes.NotesFragment.OnListFragmentInteractionListener;
import com.mainor.notes.entities.Note;
import com.mainor.notes.persistence.AppDatabase;
import com.mainor.notes.persistence.NoteDao;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Note} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>  {
     MyNoteRecyclerViewAdapter mAdapter;
     Context contextt;


    AppDatabase dBase;


    private final List<Note> mValues;



    public MyNoteRecyclerViewAdapter(List<Note> items, AppDatabase db, Context context) {
        mValues = items;
        dBase= db;
        contextt = context;


      //  mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int noteId;
        holder.mItem = mValues.get(position);
       noteId = mValues.get(position).getId();
        final Bundle extras = new Bundle();




        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mContentView.setText(mValues.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(v.getContext(), holder.mContentView.getText(), Toast.LENGTH_LONG).show();
                v.getContext().startActivity(new Intent(v.getContext(),NoteViewActivity.class).putExtra("ID", noteId));






            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                alertView(position, noteId);
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return mValues != null? mValues.size() : 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mTitle;
        public final TextView mContentView;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.item_number);
            mTitle = view.findViewById(R.id.note_tv_title);
            mContentView = view.findViewById(R.id.note_tv_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void alertView( final int position,  final int noteId ) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(contextt);
        dialog.setTitle( "Warning" )

                .setMessage("Do you wish to delete this note?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        mValues.remove(position);
                        Note note = dBase.noteDao().selectNote(noteId);
                        dBase.noteDao().deleteNote(note);
                        //notifyItemRemoved(position);
                        notifyDataSetChanged();

                    } })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialoginterface, int i) {

         dialoginterface.cancel();
          }})


                .show();
    }
}
