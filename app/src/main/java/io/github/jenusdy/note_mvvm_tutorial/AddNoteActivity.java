package io.github.jenusdy.note_mvvm_tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import io.github.jenusdy.livedata_tutorial.R;
import io.github.jenusdy.note_mvvm_tutorial.entity.Note;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "extra-title";
    public static final String EXTRA_DESCRIPTION = "extra-description";
    public static final String EXTRA_PRIORITY = "extra-priority";

    private TextInputEditText titleNote, descriptionNote;
    private NumberPicker priorityNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleNote = findViewById(R.id.title_note);
        descriptionNote = findViewById(R.id.description_note);
        priorityNote = findViewById(R.id.number_priority);

        priorityNote.setMinValue(1);
        priorityNote.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_white);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = titleNote.getText().toString();
        String description = descriptionNote.getText().toString();
        Integer priority = priorityNote.getValue();
        Note note = new Note(title, description, priority);

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Mohon isi title dan deskripsi terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();

    }
}