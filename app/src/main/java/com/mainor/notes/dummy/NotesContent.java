package com.mainor.notes.dummy;

import android.provider.ContactsContract;

import com.mainor.notes.entities.Note;


import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NotesContent {


    public static final List<Note> ITEMS = new ArrayList<Note>();


    public static final Map<String, Note> ITEM_MAP = new HashMap<String, Note>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNote(i));
        }
    }

    private static void addItem(Note note) {
        ITEMS.add(note);
        ITEM_MAP.put("1", note);
    }

    private static Note createNote(int position) {
        Note note = new Note();

        note.setTitle("Title");
        note.setCreationDate(DateTime.now().getMillis());
        note.setContent("Test Content " + position);

        return note;
    }



}
