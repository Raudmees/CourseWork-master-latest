package com.mainor.notes;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.mainor.notes.dummy.NotesContent;
import com.mainor.notes.dummy.NotesContent;
import com.mainor.notes.entities.Note;
import com.mainor.notes.persistence.AppDatabase;
//import com.mainor.notes.persistence.NotePersistence;

import java.util.List;


public class NotesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    public NotesFragment() {
    }



    public static NotesFragment newInstance(int columnCount) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database-name").build();
            NotesContent notes = new NotesContent();

           // AppDatabase db = AppDatabase.Builder();


            //recyclerView.setAdapter(new MyNoteRecyclerViewAdapter(NotesContent.ITEMS, mListener));
/*
            Runnable target = new Runnable() {
                @Override
                public void run() {
                    List<Note> notes = NotePersistence.getDb(context.getApplicationContext()).noteDao().getAll();
                    recyclerView.setAdapter(new MyNoteRecyclerViewAdapter(notes, mListener));
                }
            };
            Thread thread = new Thread(target);
            thread.start();
            */



        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Note item);
    }
}
