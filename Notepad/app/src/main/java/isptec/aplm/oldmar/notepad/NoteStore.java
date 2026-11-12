package isptec.aplm.oldmar.notepad;


import java.util.ArrayList;
import java.util.List;

public class NoteStore {
    private static final NoteStore INSTANCE = new NoteStore();
    private final List<Note> notes = new ArrayList<>();

    private NoteStore() { }

    public static NoteStore getInstance() { return INSTANCE; }

    public void add(Note note) { notes.add(note); }

    public List<Note> getAll() { return notes; }
}
