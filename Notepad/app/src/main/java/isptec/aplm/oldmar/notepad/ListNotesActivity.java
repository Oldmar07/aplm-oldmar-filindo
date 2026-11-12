package isptec.aplm.oldmar.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    private final List<String> titles = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    // Recebe o resultado de CreateNoteActivity (Ok -> RESULT_OK, Cancel -> RESULT_CANCELED)
    private final ActivityResultLauncher<Intent> createLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String title = data.getStringExtra(CreateNoteActivity.EXTRA_TITLE);
                    String text = data.getStringExtra(CreateNoteActivity.EXTRA_TEXT);
                    NoteStore.getInstance().add(new Note(title, text));
                    refreshTitles();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        ListView listNotes = findViewById(R.id.listNotes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listNotes.setAdapter(adapter);

        listNotes.setOnItemClickListener((parent, view, position, id) -> {
            Note note = NoteStore.getInstance().getAll().get(position);
            Intent intent = new Intent(this, ReadNoteActivity.class);
            intent.putExtra(ReadNoteActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(ReadNoteActivity.EXTRA_TEXT, note.getText());
            startActivity(intent);
        });

        Button btnNewNote = findViewById(R.id.btnNewNote);
        btnNewNote.setOnClickListener(v ->
                createLauncher.launch(new Intent(this, CreateNoteActivity.class)));

        refreshTitles();
    }

    private void refreshTitles() {
        titles.clear();
        for (Note n : NoteStore.getInstance().getAll()) {
            titles.add(n.getTitle());
        }
        adapter.notifyDataSetChanged();
    }
}