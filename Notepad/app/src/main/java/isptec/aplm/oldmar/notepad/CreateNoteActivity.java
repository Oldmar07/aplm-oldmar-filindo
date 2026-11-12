package isptec.aplm.oldmar.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etText = findViewById(R.id.etText);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            if (title.isEmpty()) {
                etTitle.setError("Escreve um título");
                return;
            }
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_TEXT, etText.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}